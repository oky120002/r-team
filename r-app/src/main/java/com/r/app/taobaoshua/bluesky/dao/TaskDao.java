/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import java.awt.Image;

import org.hibernate.Session;

import com.r.app.taobaoshua.bluesky.core.AbstractDao;
import com.r.app.taobaoshua.bluesky.model.BuyAccount;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.core.httpsocket.context.HttpProxy;

/**
 * 用户Dao
 * 
 * @author rain
 */
public interface TaskDao extends AbstractDao<Task> {

	/** 获取当前数据库链接Session */
	public Session getSession();

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
	String login(String account, String accountPassword, String captcha, String question, String answer);

	/** 获取验证码图片 */
	Image getLoginCaptchaImage();

	/**
	 * 获取任务列表html
	 * 
	 * @param page
	 *            任务列表页码
	 * @param type
	 *            列表类型<br />
	 *            0:默认类型<br />
	 *            1:所有任务<br />
	 *            2:等待接手<br />
	 *            3:虚拟<br />
	 *            4:实物<br />
	 *            5:套餐<br />
	 *            6:立即<br />
	 *            7:30分<br />
	 *            8:1天<br />
	 *            9:2天<br />
	 *            10:3天<br />
	 *            11:4天<br />
	 *            12:5天<br />
	 *            13:6天<br />
	 *            14:7天<br />
	 * @param order
	 *            搜索排序<br />
	 *            0:默认排序<br />
	 *            1:担保金从高到低<br />
	 *            2:担保金从低到高<br />
	 *            3:发布点从高到低<br />
	 *            4:发布点从低到高<br />
	 * @return
	 */
	String getTaskListHtml(int page, int type, int order);

	/** 获取任务详细信息 */
	String getTaskDetail(String taskid);

	/** 接手任务 */
	String acceptTask(Task task);

	/** 退出任务 */
	String discardTask(Task task);

	/** 获取绑定的买号 */
	String getTaoBaoAccount();

	/** 绑定买号 */
	String bindingBuyAccount(Task task, BuyAccount buyAccount);

	/**检测宝贝地址是否正确*/
	String checkItemAddr(Task task, String itemAddr);

	/**支付*/
    String pay(Task task);
}
