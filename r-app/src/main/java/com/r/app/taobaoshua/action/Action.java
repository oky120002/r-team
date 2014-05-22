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
import com.r.core.exceptions.CaptchaErrorException;
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
	 * @throws CaptchaErrorException
	 *             验证码错误
	 */
	public void login(String account, String accountPassword, String captcha) throws IOException, CaptchaErrorException {
		String login = urlManger.login(account, accountPassword, captcha);
		if (0 <= login.indexOf("验证码不正确")) {
			throw new CaptchaErrorException("验证码错误,请重新登陆");
		}
		if (0 <= login.indexOf("为确认你的身份")) {
			throw new CaptchaErrorException("为确认你的身份，登陆时需要进行安全验证.请到网站进行安全认证后,再操作.谢谢!");
		}
		logger.debug("登陆成功后的body文本 : {}", login);
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
		logger.debug("开启PV自动获取列表功能,每{}秒获取一次............ ", String.valueOf(dataContext.getPVListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 1;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5000)); // 休眠[0,2}秒
				List<PV> pvList = null;
				try {
					pvList = resolve.resolvePV(urlManger.getPVList(curPage++));
					if (CollectionUtils.isEmpty(pvList)) {
						curPage = 1; // 重置页数到第一页
						pvList = resolve.resolvePV(urlManger.getPVList(curPage++));
					}
					if (CollectionUtils.isNotEmpty(pvList)) {
						dataContext.addPVs(pvList);
					}
				} catch (IOException e) {

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
		logger.debug("开启PV任务自动获取列表功能,每{}秒获取一次............ ", String.valueOf(dataContext.getPVListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 1;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5000)); // 休眠[0,2}秒
				List<PVQuest> pvQuestList = null;
				try {
					pvQuestList = resolve.resolvePVQuest(urlManger.getPVQuestList(curPage++));
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

				} catch (IOException e) {

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
		logger.debug("开启自动接手任务功能............ ", String.valueOf(dataContext.getPVListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				while (!takeTask())
					;
			}

			private boolean takeTask() {
				PV pollPV = dataContext.pollPV();
				if (pollPV != null) {
					try {
						if (urlManger.takeTask(dataContext.pollPV())) {
							return true;
						}
					} catch (YouBaoException e) {
						logger.debug(e.getMessage(), e);
					} catch (IOException e) {
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
		logger.debug("开启刷流量功能............ ", String.valueOf(dataContext.getPVListRefreshInterval()));

		// 每5秒就校验一个商品,最大查找页数为5页
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				PVQuest pollPVQuest = dataContext.pollPVQuest();
				try {
					int page = 1;
					if (pollPVQuest != null) {
						while (page <= 5) {
							String baobeipage = urlManger.search(pollPVQuest, page++);
							List<String> itemids = resolve.resolveBaoBeiUrl(pollPVQuest, baobeipage);
							for (String itemid : itemids) {
								if (urlManger.checkTaskUrl(pollPVQuest, itemid)) {
									return;
								}
							}
						}
						urlManger.cancelTask(pollPVQuest);
						dataContext.addPVFailTaskId(pollPVQuest.getId());
					}
				} catch (IOException e) {
				} catch (YouBaoException ybe) {
					logger.error("撤销任务失败!", ybe);
					dataContext.addPVFailTaskId(pollPVQuest.getId());
				}
			}
		}, -1, dataContext.getPVQuestTakeTaskIntervalTime(), null, null);
	}
}
