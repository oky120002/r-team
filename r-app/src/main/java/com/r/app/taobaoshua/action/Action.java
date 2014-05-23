package com.r.app.taobaoshua.action;

import java.awt.Image;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.data.DataContext;
import com.r.app.taobaoshua.exception.YouBaoException;
import com.r.app.taobaoshua.manger.UrlManger;
import com.r.app.taobaoshua.manger.UrlResolve;
import com.r.app.taobaoshua.model.PV;
import com.r.app.taobaoshua.model.PVQuest;
import com.r.core.exceptions.LogginErrorException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

public class Action {
	private static final Logger logger = LoggerFactory.getLogger(Action.class); // 日志
	private static final DataContext dataContext = TaobaoShuaApp.getInstance().getDataContext();
	private UrlManger urlManger = new UrlManger(); // Url管理器
	private UrlResolve resolve = new UrlResolve(); // Url解析器
	private boolean isPVListRefreshExecuting = false; // pvlist刷新是否正在执行中
	private boolean isPVQuestListRefreshExecuting = false; // pv任务list刷新是否正在执行中
	private boolean isStartExecCommanding = false; // 是否正在执行流量任务
	private boolean isAutoTakeTaskCommanding = false; // 是否自动接手任务

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
	public Image getLoginCaptchaImage() {
		return urlManger.getLoginCaptchaImage();
	}

	/**
	 * 登陆
	 * 
	 * @param account
	 * @param accountPassword
	 * @param captcha
	 * @param question
	 * @param answer
	 * @throws IOException
	 * @throws LogginErrorException
	 *             验证码错误
	 */
	public void login(String account, String accountPassword, String captcha, String question, String answer) throws LogginErrorException {
		String login = urlManger.login(account, accountPassword, captcha, question, answer);
		logger.debug("登陆成功后的body文本 : {}", login);
		if (0 <= login.indexOf("验证码不正确")) {
			throw new LogginErrorException("验证码错误,请重新登陆", 1);
		}
		if (0 <= login.indexOf("为确认你的身份")) {
			throw new LogginErrorException("为确认你的身份,请登陆网站进行身份认证", 2);
		}
	}

