package com.r.app.taobaoshua.action;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.quartz.SchedulerException;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.data.DataContext;
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
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final DataContext dataContext = TaobaoShuaApp.getInstance().getDataContext();
	private UrlManger urlManger = new UrlManger(); // Url管理器
	private UrlResolve resolve = new UrlResolve(); // Url解析器
	private boolean isPVListRefreshExecuting = false; // pvlist刷新是否正在执行中
	private boolean isPVQuestListRefreshExecuting = false; // pv任务list刷新是否正在执行中
	private boolean isStartExecCommanding = false; // 是否正在执行流量任务
	private boolean isAutoTakeTaskCommanding = false; // 是否自动接手任务
	private boolean isAutoSaveDatas = false; // 是否自动保存数据

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
			private int curPage = 1;

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
					if (urlManger.takeTask(pollPV)) {
						return true;
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
		final int execSearchTaobaoPageNumberCommand = dataContext.getExecSearchTaobaoPageNumberCommand();
		logger.debug("开启刷流量功能，每{}秒获取一次..在淘宝搜索匹配宝贝时,仅仅匹配前{}页中的宝贝,在前{}页中没有找到.则自动停止搜索匹配,撤销接手任务.......... ", dataContext.getPVQuestTakeTaskIntervalTime(), execSearchTaobaoPageNumberCommand, execSearchTaobaoPageNumberCommand);

		// 每5秒就校验一个商品,最大查找页数为5页
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000)); // 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				PVQuest pollPVQuest = dataContext.pollPVQuest();
				if (pollPVQuest != null) {
					dataContext.addPVFailTaskId(pollPVQuest.getId());
					logger.info("questid : {}  开始搜索关键字为[{}]，售价区间为[{},{}]，预置所在地为[{}]和店主为[{}]的淘宝宝贝", pollPVQuest.getQuestid(), pollPVQuest.getSearchKey(), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax(), pollPVQuest.getLocation(), pollPVQuest.getShopKeeper());
					int page = 1;
					// 确定查询条件中的"所在地"
					// 利用"所在地"最后两个字开始,递增查询淘宝,如果出宝贝信息,则说明此所在地存在,则使用,直到所在地全部查询完成
					urlManger.searchLoc(pollPVQuest);
					while (page <= execSearchTaobaoPageNumberCommand) {
						String baobeipage = urlManger.search(pollPVQuest, page);
						if (baobeipage.contains("点击返回上一步")) {
							logger.info("questid : {}  在第{}页已经没有任何宝贝可以搜索匹配!  搜索结束!", pollPVQuest.getQuestid(), page);
							break;
						}
						List<String> itemids = resolve.resolveBaoBeiUrl(pollPVQuest, baobeipage);
						for (String itemid : itemids) {
							if (urlManger.checkTaskUrl(pollPVQuest, itemid)) {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,并且验证通过,恭喜您增加了  [发布点]", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
								return;
							} else {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,但验证失败", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
							}
						}
						logger.info("questid : {}  在第{}页没有搜索到关键字为[{}]，售价区间为[{},{}]的宝贝.", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
						page++;
					}
					urlManger.cancelTask(pollPVQuest);
				}
			}
		}, -1, dataContext.getPVQuestTakeTaskIntervalTime(), null, null);
	}

	/** 启动自动保存数据功能 */
	public void startAutoSaveDatas() throws SchedulerException {
		if (isAutoSaveDatas) {
			return;
		}
		isAutoSaveDatas = true;
		int time = 60;
		final File pvFailsFile = new File("./datas/pvfails." + sdf.format(new Date()) + ".dat");
		logger.debug("开启自动保存数据功能，每{}秒保存一次............ ", time);

		if (pvFailsFile.exists()) {
			try {
				List<String> readLines = FileUtils.readLines(pvFailsFile);
				if (CollectionUtils.isNotEmpty(readLines)) {
					dataContext.addPVFailTaskIds(readLines);
				}
			} catch (IOException e) {
				logger.error("读取PVFail文件失败!", e);
			}
		}

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				Collection<String> pvFailTaskIds = dataContext.getPvFailTaskIds();

				if (CollectionUtils.isNotEmpty(pvFailTaskIds)) {
					try {
						FileUtils.writeLines(pvFailsFile, pvFailTaskIds);
					} catch (IOException e) {
						logger.error("保存PVFail文件失败!", e);
					}
				}
			}
		}, -1, time, null, null);
	}
}
