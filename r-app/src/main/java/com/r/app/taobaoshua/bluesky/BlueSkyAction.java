/**
 * 
 */
package com.r.app.taobaoshua.bluesky;

import java.awt.Image;

import org.apache.commons.lang3.StringUtils;

import com.r.core.httpsocket.context.HttpProxy;

/**
 * @author oky
 * 
 */
public class BlueSkyAction {
	private static final BlueSky blueSky = BlueSky.getInstance();

	/** 获取ip地址 */
	public String getNetWorkIp() {
		String html = BlueSky.getInstance().getBlueSkyManger().getNetWorkIp();
		return StringUtils.substringBetween(html, "<code>", "</code>");
	}

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy proxy) {
		blueSky.getBlueSkyManger().setSocketProxy(proxy);
	}

	/** 获取验证码 */
	public Image getLoginBlueSkyCaptchaImage() {
		return blueSky.getBlueSkyManger().getLoginCaptchaImage();
	}
}
