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

import com.r.web.vote931.support.abs.AbstractControl;
import com.r.web.vote931.support.bean.Support;
import com.r.web.vote931.vote.VoteItemType;
import com.r.web.vote931.vote.model.AbsVoteItem;
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

    /**
     * 问卷项列表
     * 
     * @param model
     * @return 页面路径
     */
    @RequestMapping(value = "index")
    public String voteItemListPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        List<AbsVoteItem> voteItems = voteService.queryAll();
        model.put("voteItems", voteItems);
        return "admin/voteItemList";
    }

    /** 新增问卷项 */
    @RequestMapping(value = "page/add")
    public String voteItemAddPage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.put("types", VoteItemType.values());
        return "admin/voteItemAdd";
    }

    /** 修改问卷项 */
    @RequestMapping(value = "page/edit/{viid}")
    public String voteItemUpdatePage(@PathVariable String viid, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "admin/voteItemUpdate";
    }

    /** 执行保存操作 */
    @RequestMapping(value = "func/save")
    public Support<AbsVoteItem> voteItemSave(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Support<AbsVoteItem> support = new Support<AbsVoteItem>();
        support.setSuccess(true);
        String viid = request.getParameter("viid");
        if (StringUtils.isBlank(viid)) { // 新增
            
            support.setTips("新增成功。");
        } else { // 修改
            
            support.setTips("编辑成功。");
        }
        return support;
    }
}
