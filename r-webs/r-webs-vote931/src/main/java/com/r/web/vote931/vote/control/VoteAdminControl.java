/**
 * 
 */
package com.r.web.vote931.vote.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.r.core.exceptions.SwitchPathException;
import com.r.core.util.RandomUtil;
import com.r.web.vote931.support.abs.AbstractControl;
import com.r.web.vote931.support.bean.Support;
import com.r.web.vote931.vote.VoteItemType;
import com.r.web.vote931.vote.exception.VoteItemContextErrorException;
import com.r.web.vote931.vote.model.AbsVoteItem;
import com.r.web.vote931.vote.model.CompletionVoteItem;
import com.r.web.vote931.vote.model.MultipleOptionVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.Vote;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;
import com.r.web.vote931.vote.service.VoteItemService;
import com.r.web.vote931.vote.service.VoteService;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller("vote.control.admin")
@RequestMapping(value = "/admin")
public class VoteAdminControl extends AbstractControl {

    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Resource(name = "vote.service.voteitem")
    private VoteItemService voteItemService;

    /** 问卷列表页面 */
    @RequestMapping(value = "page/vote/index")
    public String pageVoteGenerate(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<Vote> votes = voteService.queryAll();
        model.put("votes", votes);
        return "admin/voteList";
    }

    /**
     * 问卷项列表
     * 
     * @param model
     * @return 页面路径
     */
    @RequestMapping(value = "page/voteitem/index")
    public String pageVoteItemList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<AbsVoteItem> voteItems = voteItemService.queryAll();
        model.put("voteItems", voteItems);
        return "admin/voteItemList";
    }

    /**
     * 问卷项列表<br />
     * 
     * @param page
     *            页数
     * @param model
     * @return 页面路径
     */
    @RequestMapping(value = "page/voteitem/index/{curPage}")
    public String pageVoteItemList(@PathVariable Integer curPage, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<AbsVoteItem> voteItems = voteItemService.queryByPage(curPage, 20);
        model.put("voteItems", voteItems);
        return "admin/voteItemList";
    }

    /** 新增问卷项 */
    @RequestMapping(value = "page/voteitem/add")
    public String pageVoteItemAdd(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put("types", VoteItemType.values());
        return "admin/voteItemAdd";
    }

    /** 执行保存操作 */
    @RequestMapping(value = "func/voteitem/save")
    @ResponseBody
    public Support<AbsVoteItem> funcVoteItemSave(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Support<AbsVoteItem> support = new Support<AbsVoteItem>();
        support.setSuccess(true);
        support.setTips("保存成功。");

        VoteItemType type = null; // 问卷项类型
        try {
            type = VoteItemType.valueOf(request.getParameter("type"));
        } catch (Exception e) {
            support.setSuccess(false);
            support.setTips("问卷项类型错误，请选择一个正确的问卷项类型。");
            return support;
        }

        try {
            voteItemService.save(getAbsVoteItem(request, type));
        } catch (VoteItemContextErrorException vicee) {
            support.setSuccess(false);
            support.setTips(vicee.getMessage());
            return support;
        } catch (Exception e) {
            String uuid = RandomUtil.uuid();
            String errorMsg = "系统内部错误 : " + e.toString() + " (错误识别码 : " + uuid + ")";
            logger.error(errorMsg, e);
            support.setSuccess(false);
            support.setTips(errorMsg);
            return support;
        }
        return support;
    }

    /** 改变状态 */
    @RequestMapping(value = "func/voteitem/changestatus/{id}")
    public String funcVoteItemChangestatus(@PathVariable String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        AbsVoteItem voteItem = voteItemService.findById(id);
        voteItem.setIsEnable(!voteItem.isEnable());
        voteItemService.save(voteItem);
        return "redirect:/admin/page/voteitem/index";
    }

    /** 从request中获取问卷项信息 */
    private AbsVoteItem getAbsVoteItem(HttpServletRequest request, VoteItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("未知的问卷项。");
        }
        String id = request.getParameter("id"); // 问卷项ID
        AbsVoteItem voteItem = null;
        if (StringUtils.isNotBlank(id)) { // 修改
            voteItem = voteItemService.findById(id);
        }
        String question = request.getParameter("question"); // 问题
        String remark = request.getParameter("remark"); // 备注
        boolean isEnable = Boolean.valueOf(request.getParameter("isEnable")); // 状态(启用/禁用)

        switch (type) {
        case yesno:
            if (voteItem == null) {
                voteItem = new YesOrNoVoteItem();
            }
            YesOrNoVoteItem yesno = (YesOrNoVoteItem) voteItem;
            yesno.setType(VoteItemType.yesno);
            yesno.setRemark(remark);
            yesno.setQuestion(question);
            yesno.setProvider("闪耀筝团 - 雨");
            yesno.setIsEnable(isEnable);
            yesno.setAnswerYes(request.getParameter("answerYes"));
            yesno.setAnswerNo(request.getParameter("answerNo"));
            yesno.setAnswer(Boolean.valueOf(request.getParameter("answer")));
            return yesno;
        case single:
            if (voteItem == null) {
                voteItem = new SingleOptionVoteItem();
            }
            SingleOptionVoteItem single = (SingleOptionVoteItem) voteItem;
            single.setType(VoteItemType.single);
            single.setRemark(remark);
            single.setQuestion(question);
            single.setProvider("闪耀筝团 - 雨");
            single.setIsEnable(isEnable);
            single.setAnswer1(request.getParameter("answer1"));
            single.setAnswer2(request.getParameter("answer2"));
            single.setAnswer3(request.getParameter("answer3"));
            single.setAnswer4(request.getParameter("answer4"));
            single.setAnswer(Integer.valueOf(request.getParameter("answer")));
            return single;
        case multiple:
            if (voteItem == null) {
                voteItem = new MultipleOptionVoteItem();
            }
            MultipleOptionVoteItem multiple = (MultipleOptionVoteItem) voteItem;
            multiple.setType(VoteItemType.multiple);
            multiple.setRemark(remark);
            multiple.setQuestion(question);
            multiple.setProvider("闪耀筝团 - 雨");
            multiple.setIsEnable(isEnable);
            multiple.setAnswer1(request.getParameter("answer1"));
            multiple.setAnswer2(request.getParameter("answer2"));
            multiple.setAnswer3(request.getParameter("answer3"));
            multiple.setAnswer4(request.getParameter("answer4"));
            multiple.setAnswer(StringUtils.join(request.getParameterValues("answer"), MultipleOptionVoteItem.SPLIT));
            return multiple;
        case completion:
            if (voteItem == null) {
                voteItem = new CompletionVoteItem();
            }
            CompletionVoteItem completion = (CompletionVoteItem) voteItem;
            completion.setType(VoteItemType.completion);
            completion.setRemark(remark);
            completion.setQuestion(question);
            completion.setProvider("闪耀筝团 - 雨");
            completion.setIsEnable(isEnable);
            completion.setAnswer1(request.getParameter("answer1"));
            completion.setAnswer2(request.getParameter("answer2"));
            completion.setAnswer3(request.getParameter("answer3"));
            completion.setAnswer4(request.getParameter("answer4"));
            return completion;
        default:
            throw new SwitchPathException("没有此类型的问卷项能够进行数据装载。");
        }
    }
}