	/**
	 * 获取来路流量数据集
	 * 
	 * @throws SchedulerException
	 *             自动获取PV列表功能失败时,抛出此异常
	 */
	public void startPVListCommand() throws SchedulerException {
		if (isPVListRefreshExecuting) {
			return;
		}
		isPVListRefreshExecuting = true;
		logger.debug("开启PV自动获取列表功能，每{}秒获取一次............ ", String.valueOf(dataContext.getPVListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 4;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000));// 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				List<PV> pvList = resolve.resolvePV(urlManger.getPVList(curPage++));
				if (CollectionUtils.isEmpty(pvList)) {
					curPage = 1; // 重置页数到第一页
					pvList = resolve.resolvePV(urlManger.getPVList(curPage++));
				}
				if (CollectionUtils.isNotEmpty(pvList)) {
					dataContext.addPVs(pvList);
				}
			}
		}, -1, dataContext.getPVListRefreshInterval(), null, null);
	}

	/**
	 * 初始化PV任务列表
	 * 
	 * @throws SchedulerException
	 *             自动获取列PV任务表功能失败时,抛出此异常
	 */
	public void startPVQuestListCommand() throws SchedulerException {
		if (isPVQuestListRefreshExecuting) {
			return;
		}
		isPVQuestListRefreshExecuting = true;
		logger.debug("开启PV任务自动获取列表功能，每{}秒获取一次............ ", String.valueOf(dataContext.getPVQuestListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 1;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000)); // 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				List<PVQuest> pvQuestList = resolve.resolvePVQuest(urlManger.getPVQuestList(curPage++));
				if (CollectionUtils.isEmpty(pvQuestList)) {
					curPage = 1; // 重置页数到第一页
					pvQuestList = resolve.resolvePVQuest(urlManger.getPVQuestList(curPage++));
				}
				if (CollectionUtils.isNotEmpty(pvQuestList)) {
					for (PVQuest pvQuest : pvQuestList) {
						if (!dataContext.containsPVQuest(pvQuest)) {
							resolve.resolvePVQuestDetaileInfo(pvQuest, urlManger.showQuest(pvQuest));
							dataContext.addPVQuests(pvQuest);
						}
					}
				}
			}
		}, -1, dataContext.getPVQuestListRefreshInterval(), null, null);
	}

	/**
	 * 自动接手任务<br />
	 * 
	 * @param pv
	 *            流量任务
	 * @return true:接手成功 false:接手失败
	 * @throws SchedulerException
	 *             自动接手任务失败时,抛出此异常
	 */
	public void startAutoTakeTask() throws SchedulerException {
		if (isAutoTakeTaskCommanding) {
			return;
		}
		isAutoTakeTaskCommanding = true;
		logger.debug("开启自动接手任务功能，每{}秒获取一次............ ", String.valueOf(dataContext.getPVTakeTaskIntervalTime()));

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(10_000)); // 随机延迟0到10_000毫秒,主要规避友保的防刷任务机制
				while (!takeTask())
					;
			}

			private boolean takeTask() {
				PV pollPV = dataContext.pollPV();
				if (pollPV != null) {
					try {
						if (urlManger.takeTask(pollPV)) {
							return true;
						}
					} catch (YouBaoException e) {
						logger.debug(e.getMessage(), e);
					}
					dataContext.addPVFailTaskId(pollPV.getId());
					TaskUtil.sleep(500);
					return false;
				}
				return true;
			}
		}, -1, dataContext.getPVTakeTaskIntervalTime(), null, null);
	}

	/**
	 * 启动执行任务的命令
	 * 
	 * @throws SchedulerException
	 */
	public void startExecCommand() throws SchedulerException {
		if (isStartExecCommanding) {
			return;
		}
		isStartExecCommanding = true;
		logger.debug("开启刷流量功能，每{}秒获取一次............ ", String.valueOf(dataContext.getPVQuestTakeTaskIntervalTime()));

		// 每5秒就校验一个商品,最大查找页数为5页
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000)); // 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				PVQuest pollPVQuest = dataContext.pollPVQuest();
				if (pollPVQuest != null) {
					logger.info("questid : {}  开始搜索关键字为[{}]，售价区间为[{},{}]，预置所在地为[{}]和店主为[{}]的淘宝宝贝", pollPVQuest.getQuestid(), pollPVQuest.getSearchKey(), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax(), pollPVQuest.getLocation(), pollPVQuest.getShopKeeper());
					int page = 1;
					// 确定查询条件中的"所在地"
					// 利用"所在地"最后两个字开始,递增查询淘宝,如果出宝贝信息,则说明此所在地存在,则使用,直到所在地全部查询完成
					urlManger.searchLoc(pollPVQuest);
					while (page <= dataContext.getExecSearchTaobaoPageNumberCommand()) {
						String baobeipage = urlManger.search(pollPVQuest, page);
						List<String> itemids = resolve.resolveBaoBeiUrl(pollPVQuest, baobeipage);
						for (String itemid : itemids) {
							if (urlManger.checkTaskUrl(pollPVQuest, itemid)) {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,并且验证通过,恭喜您增加了  [发布点]", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
								return;
							} else {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,但验证失败", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMax());
							}
						}
						logger.info("questid : {}  在第{}页没有搜索到关键字为[{}]，售价区间为[{},{}]的宝贝.", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
						page++;
					}
					urlManger.cancelTask(pollPVQuest);
					dataContext.addPVFailTaskId(pollPVQuest.getId());
				}
			}
		}, -1, dataContext.getPVQuestTakeTaskIntervalTime(), null, null);
	}
}
