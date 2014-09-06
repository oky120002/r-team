/**
 * 
 */
package com.r.web.module.vote.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.r.web.module.vote.bean.VoteOption;
import com.r.web.module.vote.model.Vote;
import com.r.web.module.vote.service.VoteItemService;
import com.r.web.module.vote.service.VoteService;
import com.r.web.support.abs.AbstractControl;
import com.r.web.support.bean.Support;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller("vote.control.vote")
@RequestMapping(value = "/vote")
public class VoteControl extends AbstractControl {
    
    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Resource(name = "vote.service.voteitem")
    private VoteItemService voteItemService;

    /** 生成问卷管理页面 */
    @RequestMapping(value = "page/generate")
    public String pageGenerate(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        return "vote/voteGenerate";
    }

    /** 问卷答题页面 */
    @RequestMapping(value = "page/answer/{id}")
    public String pageAnswer(@PathVariable String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Vote vote = voteService.find(id);
        model.put("vote", vote); // 有可能问卷为null.则页面自行判断
        return "vote/voteAnswer";
    }

    /** 生成问卷 */
    @RequestMapping(value = "func/createVote")
    @ResponseBody
    public Support<String> funcCreateVote(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Support<String> support = new Support<String>();
        support.setSuccess(true);
        support.setTips("生成问卷成功！");

        try {
            Vote vote = voteService.createVote(new VoteOption(request));
            support.setModel(request.getContextPath() + "/vote/page/answer/" + vote.getId());
        } catch (Exception e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
        }
        return support;// "redirect:" + vote.getId();
    }

}
