package com.r.app.taobaoshua.action;

import java.awt.Image;
import java.io.IOException;
import java.util.Set;

import com.r.app.taobaoshua.exception.YouBaoException;
import com.r.app.taobaoshua.manger.UrlManger;
import com.r.app.taobaoshua.manger.UrlResolve;
import com.r.app.taobaoshua.model.PV;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

public class Action {
	private static final Logger logger = LoggerFactory.getLogger(Action.class); // 日志
	private UrlManger urlManger = new UrlManger(); // Url管理器
	private UrlResolve resolve = new UrlResolve(); // Url解析器

	public Action() {
		super();
		logger.info("Init Action ..........");
	}

	/**
	 * 获取登陆验证码图片
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 * @throws IOException
	 *             图片流IO读取错误
	 */
	public Image getLoginCaptchaImage() throws NetworkIOReadErrorException, IOException {
		return urlManger.getLoginCaptchaImage();
	}

	/**
	 * 登陆
	 * 
	 * @param account
	 * @param accountPassword
	 * @param captcha
	 * @throws IOException
	 */
	public void login(String account, String accountPassword, String captcha) throws IOException {
		String login = urlManger.login(account, accountPassword, captcha);
		logger.debug("登陆成功后的body文本 : {}", login);
	}

	/**
	 * 获取来路流量数据集
	 * 
	 * @param curPage
	 *            页数
	 * 
	 * @return
	 * @throws IOException
	 */
	public Set<PV> getPVList(int page) throws IOException {
		return resolve.resolvePV(urlManger.getPVList(page));
	}

	/**
	 * 接任务<br />
	 * 
	 * @param pv
	 *            流量任务
	 * @return true:接手成功 false:接手失败
	 * @throws IOException
	 */
	public boolean takeTask(PV pv) throws IOException {
		String result = null;
		try {
			result = urlManger.takeTask(pv);
		} catch (YouBaoException e) {
			return false;
		}

		if (0 <= result.indexOf("接手任务成功")) {
			return true;
		}
		return false;

		// <script language='javascript'
		// type='text/javascript'>if('接手任务成功，你必须在30分钟内按照任务的提示在淘宝搜索卖家的宝贝，然后回平台确认浏览。'!='')
		// {
		// alert('接手任务成功，你必须在30分钟内按照任务的提示在淘宝搜索卖家的宝贝，然后回平台确认浏览。');}window.location.href='http://dx.yuuboo.net/member/doquest.php?type=pv&status=2'</script>

	}
}
