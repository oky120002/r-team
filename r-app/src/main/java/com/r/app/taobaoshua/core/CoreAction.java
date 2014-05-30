/**
 * 
 */
package com.r.app.taobaoshua.core;

import org.apache.commons.lang3.StringUtils;

/**
 * @author oky
 * 
 */
public class CoreAction {

	/** 获取ip地址 */
	public String getNetWorkIp() {
		String html = Core.getInstance().getCoreManger().getNetWorkIp();
		return StringUtils.substringBetween(html, "<code>", "</code>");
	}
}
