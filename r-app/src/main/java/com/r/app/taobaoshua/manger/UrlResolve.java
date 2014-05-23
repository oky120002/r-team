package com.r.app.taobaoshua.manger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.r.app.taobaoshua.model.PV;
import com.r.app.taobaoshua.model.PVQuest;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class UrlResolve {
	private static final Logger logger = LoggerFactory.getLogger(UrlResolve.class);

	/**
	 * 从PV列表页面解析出PV数据集
	 * 
	 * @param pvbody
	 * @return pvList
	 */
	public List<PV> resolvePV(String pvbody) {
		List<PV> pvs = new ArrayList<PV>();
		int curPos = 0;

		while ((curPos = pvbody.indexOf("任务编号：")) != -1) {
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
		Pattern pattern = Pattern.compile(pollPVQuest.getShopKeeperPattern());
		Matcher matcher = pattern.matcher(baobeipage);
		while (matcher.find()) { // 找个一个匹配的
			int curPos = matcher.start();
			int start = baobeipage.indexOf("<div", curPos);
			int end = baobeipage.indexOf("</div>", start);
			String itemid = StringUtils.substringBetween(baobeipage.substring(start, end), "data-item=\"", "\" data-icon");
			if (StringUtils.isNotBlank(itemid) && NumberUtils.isNumber(itemid)) {
				itemids.add(itemid);
			}
		}
		return itemids;
	}
}
