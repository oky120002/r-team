/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.app.taobaoshua.bluesky.dao;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.r.app.taobaoshua.bluesky.core.AbstractDaoImpl;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 用户DaoImpl
 * 
 * @author rain
 */
@Repository("taskDao")
public class TaskDaoImpl extends AbstractDaoImpl<Task> implements TaskDao {

	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class); // 日志

	private HttpSocket httpSocket = HttpSocket.newHttpSocket(true, null);
	private HttpSocket noCookHttpSocket = HttpSocket.newHttpSocket(false, null);

	public TaskDaoImpl() {
		super(Task.class);
		logger.info("TaskDaoImpl Instance............................");
		httpSocket.setTimeout(60_000); // 1分钟超时
		noCookHttpSocket.setTimeout(60_000); // 1分钟超时
	}

	/** 设置当前链接的代理 */
	@Override
	public void setSocketProxy(HttpProxy httpProxy) {
		this.httpSocket.setProxy(httpProxy);
		this.noCookHttpSocket.setProxy(httpProxy);
	}

	/** 获取验证码图片 */
	@Override
	public Image getLoginCaptchaImage() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Accept", "image/webp,*/*;q=0.8");
		map.put("Accept-Encoding", "gzip,deflate");
		map.put("Accept-Language", "zh-CN,zh;q=0.8");
		map.put("Host", "www2.88sxy.com");
		map.put("Referer", "http://www2.88sxy.com/user/login/");
		map.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36");
		ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/plus/verifycode.asp?n=", map);
		return responseHeader.bodyToImage();
	}

	/**
	 * 登陆蓝天店主网
	 * 
	 * @param account
	 * @param accountPassword
	 * @param captcha
	 * @param question
	 * @param answer
	 */
	@Override
	public void login(String account, String accountPassword, String captcha, String question, String answer) {

	}

	/** 获取任务列表html */
	@Override
	public String getTaskListHtml(int page, int type, int order) {
		page = 10 < page ? 10 : page;
		page = page < 1 ? 1 : page;
		type = 14 < type ? 2 : type;
		type = type < 0 ? 2 : type;
		order = 4 < order ? 3 : order;
		order = order < 0 ? 3 : order;

		// 第三个数字 1:淘宝任务,2:拍拍任务
		ResponseHeader responseHeader = httpSocket.send("http://www2.88sxy.com/task/?" + type + "-" + order + "-1-0-" + page);
		return responseHeader.bodyToString("gb2312");
	}

}
