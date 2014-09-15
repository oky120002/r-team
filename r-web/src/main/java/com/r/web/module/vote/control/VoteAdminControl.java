/**
 * 
 */
package com.r.web.module.vote.control;

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
import com.r.web.module.vote.VoteItemType;
import com.r.web.module.vote.exception.VoteItemContextErrorException;
import com.r.web.module.vote.model.Vote;
import com.r.web.module.vote.model.base.VoteBaseItemImpl;
import com.r.web.module.vote.model.base.CompletionVoteBaseItem;
import com.r.web.module.vote.model.base.MultipleOptionVoteBaseItem;
import com.r.web.module.vote.model.base.SingleOptionVoteBaseItem;
import com.r.web.module.vote.model.base.YesOrNoVoteBaseItem;
import com.r.web.module.vote.service.VoteBaseItemService;
import com.r.web.module.vote.service.VoteService;
import com.r.web.support.abs.AbstractControl;
import com.r.web.support.bean.Support;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller("vote.control.admin")
@RequestMapping(value = "/admin/vote")
public class VoteAdminControl extends AbstractControl {

    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Resource(name = "vote.service.votebaseitem")
    private VoteBaseItemService voteBaseItemService;

    /** 问卷列表页面 */
    @RequestMapping(value = "index")
    public String pageIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return pageVoteIndex(model, request, response);
    }

    /** 问卷列表页面 */
    @RequestMapping(value = "page/vote/index")
    public String pageVoteIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<Vote> votes = voteService.queryAll();
        model.put("votes", votes);
        return "admin/vote/voteIndex";
    }

    /**
     * 基础问卷项列表,全部
     * 
     * @param model
     * @return 页面路径
     */
    @RequestMapping(value = "page/votebaseitem/index")
    public String pageVoteBaseItemIndex(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<VoteBaseItemImpl> voteBaseItems = voteBaseItemService.queryAll();
        model.put("voteBaseItems", voteBaseItems);
        return "admin/vote/voteBaseItemIndex";
    }

    /**
     * 基础问卷项列表,分页<br />
     * 
     * @param page
     *            页数
     * @param model
     * @return 页面路径
     */
    @RequestMapping(value = "page/votebaseitem/index/{curPage}")
    public String pageVoteItemIndex(@PathVariable Integer curPage, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<VoteBaseItemImpl> voteBaseItems = voteBaseItemService.queryByPage(curPage, 20);
        model.put("voteBaseItems", voteBaseItems);
        return "admin/vote/voteBaseItemIndex";
    }

    /** 新增基础问卷项 */
    @RequestMapping(value = "page/votebaseitem/add")
    public String pageVoteBaseItemAdd(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put("types", VoteItemType.values());
        return "admin/vote/voteBaseItemAdd";
    }

    /** 执行基础问卷项的保存操作 */
    @RequestMapping(value = "func/votebaseitem/save")
    @ResponseBody
    public Support<VoteBaseItemImpl> funcVoteBaseItemSave(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Support<VoteBaseItemImpl> support = new Support<VoteBaseItemImpl>();
        support.setSuccess(true);
        support.setTips("保存成功。");

        VoteItemType type = null; // 问卷项类型
        try {
            type = VoteItemType.valueOf(request.getParameter("type"));
        } catch (Exception e) {
            support.setSuccess(false);
            support.setTips("基础问卷项类型错误，请选择一个正确的基础问卷项类型。");
            return support;
        }

        try {
            voteBaseItemService.save(getAbsVoteItem(request, type));
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

    /** 改变基础问卷项的状态 */
    @RequestMapping(value = "func/votebaseitem/changestatus/{id}")
    public String funcVoteBaseItemChangestatus(@PathVariable String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        VoteBaseItemImpl voteBaseItem = voteBaseItemService.findById(id);
        voteBaseItem.setIsEnable(!voteBaseItem.isEnable());
        voteBaseItemService.save(voteBaseItem);
        return "redirect:/admin/vote/page/votebaseitem/index";
    }

    /** 从request中获取问卷项信息 */
    private VoteBaseItemImpl getAbsVoteItem(HttpServletRequest request, VoteItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("未知的问卷项。");
        }
        String id = request.getParameter("id"); // 问卷项ID
        VoteBaseItemImpl voteItem = null;
        if (StringUtils.isNotBlank(id)) { // 修改
            voteItem = voteBaseItemService.findById(id);
        }
        String question = request.getParameter("question"); // 问题
        String remark = request.getParameter("remark"); // 备注
        boolean isEnable = Boolean.valueOf(request.getParameter("isEnable")); // 状态(启用/禁用)

        switch (type) {
        case yesno:
            if (voteItem == null) {
                voteItem = new YesOrNoVoteBaseItem();
            }
            YesOrNoVoteBaseItem yesno = (YesOrNoVoteBaseItem) voteItem;
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
                voteItem = new SingleOptionVoteBaseItem();
            }
            SingleOptionVoteBaseItem single = (SingleOptionVoteBaseItem) voteItem;
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
                voteItem = new MultipleOptionVoteBaseItem();
            }
            MultipleOptionVoteBaseItem multiple = (MultipleOptionVoteBaseItem) voteItem;
            multiple.setType(VoteItemType.multiple);
            multiple.setRemark(remark);
            multiple.setQuestion(question);
            multiple.setProvider("闪耀筝团 - 雨");
            multiple.setIsEnable(isEnable);
            multiple.setAnswer1(request.getParameter("answer1"));
            multiple.setAnswer2(request.getParameter("answer2"));
            multiple.setAnswer3(request.getParameter("answer3"));
            multiple.setAnswer4(request.getParameter("answer4"));
            multiple.setAnswer(StringUtils.join(request.getParameterValues("answer"), MultipleOptionVoteBaseItem.SPLIT));
            return multiple;
        case completion:
            if (voteItem == null) {
                voteItem = new CompletionVoteBaseItem();
            }
            CompletionVoteBaseItem completion = (CompletionVoteBaseItem) voteItem;
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
