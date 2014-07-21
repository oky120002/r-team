package com.r.app.taobaoshua.bluesky.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.PaymentType;
import com.r.app.taobaoshua.bluesky.model.enumtask.ShopScore;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskTimeLimit;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskType;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class BlueSkyResolve {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyResolve.class); // 日志

	/**
	 * 解析任务列表
	 * 
	 * @param html
	 * @return
	 */
	public void resolveTaskListHtml(Collection<Task> tasks, String html) {
		int curPos = 0;
		int start = html.indexOf("class=\"list_tbl\"");
		int end = html.indexOf("</table>", start);
		html = html.substring(start, end);

		while ((curPos = html.indexOf("<tr>")) != -1) {
			html = html.substring(curPos);
			
			// 过滤掉已经被接手或者已经完成 的任务
			if (0 < html.indexOf("任务已完成") || 0 < html.indexOf("任务已接手，正在进行中")) {
				curPos += 5;
				html = html.substring(curPos);
				continue;
			}
			
			Task task = new Task();

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
	}

	/** 解析任务详细信息 */
	public void resolveTaskDetail(Task task, String taskDetail) {
		if (0 < taskDetail.indexOf("任务编号")) { // 有异常,例如:传送的ID有误或该任务已被删除
			String[] substringsBetween = StringUtils.substringsBetween(taskDetail, " class=\"tdbg\"", "</tr>");
			for (String tr : substringsBetween) {
				if (0 < tr.indexOf("掌柜名称")) {
					task.setShopkeeper(StringUtils.substringBetween(tr, "&nbsp;", "</td>"));
				}

				if (0 < tr.indexOf("商品标题")) {
					String substringBetween = StringUtils.substringBetween(tr, "&nbsp;", "</td>");
					if (0 < substringBetween.indexOf("color=red")) {
						task.setItemTitle("");
					} else {
						task.setItemTitle(substringBetween);
					}
				}

				if (0 < tr.indexOf("商品地址")) {
					task.setItemAddr(StringUtils.substringBetween(tr, "http://", "</td>"));
				}

				if (0 < tr.indexOf("支付方式")) {
					task.setPaymentType(PaymentType.valueOf(StringUtils.substringBetween(tr, "&nbsp;", "</td>").trim()));
				}

				if (0 < tr.indexOf("指定好评内容")) {
					task.setPraise(StringUtils.substringBetween(tr, "KS_HaoPingContent\">", "</textarea>"));
				}

				if (0 < tr.indexOf("任务发布时间")) {
					try {
						task.setTaskPublishingTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}

				if (0 < tr.indexOf("任务接手时间")) {
					try {
						task.setTaskedTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", " &nbsp;")));
					} catch (Exception e) {
					}

					if (0 < tr.indexOf("target=\"_blank\">")) {
						task.setTaskerAccount(StringUtils.substringBetween(tr, "target=\"_blank\">", "</a>"));
					}

					if (0 < tr.indexOf("接手买号")) {
						task.setTaskerBuyAccount(StringUtils.substringBetween(tr, "接手买号：", "</td>"));
					}
				}

				if (0 < tr.indexOf("任务支付时间")) {
					try {
						task.setTaskPaymentTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}

				if (0 < tr.indexOf("任务发货时间")) {
					try {
						task.setTaskShippingTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}
				if (0 < tr.indexOf("任务收货好评时间")) {
					try {
						task.setTaskReceiptTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}
				if (0 < tr.indexOf("任务确认审核时间")) {
					try {
						task.setTaskConfirmedReviewTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}
				if (0 < tr.indexOf("发布方平台评价时间")) {
					try {
						task.setTaskPublishingEvaluationTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}
				if (0 < tr.indexOf("接手方平台评价时间")) {
					try {
						task.setTaskerEvaluationTime(sdf.parse(StringUtils.substringBetween(tr, "&nbsp;", "</td>")));
					} catch (Exception e) {
					}
				}

				if (0 < tr.indexOf("任务当前状态")) {
					TaskStatus status = null;
					try {
						status = TaskStatus.valueOf(StringUtils.substringBetween(tr, "&nbsp;", "</td>"));
					} catch (IllegalArgumentException e) {
						// 找不到此枚举
						if (0 < tr.indexOf("等待双方平台评价")) {
							status = TaskStatus.已完成_等待双方平台评价;
						} else if (0 < tr.indexOf("等待发布方平台评价")) {
							status = TaskStatus.已完成_等待发布方平台评价;
						} else if (0 < tr.indexOf("等待接手方平台评价")) {
							status = TaskStatus.已完成_等待接手方平台评价;
						} else if (0 < tr.indexOf("等待接手") && 0 < tr.indexOf("已暂停")) {
							status = TaskStatus.等待接手_已暂停;
						} else {
							status = TaskStatus.异常;
							logger.warn("解析当前任务状态异常 : " + tr);
						}
					}
					task.setStatus(status);
				}
			}
		} else {
			if (0 < taskDetail.indexOf("该任务已被删除")) {
				task.setStatus(TaskStatus.任务已删除);
			} else {
				logger.warn("获取任务详细信息异常 : " + taskDetail);
				task.setStatus(TaskStatus.未知状态);
			}
		}
	}

	/** 解析买号页面 */
	public void resolveTaoBaoAccount(Collection<BuyAccount> buyAccounts, String html) {
		// 解析淘宝Tid
		html = html.substring(html.indexOf("Name='淘宝'"));
		String taobaoTid = StringUtils.substringBetween(html, "value='", "'>");

		html = html.substring(html.indexOf("list_Mai"));
		html = StringUtils.substringBetween(html, "list_tbl", "</table>");

		String[] trs = StringUtils.substringsBetween(html, "<tr>", "</tr>");
		for (String tr : trs) {
			BuyAccount buy = new BuyAccount();
			String[] tds = StringUtils.substringsBetween(tr, "<td", "</td>");

			// 买号所属淘宝区域id
			buy.setTaobaoTid(taobaoTid);

			// 买号区域
			if (0 < tds[0].indexOf("淘宝任务")) {
				buy.setType(TaskAddr.淘宝任务区);
			}

			// 买号
			String td1 = tds[1];
			if (0 < td1.indexOf("实名认证")) {
				buy.setIsIDCard(true);
				buy.setBuyAccount(StringUtils.substringBetween(td1, "center\">", "<img").trim());
			} else {
				buy.setIsIDCard(false);
				buy.setBuyAccount(StringUtils.substringAfter(td1, "center\">").trim());
			}

			// 买号信誉
			String xinyu = StringUtils.substringBetween(tds[2], "orange\">", "</span>").trim();
			buy.setBuyPrestige(Integer.valueOf(xinyu));

			// 今日接任务数
			String day = StringUtils.substringAfter(tds[4], "center\">").trim();
			buy.setTakeTaskByDay(Integer.valueOf(day));

			// 本周接任务数
			String week = StringUtils.substringAfter(tds[5], "center\">").trim();
			buy.setTakeTaskByWeek(Integer.valueOf(week));

			// 是否启用
			if (0 < tds[7].indexOf("Status=4")) { // 启用中
				buy.setIsEnable(true);
			} else { // status=0 则是停用中
				buy.setIsEnable(false);
			}

			buyAccounts.add(buy);
		}
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

		String s = td.substring(td.indexOf("title"));
		String timeLimit = StringUtils.substringBetween(s, "\">", "</span>");
		task.setTimeLimit(TaskTimeLimit.getTaskTimeLimitByName(timeLimit));
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
		if (0 < td.indexOf("DiZhi.gif")) { // 改地址
			task.setIsUpdateAddr(Boolean.TRUE);
		} else {
			task.setIsUpdateAddr(Boolean.FALSE);
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

			// 判断是否需要真实地址接空包
			if (0 < td.indexOf("真实发货")) {
				task.setIsBaoGuo(Boolean.TRUE);
			} else {
				task.setIsBaoGuo(Boolean.FALSE);
			}

			// 判断改价..不止这里一处
			if (0 < td.indexOf("改价")) {
				task.setIsUpdatePrice(Boolean.TRUE);
			}
		}
	}
}
