package com.r.app.taobaoshua.bluesky.websource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskType;
import com.r.app.taobaoshua.yuuboo.model.PV;
import com.r.app.taobaoshua.yuuboo.model.PVQuest;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class BlueSkyResolve {
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyResolve.class);

	/**
	 * 从PV列表页面解析出PV数据集
	 * 
	 * @param pvbody
	 * @return pvList
	 */
	public List<PV> resolvePV(String pvbody) {
		List<PV> pvs = new ArrayList<PV>();
		int curPos = 0;

		while ((curPos = pvbody.indexOf("class=\"list_tbl\"")) != -1) {
			PV pv = new PV();

			// 任务编号
			pvbody = pvbody.substring(curPos);
			pv.setId(StringUtils.substringBetween(pvbody, "<!--span class=\"deeppurple\"-->", "<!--/span-->"));

			// 发布者
			curPos = pvbody.indexOf("class=\"mb3\"");
			pvbody = pvbody.substring(curPos);
			pv.setPublisher(StringUtils.substringBetween(pvbody, "<span>", "</span>"));

			// 访问次数/流量
			curPos = pvbody.indexOf("总流量：");
			pvbody = pvbody.substring(curPos);
			pv.setSumFlow(StringUtils.substringBetween(pvbody, "deepred\">", "</span>"));
			curPos = pvbody.indexOf("剩余流量：");
			pvbody = pvbody.substring(curPos);
			pv.setSurplusFlow(StringUtils.substringBetween(pvbody, "deepblue\">", "</span>"));

			// 发布时间,特殊要求
			curPos = pvbody.indexOf("<td");
			pvbody = pvbody.substring(curPos);
			pv.setReleaseTime(StringUtils.substringBetween(pvbody, "center\">", "<br"));
			String substringBetween = StringUtils.substringBetween(pvbody, "<br", "</td>");
			if (0 < substringBetween.indexOf("mission_pvshopin.png")) { // 进店再搜索
				pv.setIntoStoreAndSearch(true);
			}
			if (0 < substringBetween.indexOf("mission_pvwait.gif")) { // 停留5分钟
				pv.setStayFor5Minutes(true);
			}
			if (0 < substringBetween.indexOf("mission_tmall.png")) { // 天猫店
				pv.setTmall(true);
			}

			// 商品数
			curPos = pvbody.indexOf("deepred");
			pvbody = pvbody.substring(curPos);
			pv.setCommodityNumber(StringUtils.substringBetween(pvbody, "\">", "</span>"));

			// 接受url
			curPos = pvbody.indexOf("href");
			pvbody = pvbody.substring(curPos);
			pv.setUrl(StringUtils.substringBetween(pvbody, "=\"", "\""));

			pvs.add(pv);
		}

		return pvs;
	}

	/**
	 * 从PV任务列表页面解析出PVQuest数据集
	 * 
	 * @param pvQuestBody
	 * @return pvQuestList
	 */
	public List<PVQuest> resolvePVQuest(String pvQuestBody) {
		List<PVQuest> pvQuests = new ArrayList<PVQuest>();
		int curPos = 0;
		while ((curPos = pvQuestBody.indexOf("任务编号：")) != -1) {
			PVQuest pvQuest = new PVQuest();

			// 任务编号
			pvQuestBody = pvQuestBody.substring(curPos);
			pvQuest.setId(StringUtils.substringBetween(pvQuestBody, ");\">", "</a>"));

			// 发布人
			curPos = pvQuestBody.indexOf("发布方：");
			pvQuestBody = pvQuestBody.substring(curPos);
			pvQuest.setPublisher(StringUtils.substringBetween(pvQuestBody, "<span>", "</span>"));

			// 发布时间
			curPos = pvQuestBody.indexOf("赚取发布点");
			pvQuestBody = pvQuestBody.substring(curPos);
			pvQuest.setReleaseTime(StringUtils.substringBetween(pvQuestBody, "center\">", "</td>"));

			// questid
			curPos = pvQuestBody.indexOf("onclick=\"show");
			pvQuestBody = pvQuestBody.substring(curPos);
			pvQuest.setQuestid(StringUtils.substringBetween(pvQuestBody, "est(", ");\""));

			pvQuests.add(pvQuest);
		}

		return pvQuests;
	}

	/** 从PVQuestDetail详细信息中解析出PVQuest详细信息 */
	public void resolvePVQuestDetaileInfo(PVQuest pvQuest, String pvQuestDetailBody) {
		int curPos = 0;

		// 店主
		curPos = pvQuestDetailBody.indexOf("掌柜名称");
		try {
			pvQuestDetailBody = pvQuestDetailBody.substring(curPos);
		} catch (Exception e) {
			logger.error("----错误的PVQuest，查询关键字[{}]。错误信息：\r\n {}", pvQuest.getSearchKey(), e.getMessage());
		}

		pvQuest.setShopKeeper(StringUtils.substringBetween(pvQuestDetailBody, "\"blue\">", "</strong>"));

		// 接手时间
		curPos = pvQuestDetailBody.indexOf("接手时间");
		pvQuestDetailBody = pvQuestDetailBody.substring(curPos);
		pvQuest.setBoardTime(StringUtils.substringBetween(pvQuestDetailBody, "<td>", "</td>"));

		// 搜索关键字
		curPos = pvQuestDetailBody.indexOf("搜索关键字");
		pvQuestDetailBody = pvQuestDetailBody.substring(curPos);
		pvQuest.setSearchKey(StringUtils.substringBetween(pvQuestDetailBody, "');\">", "</textarea>"));

		// 搜索提示
		curPos = pvQuestDetailBody.indexOf("搜索提示");
		pvQuestDetailBody = pvQuestDetailBody.substring(curPos);
		String tip = StringUtils.substringBetween(pvQuestDetailBody, "readonly>", "</textarea>");
		String[] tips = tip.split("，");
		String money = tips[1]; // 钱
		int min = -1;
		int max = -1;
		try {
			min = Integer.valueOf(money.substring(0, money.indexOf('-'))).intValue(); // 最小价格
		} catch (NumberFormatException e) {
			min = 0;
		}
		try {
			max = Integer.valueOf(money.substring(money.indexOf('-') + 1, money.length() - 1)); // 最小价格
		} catch (NumberFormatException e) {
			max = 9999;
		}
		pvQuest.setPrice(min, max);
		pvQuest.setLocation(tips[2].substring(3)); // 所在地

		// 特殊条件
		if (pvQuestDetailBody.contains("mission_pvshopin.png")) { // 进店再搜索
			pvQuest.setIntoStoreAndSearch(true);
		}
		if (pvQuestDetailBody.contains("mission_pvwait.gif")) { // 停留5分钟
			pvQuest.setStayFor5Minutes(true);
		}
		if (pvQuestDetailBody.contains("mission_tmall.png")) { // 天猫店
			pvQuest.setTmall(true);
		}
	}

	/** 从商品页面解析出宝贝id集 */
	public List<String> resolveBaoBeiUrl(PVQuest pollPVQuest, String baobeipage) {
		List<String> itemids = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\">" + pollPVQuest.getShopKeeperPattern() + "</a>");
		Matcher matcher = pattern.matcher(baobeipage);
		while (matcher.find()) { // 找个一个匹配的
			int start = matcher.start();
			int end = baobeipage.indexOf("data-icon", start);
			String itemid = StringUtils.substringBetween(baobeipage.substring(start, end), "data-item=\"", "\"");
			if (StringUtils.isNotBlank(itemid) && NumberUtils.isNumber(itemid)) {
				itemids.add(itemid);
			}
		}
		return itemids;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("*", "gbk"));
		// Pattern pattern = Pattern.compile("\\*+");
		// Matcher matcher = pattern.matcher(this.shopKeeper);
		// return matcher.replaceFirst(".{" + length + "}");

		String sss = "<span class=\"J_WangWang\" data-nick=\">%E4%BA%abcd1234%E5%88\" data-display=\"inline\" data-item=\"38711056514\"";
		Pattern pattern = Pattern.compile("data-nick=\">%E4%BA%.{8}%E5%88\"");
		Matcher matcher = pattern.matcher(sss);
		System.out.println(matcher.find());
		System.out.println(matcher.start());

	}

	/**
	 * 解析任务列表
	 * 
	 * @param html
	 * @return
	 */
	public Collection<? extends Task> resolveTaskListHtml(String html) {
		List<Task> tasks = new ArrayList<Task>();
		int curPos = 0;

		int indexOf = html.indexOf("class=\"list_tbl\"") + 10;
		html = html.substring(curPos);

		while ((curPos = html.indexOf("<tr>")) != -1) {
			Task task = new Task();

			// 商品类型和编号
			html = html.substring(curPos);
			String td = StringUtils.substringBetween(html, "<td width=\"163\">", "</td>");
			resolveTaskNumberAndTaskType(task, td);

//			// 发布者
//			curPos = html.indexOf("<td width=\"145\"") - 2;
//			html = html.substring(curPos);
//			task.setPublisher(StringUtils.substringBetween(html, "<td", "</td>"));
//
//			// 访问次数/流量
//			curPos = html.indexOf("总流量：");
//			html = html.substring(curPos);
//			task.setSumFlow(StringUtils.substringBetween(html, "deepred\">", "</span>"));
//			curPos = html.indexOf("剩余流量：");
//			html = html.substring(curPos);
//			task.setSurplusFlow(StringUtils.substringBetween(html, "deepblue\">", "</span>"));
//
//			// 发布时间,特殊要求
//			curPos = html.indexOf("<td");
//			html = html.substring(curPos);
//			task.setReleaseTime(StringUtils.substringBetween(html, "center\">", "<br"));
//			String substringBetween = StringUtils.substringBetween(html, "<br", "</td>");
//			if (0 < substringBetween.indexOf("mission_pvshopin.png")) { // 进店再搜索
//				task.setIntoStoreAndSearch(true);
//			}
//			if (0 < substringBetween.indexOf("mission_pvwait.gif")) { // 停留5分钟
//				task.setStayFor5Minutes(true);
//			}
//			if (0 < substringBetween.indexOf("mission_tmall.png")) { // 天猫店
//				task.setTmall(true);
//			}
//
//			// 商品数
//			curPos = html.indexOf("deepred");
//			html = html.substring(curPos);
//			task.setCommodityNumber(StringUtils.substringBetween(html, "\">", "</span>"));
//
//			// 接受url
//			curPos = html.indexOf("href");
//			html = html.substring(curPos);
//			task.setUrl(StringUtils.substringBetween(html, "=\"", "\""));

			tasks.add(task);
		}

		return tasks;
	}

	/** 解析商品类型和编号 */
	private void resolveTaskNumberAndTaskType(Task task, String td) {
		// &nbsp;<img src="/style/images/realobject.gif" align="absmiddle"
		// title="该任务为实物商品" /> 2014616143449233
		if (0 < td.indexOf("realobject.gif")) { // 实物商品
			task.setTaskType(TaskType.实物);
		} else if (0 < td.indexOf("realobject_shop.gif")) { // 实购商品
			task.setTaskType(TaskType.实购);
		} else if (0 < td.indexOf("virtual.gif")) { // 虚拟
			task.setTaskType(TaskType.虚拟);
		} else if (0 < td.indexOf("virtual_shop.gif")) { // 虚购商品
			task.setTaskType(TaskType.虚购);
		} else {
			task.setTaskType(TaskType.其它);
		}

		task.setNumber(StringUtils.trimToNull(td.split("/>")[1]));
	}
}
