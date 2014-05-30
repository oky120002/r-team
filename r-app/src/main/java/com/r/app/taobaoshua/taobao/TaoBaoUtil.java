/**
 * 
 */
package com.r.app.taobaoshua.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.r.core.exceptions.arg.ArgNullException;

/**
 * @author oky
 * 
 */
public class TaoBaoUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 
	 * @param key
	 *            搜索的关键字
	 * @param location
	 *            宝贝所在地
	 * @param isTmall
	 *            是否天猫店,false则搜索包括天猫在内定所有店铺
	 * @param priceMin
	 *            最低售价
	 * @param priceMax
	 *            最高售价
	 * @param page
	 *            搜索的页数
	 * @return
	 */
	public static String reloveTaobaoSearchAddr(String key, String location, boolean isTmall, int priceMin, int priceMax, int page) {
		if (StringUtils.isBlank(key)) {
			throw new ArgNullException("解析淘宝搜索url时,不能传入的搜索关键字");
		}
		if (page < 1) {
			page = 1;
		}
		if (StringUtils.isBlank(location)) {
			location = "all";
		}

		StringBuilder url = new StringBuilder();
		url.append("http://s.taobao.com/search?");
		url.append("tab=").append(isTmall ? "mall" : "all");
		url.append("&q=").append(convertSearchKey(key)); // 必须存在
		url.append("&loc=").append(convertLocation(location));
		// 增加一个判断.就是发布任务的可能就是要人不输入价格.填写价格 0 0(我这个0 1是set时做了处理)
		if (0 <= priceMin && 0 < priceMax) {
			url.append("&filter=reserve_price[").append(priceMin).append(',').append(priceMax).append(']');
		}
		url.append("&filterFineness=2&promote=0");
		url.append("&fs=0&stats_click=search_radio_all%3A1&initiative_id=staobaoz_").append(sdf.format(new Date()));
		url.append("&s=").append((page - 1) * 40).append("#J_relative");
		return url.toString();
	}

	/** 转换搜索条件 */
	private static String convertSearchKey(String key) {
		try {
			return URLEncoder.encode(key, "gbk");
		} catch (UnsupportedEncodingException e) {
		}
		return key;
	}
	/** 转换所在地 */
	private static String convertLocation(String loc) {
		if (StringUtils.isBlank(loc)) {
			return "all";
		}
		loc = loc.trim();
		// 过滤特殊地址
		switch (loc) {
		case "江沪浙":
			loc = "江苏,浙江,上海";
			break;
		case "港澳台":
			loc = "香港,澳门,台湾";
			break;
		case "珠三角":
			loc = "广州,深圳,中山,珠海,佛山,东莞,惠州";
			break;
		case "京津":
			loc = "北京,天津";
			break;
		case "江浙沪皖":
			loc = "江苏,浙江,上海,安徽";
			break;
		case "东三省":
			loc = "黑龙江,吉林,辽宁";
			break;
		case "京津冀":
			loc = "北京,天津,河北";
		case "海外":
			loc = "美国,英国,法国,瑞士,澳洲,新西兰,加拿大,奥地利,韩国,日本,德国,意大利,西班牙,俄罗斯,泰国,印度,荷兰,新加坡,其它国家";
			break;
		default:
			break;
		}
		try {
			loc = URLEncoder.encode(loc, "gbk");
		} catch (UnsupportedEncodingException e) {
		}
		return loc;
	}
}
