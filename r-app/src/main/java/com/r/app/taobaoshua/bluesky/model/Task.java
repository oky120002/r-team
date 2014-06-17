package com.r.app.taobaoshua.bluesky.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.r.app.taobaoshua.bluesky.model.enums.PaymentType;
import com.r.app.taobaoshua.bluesky.model.enums.ShopScore;
import com.r.app.taobaoshua.bluesky.model.enums.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enums.TaskStatus;
import com.r.app.taobaoshua.bluesky.model.enums.TaskType;

@Entity
@Table(name = "task")
public class Task {

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	private String id;
	@Column
	private String number; // 任务编号
	@Column
	private String account; // 发布任务者账号
	@Column
	private Boolean isAccountVip; // 发布任务者是否VIP
	@Column
	private Integer accountExe; // 发布任务者账号经验值
	@Column
	private String accountLevel; // 发布者等级(等级图标名)
	@Column
	private Boolean isAccountOnLine; // 发布任务者是否在线
	@Column
	private Boolean isNew; // 发布者是否是新手
	@Column
	private String shopkeeper; // 店掌柜
	@Column
	private String taskId; // 接手任务的id
	@Column
	@Enumerated(EnumType.STRING)
	private TaskAddr type; // 任务区
	@Column
	private String itemId; // 商品id
	@Column
	private String itemTitle; // 商品标题
	@Column
	private Boolean isUseSearchLoc; // 是否使用搜索来路
	@Column
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType; // 支付方式
	@Column
	private Number securityPrice; // 任务担保金额,这里金额不大所以使用Double.如果发现有大金额可以改为BigDecimal
	@Column
	private Number publishingPoint; // 发布点
	@Column
	private Boolean isUpdatePrice; // 是否改了价
	@Column
	@Enumerated(EnumType.STRING)
	private TaskType taskType; // 任务类型
	@Column
	private Number timeLimit; // 任务时限(分钟)
	@Column
	@Enumerated(EnumType.STRING)
	private ShopScore shopScore; // 商铺动态平分
	@Column
	private String praise; // 好评内容
	@Column
	private String qq; // 发布方QQ号码

	@Column
	private Boolean isSearch; // 是否需要搜索
	@Column
	private Boolean isCollect; // 是否收藏
	@Column
	private Boolean isIDCard;// 是否实名认证
	@Column
	private Boolean isSincerity; // 诚信商家
	@Column
	private Boolean isWangWang; // 是否旺聊
	@Column
	private Boolean isReview; // 是否需要审核
	@Column
	private String auxiliaryCondition; // 附加条件

	@Column
	private String tasker; // 任务接手人
	@Column
	private String addr; // 收货地址
	@Column
	private String loc; // 指定地区
	@Column
	private String taskerAccount; // 接手人账号
	@Column
	private String taskerBuyAccount; // 接手人买号
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskPublishingTime;// 任务发布时间： 2014-6-5 21:45:59
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskedTime;// 任务接手时间： 2014-6-5 23:23:30
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskPaymentTime;// 任务支付时间： 2014-6-5 23:27:48
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskShippingTime;// 任务发货时间： 2014-6-5 23:49:54
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskReceiptTime;// 任务收货好评时间： 2014-6-6 23:54:08
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskConfirmedReviewTime;// 任务确认审核时间： 2014-6-7 22:31:38
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskPublishingEvaluationTime;// 发布方平台评价时间： 2014-6-7 22:31:50
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date taskerEvaluationTime;// 接手方平台评价时间： 2014-6-8 12:34:18

	@Column
	@Enumerated(EnumType.STRING)
	private TaskStatus status; // 当前任务状态

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the isAccountVip
	 */
	public Boolean getIsAccountVip() {
		return isAccountVip;
	}

	/**
	 * @param isAccountVip
	 *            the isAccountVip to set
	 */
	public void setIsAccountVip(Boolean isAccountVip) {
		this.isAccountVip = isAccountVip;
	}

	/**
	 * @return the accountExe
	 */
	public Integer getAccountExe() {
		return accountExe;
	}

	/**
	 * @param accountExe
	 *            the accountExe to set
	 */
	public void setAccountExe(Integer accountExe) {
		this.accountExe = accountExe;
	}

	/**
	 * @return the accountLevel
	 */
	public String getAccountLevel() {
		return accountLevel;
	}

	/**
	 * @param accountLevel
	 *            the accountLevel to set
	 */
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	/**
	 * @return the isAccountOnLine
	 */
	public Boolean getIsAccountOnLine() {
		return isAccountOnLine;
	}

