/**
 * 
 */
package com.r.app.taobaoshua.bluesky;

import java.awt.Image;

import com.r.core.httpsocket.context.HttpProxy;

/**
 * @author oky
 * 
 */
public class BlueSkyAction {
	private static final BlueSky blueSky = BlueSky.getInstance();

	/** 设置当前链接的代理 */
	public void setSocketProxy(HttpProxy proxy) {
		blueSky.getManger().setSocketProxy(proxy);
	}

	/** 获取验证码 */
	public Image getLoginBlueSkyCaptchaImage() {
		return blueSky.getManger().getLoginCaptchaImage();
	}

	/**
	 * 登陆
	 * 
	 * @param account
	 *            账户
	 * @param accountPassword
	 *            账户密码
	 * @param captcha
	 *            验证码
	 * @param question
	 *            密保问题
	 * @param answer
	 *            密保答案
	 */
	public void login(String account, String accountPassword, String captcha, String question, String answer) {
		blueSky.getManger().login(account, accountPassword, captcha, question, answer);
	}
}
