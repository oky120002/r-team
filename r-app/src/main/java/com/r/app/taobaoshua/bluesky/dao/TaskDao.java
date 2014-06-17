/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import java.awt.Image;

import com.r.app.taobaoshua.bluesky.core.AbstractDao;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.core.httpsocket.context.HttpProxy;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface TaskDao extends AbstractDao<Task> {

	/** 设置当前链接的代理 */
	void setSocketProxy(HttpProxy httpProxy);
	
	/**
	 * 登陆蓝天店主网
	 * 
	 * @param account
	 * @param accountPassword
	 * @param captcha
	 * @param question
	 * @param answer
	 */
	void login(String account, String accountPassword, String captcha, String question, String answer) ;

	/** 获取验证码图片 */
	Image getLoginCaptchaImage();

	/** 获取验证码图片 */
	String getTaskListHtml(int page);
	
	
}
