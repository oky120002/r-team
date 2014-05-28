package com.r.app.taobaoshua.yuuboo;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.helpers.MessageFormatter;

import com.r.app.taobaoshua.yuuboo.model.PV;
import com.r.app.taobaoshua.yuuboo.model.PVQuest;
import com.r.core.exceptions.LogginErrorException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;

public class YuuBooAction {
	private static final Logger logger = LoggerFactory.getLogger(YuuBooAction.class); // 日志
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final YuuBoo yuuBoo = YuuBoo.getInstance();
	private boolean isPVListRefreshExecuting = false; // pvlist刷新是否正在执行中
	private boolean isPVQuestListRefreshExecuting = false; // pv任务list刷新是否正在执行中
	private boolean isStartExecCommanding = false; // 是否正在执行流量任务
	private boolean isAutoTakeTaskCommanding = false; // 是否自动接手任务
	private boolean isAutoSaveDatas = false; // 是否自动保存数据

	public YuuBooAction() {
		super();
		logger.info("Init Action ..........");
	}

	/**
	 * 获取登陆友保的验证码图片
	 * 
	 * @throws NetworkIOReadErrorException
	 *             网络IO读取错误
	 * @throws IOException
	 *             图片流IO读取错误
	 */
	public Image getLoginYuuBooCaptchaImage() {
		return yuuBoo.getYuuBooManger().getLoginCaptchaImage();
	}

