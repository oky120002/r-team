package com.r.app.taobaoshua.manger;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.exception.YouBaoException;
import com.r.app.taobaoshua.model.PV;
import com.r.app.taobaoshua.model.PVQuest;
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

	// 优保部分
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
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/checkcode.php?id=");
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
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/member/login.php", post.toString(), null);
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
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/quest.php?type=pv&page=" + page);
		return responseHeader.bodyToString();
	}

	/**
	 * 获得pv任务列表body
	 * 
	 * @param page
	 *            获取第几页的PV列表数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getPVQuestList(int page) throws IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/member/doquest.php?type=pv&status=2&page=" + page);
		return responseHeader.bodyToString();
	}

	/**
	 * 获得pv任务详细信息
	 * 
	 * @throws IOException
	 */
	public String showQuest(PVQuest pvQuest) throws IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/member/questinfo.php?questid=" + pvQuest.getQuestid());
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
	public boolean takeTask(PV pv) throws IOException, YouBaoException {
		if (pv.isIntoStoreAndSearch()) {
			// FIXME r-app:taobaoshua 添加此支持"进店后再次搜索"
			throw new YouBaoException("暂时不支持\"进店后再次搜索\"任务");
		}
		if (pv.isStayFor5Minutes()) {
			// FIXME r-app:taobaoshua 添加此支持"停留5分钟"
			throw new YouBaoException("暂时不支持\"停留5分钟\"任务");
		}

		if (pv.isTmall()) {
			// FIXME r-app:taobaoshua 添加此支持"天猫"
			throw new YouBaoException("暂时不支持\"天猫\"任务");
		}

		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send(pv.getUrl());
		String body = responseHeader.bodyToString();

		if (0 <= body.indexOf("接手任务成功")) {
			return true;
		} else {
			logger.debug(body);
			return false;
		}
	}

	/**
	 * 校验url是否正确
	 * 
	 * @param pvQuest
	 * @param itemid
	 *            宝贝id
	 * @throws IOException
	 */
	public boolean checkTaskUrl(PVQuest pvQuest, String itemid) throws IOException, YouBaoException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/member/questinfo.php?questid=" + pvQuest.getQuestid() + "&type=pv&act=isend&idd=" + itemid);
		String body = responseHeader.bodyToString();
		if (0 <= body.indexOf("你已经获得发布点")) {
			return true;
		}
		return false;
	}

	/**
	 * 撤销此任务
	 * 
	 * @param pvQuest
	 * 
	 * @throws IOException
	 */
	public boolean cancelTask(PVQuest pvQuest) throws IOException {
		HttpSocket httpSocket = app.getYoubaoSocket();
		ResponseHeader responseHeader = httpSocket.send("http://dx.yuuboo.com/member/questinfo.php?questid=" + pvQuest.getQuestid() + "&type=pv&act=remove");
		String body = responseHeader.bodyToString();
		if (0 <= body.indexOf("撤销接手成功")) {
			logger.debug("成功撤销搜索关键字为[{}]的宝贝的流量任务!", pvQuest.getSearchKey());
			return true;
		} else {
			logger.debug("--撤销搜索关键字为[{}]的宝贝的流量任务失败!", pvQuest.getSearchKey());
			return false;
		}
	}

	// 淘宝部分
	/**
	 * 查询宝贝
	 * 
	 * @param pvQuest
	 *            PVQuest
	 * @param page
	 *            查询的页数
	 * @return
	 * @throws IOException
	 */
	public String search(PVQuest pvQuest, int page) throws IOException {
		HttpSocket httpSocket = app.getTaobaoSocket();
		ResponseHeader responseHeader = httpSocket.send(pvQuest.getTaobaoSearchAddr(page));
		return responseHeader.bodyToString();
	}

	/**
	 * 查询宝贝的真实"所在地"
	 * 
	 * @throws IOException
	 */
	public void searchLoc(PVQuest pvQuest) throws IOException {
		String location = pvQuest.getLocation();
		logger.debug("校验关键字为[{}]的宝贝的预置地址是否正确 : {}", pvQuest.getSearchKey(), location);
		if (StringUtils.isNotBlank(location) && 2 <= location.length()) {
			HttpSocket httpSocket = app.getTaobaoSocket();
			ResponseHeader responseHeader = null;
			int right = 2;
			String html = "点击返回上一步";

			while (true) {
				String loc = StringUtils.right(location, right++);
				//
				responseHeader = httpSocket.send(pvQuest.getTaobaoSearchAddr(1));
				html = responseHeader.bodyToString();

				if (0 <= html.indexOf("点击返回上一步")) { // 截取出的所在地不存在
					if (loc.equals(location)) { // 截取出的所在地和原所在地相等,则设置成通用所在地"all"后返回
						pvQuest.setLocation("all");
						logger.debug("搜索到关键字为[{}]的宝贝的真实所在地不存在,所以设置成通用所在地 : all", pvQuest.getSearchKey());
						return;
					} else { // 截取出的所在地和原所在地不相等,则继续截取

					}
				} else { // 截取出的所在地存在,则直接设置此所在地后跳出
					pvQuest.setLocation(loc);
					logger.debug("成功搜索到关键字为[{}]的宝贝的真实所在地 : {}", pvQuest.getSearchKey(), loc);
					return;
				}
			}
		} else {
			pvQuest.setLocation("all");
			logger.debug("成功搜索到关键字为[{}]的宝贝的真实所在地 : {}", pvQuest.getSearchKey(), "all");
		}
	}
}
