package com.r.app.taobaoshua.manger;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.model.PV;

public class UrlResolve {

	/**
	 * 从PV列表页面解析出PV数据集
	 * 
	 * @param pvList
	 * @return
	 */
	public Set<PV> resolvePV(String pvbody) {
		Set<PV> pvs = new HashSet<PV>();
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
}
