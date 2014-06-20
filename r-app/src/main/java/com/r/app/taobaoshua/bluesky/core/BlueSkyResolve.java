package com.r.app.taobaoshua.bluesky.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.ShopScore;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskType;

public class BlueSkyResolve {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

	/**
	 * 解析任务列表
	 * 
	 * @param html
	 * @return
	 */
	public Collection<Task> resolveTaskListHtml(String html) {
		List<Task> tasks = new ArrayList<Task>();
		int curPos = 0;

		int start = html.indexOf("class=\"list_tbl\"");
		int end = html.indexOf("</table>", start);
		html = html.substring(start, end);

		while ((curPos = html.indexOf("<tr>")) != -1) {
			html = html.substring(curPos);
			Task task = new Task();
			task.setType(TaskAddr.淘宝任务区);
			task.setStatus(TaskStatus.等待接手);

			// 商品类型和编号
			String td = StringUtils.substringBetween(html, "<td width=\"163\"", "</td>");
			resolveTaskNumberAndTaskType(task, td);

			// 发布者
			td = StringUtils.substringBetween(html, "<td width=\"145\"", "</td>");
			resolveTaskPublishing(task, td);

			// 商品抵押金
			td = StringUtils.substringBetween(html, "<td width=\"104\"", "</td>");
			resolveTaskSecurityPrice(task, td);

			// 发布点
			curPos = html.indexOf("width=\"70\"");
			html = html.substring(curPos);
			task.setPublishingPoint(Double.valueOf(StringUtils.substringBetween(html, "&nbsp; &nbsp;", "点").trim()));

			// 发布时间
			curPos = html.indexOf("width=\"140\"");
			html = html.substring(curPos);
			String time = StringUtils.substringBetween(html, "/>", "</td>").trim();
			try {
				task.setTaskPublishingTime(sdf.parse(time));
			} catch (ParseException e) {
			}

			// 几天好评
			td = StringUtils.substringBetween(html, "<td width=\"80\"", "</td>");
			resolveTaskTimeLimit(task, td);

			// 限制条件
			td = StringUtils.substringBetween(html, "<td width=\"164\"", "</td>");
			resolveTaskRestrictiveCondition(task, td);

			// 接手任务ID
			curPos = html.indexOf("width=\"72\"");
			html = html.substring(curPos);
			task.setTaskId(StringUtils.substringBetween(html, "ID=", "\">").trim());

			// 计算统计值
			task.calStatistics();

			tasks.add(task);
		}

		return tasks;
	}

	/** 解析商品类型和编号 */
	private void resolveTaskNumberAndTaskType(Task task, String td) {
		if (0 < td.indexOf("realobject.gif")) { // 实物商品
			task.setTaskType(TaskType.实物);
		} else if (0 < td.indexOf("realobject_shop.gif")) { // 实购商品
			task.setTaskType(TaskType.实购);
		} else if (0 < td.indexOf("virtual.gif")) { // 虚拟
			task.setTaskType(TaskType.虚拟);
		} else if (0 < td.indexOf("virtual_shop.gif")) { // 虚购商品
			task.setTaskType(TaskType.虚购);
		} else if (0 < td.indexOf("dapei.gif")) { // 搭配
			task.setTaskType(TaskType.搭配);
		} else {
			task.setTaskType(TaskType.其它);
		}

		// 任务编号

		task.setNumber(td.substring(td.indexOf("/>") + 2).trim());
	}

