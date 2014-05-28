package com.r.app.taobaoshua.yuuboo;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.taobao.TaoBao;
import com.r.app.taobaoshua.yuuboo.model.PV;
import com.r.app.taobaoshua.yuuboo.model.PVQuest;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;
import com.r.core.httpsocket.context.ResponseHeader;
import com.r.core.httpsocket.context.responseheader.ResponseStatus;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

public class YuuBooManger {
	private static final Logger logger = LoggerFactory.getLogger(YuuBooManger.class);
	private static YuuBoo yuuBoo = YuuBoo.getInstance();

	public YuuBooManger() {
		super();
		logger.debug("YuuBooManger   newInstance  ................");
	}

	// 优保部分
	/**
	 * 获取登陆时的验证码图片
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 */
	public Image getLoginCaptchaImage() throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/checkcode.php?id=");
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
	 * @param question
	 *            密保问题
	 * @param answer
	 *            密保答案
	 * @return 登陆成功后的body
	 * @throws IOException
	 */
	public String login(String account, String accountPassword, String captcha, String question, String answer) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		StringBuilder post = new StringBuilder();
		try {
			account = URLEncoder.encode(account, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		post.append("username=").append(account);
		try {
			accountPassword = URLEncoder.encode(accountPassword, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		post.append("&password=").append(accountPassword);
		post.append("&checkcodestr=").append(captcha);
		if (StringUtils.isNotBlank(answer) && StringUtils.isNotBlank(question)) {
			try {
				answer = URLEncoder.encode(answer, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			post.append("&answer=").append(answer);

			try {
				question = URLEncoder.encode(question, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			post.append("&question=").append(question);
		}
		post.append("&dosubmit=1&forward=http%3A%2F%2Fwww.yuuboo.net%2Fmember%2Findex.php&btnSubmit.x=82&btnSubmit.y=13");
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/login.php", post.toString(), null);
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
	public String getPVList(int page) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/quest.php?type=pv&page=" + page);
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
	public String getPVQuestList(int page) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/doquest.php?type=pv&status=2&page=" + page);
		return responseHeader.bodyToString();
	}

	/**
	 * 获得pv任务详细信息
	 * 
	 * @throws IOException
	 */
	public String showQuest(PVQuest pvQuest) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/questinfo.php?questid=" + pvQuest.getQuestid());
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
	public boolean takeTask(PV pv) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send(pv.getUrl());
		String body = responseHeader.bodyToString();

		String substringBetween = StringUtils.substringBetween(body, "alert('", "')");
		logger.debug(substringBetween);
		if (StringUtils.isNotBlank(substringBetween)) {
			if (substringBetween.contains("接手任务成功")) {
				return true;
			}
			if (substringBetween.contains("同一IP一天只能接手30个来路流量任务")) {
				return true;
			}
			if (substringBetween.contains("一个平台号一天只能接手同一个流量地址1次")) {
				return false;
			}
			if (substringBetween.contains("请重新接手其他任务")) {
				return false;
			}
		} else {
			logger.debug(body);
		}

		return false;
	}

	/**
	 * 校验url是否正确
	 * 
	 * @param pvQuest
	 * @param itemid
	 *            宝贝id
	 * @throws IOException
	 */
	public boolean checkTaskUrl(PVQuest pvQuest, final String itemid) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/questinfo.php?questid=" + pvQuest.getQuestid() + "&type=pv&act=isend&idd=" + itemid);
		String body = responseHeader.bodyToString();
		if (0 <= body.indexOf("你已经获得发布点")) {
			// 验证成功后,进入相映itemid的宝贝页面,给别个增加一个流量....虽然刷,但是还是不要太过分了.
			TaskUtil.executeSequenceTask(new Runnable() {
				@Override
				public void run() {
					try {
						HttpSocket taobaoSocket = yuuBoo.getYuuBooSocket();
						taobaoSocket.send("http://item.taobao.com/item.htm?id=" + itemid);
					} catch (Exception e) {
						throw new NetworkIOReadErrorException("进入淘宝宝贝详情页失败!", e);
					}
				}
			});
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
	public boolean cancelTask(PVQuest pvQuest) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = yuuBoo.getYuuBooSocket();
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/questinfo.php?questid=" + pvQuest.getQuestid() + "&type=pv&act=remove");
		String body = responseHeader.bodyToString();
		if (0 <= body.indexOf("撤销接手成功")) {
			logger.info("成功撤销搜索关键字为[{}]的宝贝的流量任务!", pvQuest.getSearchKey());
			return true;
		} else {
			logger.info("--撤销搜索关键字为[{}]的宝贝的流量任务失败!", pvQuest.getSearchKey());
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
	public String search(PVQuest pvQuest, int page) throws NetworkIOReadErrorException {
		HttpSocket httpSocket = TaoBao.getInstance().getNewTaoBaoSocket();
		ResponseHeader responseHeader = httpSocket.send(pvQuest.getTaobaoSearchAddr(page));
		return responseHeader.bodyToString();
	}

	/**
	 * 查询宝贝的真实"所在地"
	 * 
	 * @throws IOException
	 */
	public void searchLoc(PVQuest pvQuest) throws NetworkIOReadErrorException {
		String location = pvQuest.getLocation();
		logger.info("questid : {}  搜索预置所在地[{}]的真实所在地!", pvQuest.getQuestid(), location);
		if (PVQuest.checkLoc(location)) {
			logger.info("questid : {}  经过预置的\"所在地\"词典校验，关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地 : {} 正确无误，可以使用。", pvQuest.getQuestid(), pvQuest.getSearchKeyCut(15), pvQuest.getPriceMin(), pvQuest.getPriceMax(), location);
			return;
		}
		if (StringUtils.isNotBlank(location) && 2 <= location.length()) {
			HttpSocket httpSocket = TaoBao.getInstance().getNewTaoBaoSocket();
			ResponseHeader responseHeader = null;
			String html = null;
			String loc = null;
			int right = 2;

			while (true) {
				loc = StringUtils.right(location, right++);
				pvQuest.setLocation(loc);
				responseHeader = httpSocket.send(pvQuest.checkSearchAddr());
				html = responseHeader.bodyToString();

				if (html.contains("点击返回上一步")) { // 截取出的所在地不存在
					if (loc.equals(location)) { // 截取出的所在地和原所在地相等,则设置成通用所在地"all"后返回
						pvQuest.setLocation("all");
						logger.info("questid : {}  搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地不存在,所以设置成通用所在地 : all", pvQuest.getQuestid(), pvQuest.getSearchKeyCut(15), pvQuest.getPriceMin(), pvQuest.getPriceMax());
						return;
					} else { // 截取出的所在地和原所在地不相等,则继续截取

					}
				} else { // 截取出的所在地存在,则直接设置此所在地后跳出
					pvQuest.setLocation(loc);
					logger.info("questid : {}  成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地 : {}", pvQuest.getQuestid(), pvQuest.getSearchKeyCut(15), pvQuest.getPriceMin(), pvQuest.getPriceMax(), loc);
					return;
				}
			}
		} else {
			pvQuest.setLocation("all");
			logger.info("3 : questid : {}  成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝的真实所在地 : {}", pvQuest.getQuestid(), pvQuest.getSearchKeyCut(15), pvQuest.getPriceMin(), pvQuest.getPriceMax(), "all");
		}
	}

	/** 校验能否通过代理进入友保 */
	public String checkYuuBoo(Proxy proxy) {
		HttpSocket httpSocket = HttpSocket.newHttpSocket(false, HttpProxy.newInstance(true, proxy));
		ResponseHeader responseHeader = httpSocket.send("http://www.yuuboo.net/member/login.php");
		if (ResponseStatus.s200.equals(responseHeader.getStatus())) {
			return responseHeader.bodyToString();
		}
		return null;
	}
}
