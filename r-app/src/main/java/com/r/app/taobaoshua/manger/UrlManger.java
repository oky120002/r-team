package com.r.app.taobaoshua.manger;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.exception.YouBaoException;
import com.r.app.taobaoshua.model.PV;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class UrlManger {
	private static final Logger logger = LoggerFactory.getLogger(UrlManger.class);
	private static TaobaoShuaApp app = TaobaoShuaApp.getInstance();

	public UrlManger() {
		super();
		logger.debug("Init UrlManger.......");
	}

	/**
	 * 获取登陆时的验证码图片
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 * @throws IOException
	 *             图片流IO读取错误
	 */
	public Image getLoginCaptchaImage() throws NetworkIOReadErrorException, IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.net/checkcode.php?id=");
		return responseHeader.bodyToImage();
	}

	/**
	 * 登陆
	 * 
	 * @param account
	 *            友保账号
	 * @param accountPassword
	 *            友保密码
	 * @param captcha
	 *            验证码
	 * @return 登陆成功后的body
	 * @throws IOException
	 */
	public String login(String account, String accountPassword, String captcha) throws IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		StringBuilder post = new StringBuilder();
		post.append("username=");
		post.append(account);
		post.append("&password=");
		try {
			post.append(URLEncoder.encode(accountPassword, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			post.append(accountPassword);
		}
		post.append("&checkcodestr=");
		post.append(captcha);
		post.append("&dosubmit=1&forward=http%3A%2F%2Fyuuboo.net%2Fmember%2Findex.php&btnSubmit.x=82&btnSubmit.y=13");
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.net/member/login.php", post.toString(), null);
		return responseHeader.bodyToString();
	}

	/**
	 * 获得pv列表body
	 * 
	 * @param page
	 *            获取第几页的PV列表数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getPVList(int page) throws IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.net/quest.php?type=pv&page=" + page);
		return responseHeader.bodyToString();
	}

	/**
	 * 接任务
	 * 
	 * @param pv
	 * @return
	 * @throws IOException
	 * @throws YouBaoException
	 *             不支持完成某种特殊条件的任务时,抛出此异常
	 */
	public String takeTask(PV pv) throws IOException, YouBaoException {
		if (pv.isIntoStoreAndSearch()) {
			// FIXME r-app:taobaoshua 添加此支持"进店后再次搜索"
			throw new YouBaoException("暂时不支持\"进店后再次搜索\"任务");
		}
		if (pv.isStayFor5Minutes()) {
			// FIXME r-app:taobaoshua 添加此支持"停留5分钟"
			throw new YouBaoException("暂时不支持\"停留5分钟\"任务");
		}

		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send(pv.getUrl());
		return responseHeader.bodyToString();
	}
}