	/** 解析发布者信息 */
	private void resolveTaskPublishing(Task task, String td) {
		// 发布者平台账号
		String account = StringUtils.substringBetween(td, "center\">", "<img").trim();
		String temp = StringUtils.substringBetween(account, "<span class=\"red\">", "</span>");
		if (StringUtils.isBlank(temp)) {
			task.setAccount(account);
		} else {
			task.setAccount(temp);
		}

		// 是否在线
		if (0 < td.indexOf("/online.gif")) {
			task.setIsAccountOnLine(Boolean.TRUE);
		} else {
			task.setIsAccountOnLine(Boolean.FALSE);
		}

		// 是否VIP
		if (0 < td.indexOf("vip.gif")) {
			task.setIsAccountVip(Boolean.TRUE);
		} else {
			task.setIsAccountVip(Boolean.FALSE);
		}

		// 是否新人
		if (0 < td.indexOf("new.gif")) {
			task.setIsNew(Boolean.TRUE);
		} else {
			task.setIsNew(Boolean.FALSE);
		}

		// 等级
		if (0 < td.indexOf("score_")) {
			String accountLevel = StringUtils.substringBetween(td, "style/images/score_", "' align=").trim();
			task.setAccountLevel("score_" + accountLevel);
		}

		// 经验值
		if (0 < td.indexOf("经验积分")) {
			String accountExe = StringUtils.substringBetween(td, "经验积分：", "' />").trim(); // 新手是没得经验积分的
			task.setAccountExe(Integer.valueOf(accountExe));
		}
	}

	/** 解析商品抵押金 */
	private void resolveTaskSecurityPrice(Task task, String td) {
		task.setSecurityPrice(Double.valueOf(StringUtils.substringBetween(td, "dm_gold\">", "</span>").trim()));
		if (0 < td.indexOf("需改价")) { // 需改价
			task.setIsUpdatePrice(Boolean.TRUE);
		} else {
			task.setIsUpdatePrice(Boolean.FALSE);
		}
	}

	/** 解析好评期限 */
	private void resolveTaskTimeLimit(Task task, String td) {
		if (0 < td.indexOf("店评5分")) { // 店评5分
			task.setShopScore(ShopScore.全5分);
		} else {
			task.setShopScore(null);
		}

		if (0 < td.indexOf("立即确认")) { // 立即好评
			task.setTimeLimit(0);
			return;
		}
		if (0 < td.indexOf("30分钟")) { // 30分钟后好评
			task.setTimeLimit(30);
			return;
		}

		task.setTimeLimit(Integer.valueOf(StringUtils.substringBetween(td, "title=\"", "天后确认收").trim()) * 24 * 60);

	}

	/** 解析商品限制条件 */
	private void resolveTaskRestrictiveCondition(Task task, String td) {
		if (0 < td.indexOf("ShangBao.gif")) { // 商保任务
			task.setIsSincerity(Boolean.TRUE);
		} else {
			task.setIsSincerity(Boolean.FALSE);
		}
		if (0 < td.indexOf("ShiMing.gif")) { // 实名认证
			task.setIsIDCard(Boolean.TRUE);
		} else {
			task.setIsIDCard(Boolean.FALSE);
		}
		if (0 < td.indexOf("SouSuo.gif")) { // 宝贝需要搜索
			task.setIsSearch(Boolean.TRUE);
		} else {
			task.setIsSearch(Boolean.FALSE);
		}
		if (0 < td.indexOf("ShouCang.gif")) { // 双收藏
			task.setIsCollect(Boolean.TRUE);
		} else {
			task.setIsCollect(Boolean.FALSE);
		}
		if (0 < td.indexOf("WangLiao.gif")) { // 需要旺聊
			task.setIsWangWang(Boolean.TRUE);
		} else {
			task.setIsWangWang(Boolean.FALSE);
		}
		if (0 < td.indexOf("ShenHe.gif")) { // 需要审核
			task.setIsReview(Boolean.TRUE);
		} else {
			task.setIsReview(Boolean.FALSE);
		}
		if (0 < td.indexOf("ShenHe.gif")) { // 需要审核
			task.setIsReview(Boolean.TRUE);
		} else {
			task.setIsReview(Boolean.FALSE);
		}

		// 限制条件
		if (0 < td.indexOf("class=\"blue\"")) {
			String auxiliaryCondition = StringUtils.substringBetween(td, "class=\"blue\" title=\"", "\">").trim();
			task.setAuxiliaryCondition(auxiliaryCondition);

			// 只能判断是否需要QQ的参与
			if (0 < td.indexOf("Q")) {
				task.setIsUseQQ(Boolean.TRUE);
			} else {
				task.setIsUseQQ(Boolean.FALSE);
			}
		}
	}
}