	/**
	 * 登陆友保
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
	public void loginYuuBoo(String account, String accountPassword, String captcha, String question, String answer) throws LogginErrorException {
		String login = yuuBoo.getYuuBooManger().login(account, accountPassword, captcha, question, answer);
		logger.debug("登陆成功后的body文本 : {}", login);
		if (0 <= login.indexOf("验证码不正确")) {
			throw new LogginErrorException("验证码错误，请重新登陆！", 1);
		}
		if (0 <= login.indexOf("为确认你的身份")) {
			throw new LogginErrorException("为确认你的身份，请登陆网站进行身份认证！", 2);
		}
		if (0 <= login.indexOf("安全问题回答错误")) {
			throw new LogginErrorException("安全问题回答错误，请重新认证！", 3);
		}
		if (0 <= login.indexOf("密码不正确")) {
			throw new LogginErrorException("密码不正确，请重新登陆！", 4);
		}
		if (0 <= login.indexOf("你已经连续5次登录失败")) {
			throw new LogginErrorException("连续5次登录失败，请1小时后再登录！", 5);
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
		logger.debug("开启PV自动获取列表功能，每{}秒获取一次............ ", String.valueOf(yuuBoo.getYuuBooDataContext().getPVListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 1;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000));// 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				logger.debug("获取PV列表.............");
				List<PV> pvList = yuuBoo.getYuuBooResolve().resolvePV(yuuBoo.getYuuBooManger().getPVList(curPage++));
				if (CollectionUtils.isEmpty(pvList)) {
					curPage = 1; // 重置页数到第一页
					pvList = yuuBoo.getYuuBooResolve().resolvePV(yuuBoo.getYuuBooManger().getPVList(curPage++));
				}
				if (CollectionUtils.isNotEmpty(pvList)) {
					yuuBoo.getYuuBooDataContext().addPVs(pvList);
				}
			}
		}, -1, yuuBoo.getYuuBooDataContext().getPVListRefreshInterval(), null, null);
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
		logger.debug("开启PV任务自动获取列表功能，每{}秒获取一次............ ", String.valueOf(yuuBoo.getYuuBooDataContext().getPVQuestListRefreshInterval()));

		TaskUtil.executeScheduleTask(new Runnable() {
			private int curPage = 1;

			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000)); // 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				logger.debug("获取PVQuest列表.............");
				List<PVQuest> pvQuestList = yuuBoo.getYuuBooResolve().resolvePVQuest(yuuBoo.getYuuBooManger().getPVQuestList(curPage++));
				if (CollectionUtils.isEmpty(pvQuestList)) {
					curPage = 1; // 重置页数到第一页
					pvQuestList = yuuBoo.getYuuBooResolve().resolvePVQuest(yuuBoo.getYuuBooManger().getPVQuestList(curPage++));
				}
				if (CollectionUtils.isNotEmpty(pvQuestList)) {
					for (PVQuest pvQuest : pvQuestList) {
						if (!yuuBoo.getYuuBooDataContext().containsPVQuest(pvQuest)) {
							yuuBoo.getYuuBooResolve().resolvePVQuestDetaileInfo(pvQuest, yuuBoo.getYuuBooManger().showQuest(pvQuest));
							yuuBoo.getYuuBooDataContext().addPVQuests(pvQuest);
						}
					}
				}
			}
		}, -1, yuuBoo.getYuuBooDataContext().getPVQuestListRefreshInterval(), null, null);
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
		logger.debug("开启自动接手任务功能，每{}秒获取一次............ ", String.valueOf(yuuBoo.getYuuBooDataContext().getPVTakeTaskIntervalTime()));

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(10_000)); // 随机延迟0到10_000毫秒,主要规避友保的防刷任务机制
				logger.debug("自动接手PV任务.............");
				while (!takeTask())
					;
			}

			private boolean takeTask() {
				PV pollPV = yuuBoo.getYuuBooDataContext().pollPV();
				if (pollPV != null) {
					if (yuuBoo.getYuuBooManger().takeTask(pollPV)) {
						return true;
					}
					yuuBoo.getYuuBooDataContext().addPVFailTaskId(pollPV.getId());
					TaskUtil.sleep(500);
					return false;
				}
				return true;
			}
		}, -1, yuuBoo.getYuuBooDataContext().getPVTakeTaskIntervalTime(), null, null);
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
		final int execSearchTaobaoPageNumberCommand = yuuBoo.getYuuBooDataContext().getExecSearchTaobaoPageNumberCommand();
		logger.debug("开启刷流量功能，每{}秒获取一次..在淘宝搜索匹配宝贝时,仅仅匹配前{}页中的宝贝,在前{}页中没有找到.则自动停止搜索匹配,撤销接手任务.......... ", yuuBoo.getYuuBooDataContext().getPVQuestTakeTaskIntervalTime(), execSearchTaobaoPageNumberCommand, execSearchTaobaoPageNumberCommand);

		// 每5秒就校验一个商品,最大查找页数为5页
		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				TaskUtil.sleep(RandomUtil.randomInteger(5_000)); // 随机延迟0到5_000毫秒,主要规避友保的防刷任务机制
				logger.debug("执行PV的淘宝搜索任务.............");
				PVQuest pollPVQuest = yuuBoo.getYuuBooDataContext().pollPVQuest();
				if (pollPVQuest != null) {
					yuuBoo.getYuuBooDataContext().addPVFailTaskId(pollPVQuest.getId());
					logger.info("questid : {}  开始搜索关键字为[{}]，售价区间为[{},{}]，预置所在地为[{}]和店主为[{}]的淘宝宝贝", pollPVQuest.getQuestid(), pollPVQuest.getSearchKey(), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax(), pollPVQuest.getLocation(), pollPVQuest.getShopKeeper());
					int page = 1;
					// 确定查询条件中的"所在地"
					// 利用"所在地"最后两个字开始,递增查询淘宝,如果出宝贝信息,则说明此所在地存在,则使用,直到所在地全部查询完成
					yuuBoo.getYuuBooManger().searchLoc(pollPVQuest);
					while (page <= execSearchTaobaoPageNumberCommand) {
						String baobeipage = yuuBoo.getYuuBooManger().search(pollPVQuest, page);
						if (baobeipage.contains("点击返回上一步")) {
							logger.info("questid : {}  在第{}页已经没有任何宝贝可以搜索匹配!  搜索结束!", pollPVQuest.getQuestid(), page);
							break;
						}
						List<String> itemids = yuuBoo.getYuuBooResolve().resolveBaoBeiUrl(pollPVQuest, baobeipage);
						for (String itemid : itemids) {
							if (yuuBoo.getYuuBooManger().checkTaskUrl(pollPVQuest, itemid)) {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,并且验证通过,恭喜您增加了  [发布点]", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
								return;
							} else {
								logger.info("questid : {}  在第{}页成功搜索到关键字为[{}]，售价区间为[{},{}]的宝贝,但验证失败", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
							}
						}
						logger.info("questid : {}  在第{}页没有搜索到关键字为[{}]，售价区间为[{},{}]的宝贝.", pollPVQuest.getQuestid(), page, pollPVQuest.getSearchKeyCut(15), pollPVQuest.getPriceMin(), pollPVQuest.getPriceMax());
						page++;
					}
					yuuBoo.getYuuBooManger().cancelTask(pollPVQuest);
				}
			}
		}, -1, yuuBoo.getYuuBooDataContext().getPVQuestTakeTaskIntervalTime(), null, null);
	}

	/** 启动自动保存数据功能 */
	public void startAutoSaveDatas() throws SchedulerException {
		if (isAutoSaveDatas) {
			return;
		}
		isAutoSaveDatas = true;
		int time = 60;
		String filePath = MessageFormatter.format("./datas/pvfails.{}.{}.dat", yuuBoo.getYuuBooDataContext().getAccount(), sdf.format(new Date())).getMessage();
		final File pvFailsFile = new File(filePath);
		logger.debug("开启自动保存数据功能，每{}秒保存一次............ ", time);

		if (pvFailsFile.exists()) {
			try {
				List<String> readLines = FileUtils.readLines(pvFailsFile);
				if (CollectionUtils.isNotEmpty(readLines)) {
					yuuBoo.getYuuBooDataContext().addPVFailTaskIds(readLines);
				}
			} catch (IOException e) {
				logger.error("读取PVFail文件失败!", e);
			}
		}

		TaskUtil.executeScheduleTask(new Runnable() {
			@Override
			public void run() {
				logger.debug("保存PVQuestid一次.............");
				Collection<String> pvFailTaskIds = yuuBoo.getYuuBooDataContext().getPvFailTaskIds();
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

	/** 校验能否通过代理进入友保 */
	public boolean checkYuuBoo(Proxy proxy) {
		String yuuBooLoginHtml = null;
		try {
			yuuBooLoginHtml = yuuBoo.getYuuBooManger().checkYuuBoo(proxy);
		} catch (Exception e) {
			yuuBooLoginHtml = null;
		}
		if (StringUtils.isBlank(yuuBooLoginHtml)) {
			return false;
		}
		return yuuBooLoginHtml.contains("我的优保");
	}
}