	/**
	 * @param isAccountOnLine
	 *            the isAccountOnLine to set
	 */
	public void setIsAccountOnLine(Boolean isAccountOnLine) {
		this.isAccountOnLine = isAccountOnLine;
	}

	/**
	 * @return the isNew
	 */
	public Boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the shopkeeper
	 */
	public String getShopkeeper() {
		return shopkeeper;
	}

	/**
	 * @param shopkeeper
	 *            the shopkeeper to set
	 */
	public void setShopkeeper(String shopkeeper) {
		this.shopkeeper = shopkeeper;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the type
	 */
	public TaskAddr getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TaskAddr type) {
		this.type = type;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemTitle
	 */
	public String getItemTitle() {
		return itemTitle;
	}

	/**
	 * @param itemTitle
	 *            the itemTitle to set
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	/**
	 * @return the isUseSearchLoc
	 */
	public Boolean getIsUseSearchLoc() {
		return isUseSearchLoc;
	}

	/**
	 * @param isUseSearchLoc
	 *            the isUseSearchLoc to set
	 */
	public void setIsUseSearchLoc(Boolean isUseSearchLoc) {
		this.isUseSearchLoc = isUseSearchLoc;
	}

	/**
	 * @return the paymentType
	 */
	public PaymentType getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the securityPrice
	 */
	public Number getSecurityPrice() {
		return securityPrice;
	}

	/**
	 * @param securityPrice
	 *            the securityPrice to set
	 */
	public void setSecurityPrice(Number securityPrice) {
		this.securityPrice = securityPrice;
	}

	/**
	 * @return the publishingPoint
	 */
	public Number getPublishingPoint() {
		return publishingPoint;
	}

	/**
	 * @param publishingPoint
	 *            the publishingPoint to set
	 */
	public void setPublishingPoint(Number publishingPoint) {
		this.publishingPoint = publishingPoint;
	}

	/**
	 * @return the isUpdatePrice
	 */
	public Boolean getIsUpdatePrice() {
		return isUpdatePrice;
	}

	/**
	 * @param isUpdatePrice
	 *            the isUpdatePrice to set
	 */
	public void setIsUpdatePrice(Boolean isUpdatePrice) {
		this.isUpdatePrice = isUpdatePrice;
	}

	/**
	 * @return the taskType
	 */
	public TaskType getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            the taskType to set
	 */
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the timeLimit
	 */
	public Number getTimeLimit() {
		return timeLimit;
	}

	/**
	 * @param timeLimit
	 *            the timeLimit to set
	 */
	public void setTimeLimit(Number timeLimit) {
		this.timeLimit = timeLimit;
	}

	/**
	 * @return the shopScore
	 */
	public ShopScore getShopScore() {
		return shopScore;
	}

	/**
	 * @param shopScore
	 *            the shopScore to set
	 */
	public void setShopScore(ShopScore shopScore) {
		this.shopScore = shopScore;
	}

	/**
	 * @return the praise
	 */
	public String getPraise() {
		return praise;
	}

	/**
	 * @param praise
	 *            the praise to set
	 */
	public void setPraise(String praise) {
		this.praise = praise;
	}

	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq
	 *            the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return the isSearch
	 */
	public Boolean getIsSearch() {
		return isSearch;
	}

	/**
	 * @param isSearch
	 *            the isSearch to set
	 */
	public void setIsSearch(Boolean isSearch) {
		this.isSearch = isSearch;
	}

	/**
	 * @return the isCollect
	 */
	public Boolean getIsCollect() {
		return isCollect;
	}

	/**
	 * @param isCollect
	 *            the isCollect to set
	 */
	public void setIsCollect(Boolean isCollect) {
		this.isCollect = isCollect;
	}

	/**
	 * @return the isIDCard
	 */
	public Boolean getIsIDCard() {
		return isIDCard;
	}

	/**
	 * @param isIDCard
	 *            the isIDCard to set
	 */
	public void setIsIDCard(Boolean isIDCard) {
		this.isIDCard = isIDCard;
	}

	/**
	 * @return the isSincerity
	 */
	public Boolean getIsSincerity() {
		return isSincerity;
	}

	/**
	 * @param isSincerity
	 *            the isSincerity to set
	 */
	public void setIsSincerity(Boolean isSincerity) {
		this.isSincerity = isSincerity;
	}

	/**
	 * @return the isWangWang
	 */
	public Boolean getIsWangWang() {
		return isWangWang;
	}

	/**
	 * @param isWangWang
	 *            the isWangWang to set
	 */
	public void setIsWangWang(Boolean isWangWang) {
		this.isWangWang = isWangWang;
	}

	/**
	 * @return the isReview
	 */
	public Boolean getIsReview() {
		return isReview;
	}

	/**
	 * @param isReview
	 *            the isReview to set
	 */
	public void setIsReview(Boolean isReview) {
		this.isReview = isReview;
	}

	/**
	 * @return the auxiliaryCondition
	 */
	public String getAuxiliaryCondition() {
		return auxiliaryCondition;
	}

	/**
	 * @param auxiliaryCondition
	 *            the auxiliaryCondition to set
	 */
	public void setAuxiliaryCondition(String auxiliaryCondition) {
		this.auxiliaryCondition = auxiliaryCondition;
	}

	/**
	 * @return the tasker
	 */
	public String getTasker() {
		return tasker;
	}

	/**
	 * @param tasker
	 *            the tasker to set
	 */
	public void setTasker(String tasker) {
		this.tasker = tasker;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the loc
	 */
	public String getLoc() {
		return loc;
	}

	/**
	 * @param loc
	 *            the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}

	/**
	 * @return the taskerAccount
	 */
	public String getTaskerAccount() {
		return taskerAccount;
	}

	/**
	 * @param taskerAccount
	 *            the taskerAccount to set
	 */
	public void setTaskerAccount(String taskerAccount) {
		this.taskerAccount = taskerAccount;
	}

	/**
	 * @return the taskerBuyAccount
	 */
	public String getTaskerBuyAccount() {
		return taskerBuyAccount;
	}

	/**
	 * @param taskerBuyAccount
	 *            the taskerBuyAccount to set
	 */
	public void setTaskerBuyAccount(String taskerBuyAccount) {
		this.taskerBuyAccount = taskerBuyAccount;
	}

	/**
	 * @return the taskPublishingTime
	 */
	public Date getTaskPublishingTime() {
		return taskPublishingTime;
	}

	/**
	 * @param taskPublishingTime
	 *            the taskPublishingTime to set
	 */
	public void setTaskPublishingTime(Date taskPublishingTime) {
		this.taskPublishingTime = taskPublishingTime;
	}

	/**
	 * @return the taskedTime
	 */
	public Date getTaskedTime() {
		return taskedTime;
	}

	/**
	 * @param taskedTime
	 *            the taskedTime to set
	 */
	public void setTaskedTime(Date taskedTime) {
		this.taskedTime = taskedTime;
	}

	/**
	 * @return the taskPaymentTime
	 */
	public Date getTaskPaymentTime() {
		return taskPaymentTime;
	}

	/**
	 * @param taskPaymentTime
	 *            the taskPaymentTime to set
	 */
	public void setTaskPaymentTime(Date taskPaymentTime) {
		this.taskPaymentTime = taskPaymentTime;
	}

	/**
	 * @return the taskShippingTime
	 */
	public Date getTaskShippingTime() {
		return taskShippingTime;
	}

	/**
	 * @param taskShippingTime
	 *            the taskShippingTime to set
	 */
	public void setTaskShippingTime(Date taskShippingTime) {
		this.taskShippingTime = taskShippingTime;
	}

	/**
	 * @return the taskReceiptTime
	 */
	public Date getTaskReceiptTime() {
		return taskReceiptTime;
	}

	/**
	 * @param taskReceiptTime
	 *            the taskReceiptTime to set
	 */
	public void setTaskReceiptTime(Date taskReceiptTime) {
		this.taskReceiptTime = taskReceiptTime;
	}

	/**
	 * @return the taskConfirmedReviewTime
	 */
	public Date getTaskConfirmedReviewTime() {
		return taskConfirmedReviewTime;
	}

	/**
	 * @param taskConfirmedReviewTime
	 *            the taskConfirmedReviewTime to set
	 */
	public void setTaskConfirmedReviewTime(Date taskConfirmedReviewTime) {
		this.taskConfirmedReviewTime = taskConfirmedReviewTime;
	}

	/**
	 * @return the taskPublishingEvaluationTime
	 */
	public Date getTaskPublishingEvaluationTime() {
		return taskPublishingEvaluationTime;
	}

	/**
	 * @param taskPublishingEvaluationTime
	 *            the taskPublishingEvaluationTime to set
	 */
	public void setTaskPublishingEvaluationTime(Date taskPublishingEvaluationTime) {
		this.taskPublishingEvaluationTime = taskPublishingEvaluationTime;
	}

	/**
	 * @return the taskerEvaluationTime
	 */
	public Date getTaskerEvaluationTime() {
		return taskerEvaluationTime;
	}

	/**
	 * @param taskerEvaluationTime
	 *            the taskerEvaluationTime to set
	 */
	public void setTaskerEvaluationTime(Date taskerEvaluationTime) {
		this.taskerEvaluationTime = taskerEvaluationTime;
	}

	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
