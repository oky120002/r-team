/**
 * 
 */
package com.r.web.vote931.vote.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.r.web.vote931.support.abs.AbstractControl;
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
	public String voteItemList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<AbsVoteItem> voteItems = voteService.queryAll();
		model.put("voteItems", voteItems);
		return "admin/voteItemList";
	}
}
