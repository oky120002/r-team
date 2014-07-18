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
import javax.swing.Icon;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.enumtask.PaymentType;
import com.r.app.taobaoshua.bluesky.model.enumtask.ShopScore;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskAddr;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskTimeLimit;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskType;

@Entity
@Table(name = "task")
public class Task {
    
    private static final BlueSky blueSky = BlueSky.getInstance();
    
    @Id
    @Column
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
    
    @Column(length = 2048)
    private String itemAddr; // 商品地址
    
    @Column
    private Boolean isUseSearchLoc; // 是否使用搜索来路
    
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType; // 支付方式
    
    @Column
    private Double securityPrice; // 任务担保金额,这里金额不大所以使用Double.如果发现有大金额可以改为BigDecimal
    
    @Column
    private Double publishingPoint; // 发布点
    
    @Column
    @Enumerated(EnumType.STRING)
    private TaskType taskType; // 任务类型
    
    @Column
    @Enumerated(EnumType.STRING)
    private TaskTimeLimit timeLimit; // 任务时限(分钟)
    
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
    private Boolean isUseQQ; // 是否使用QQ
    
    @Column
    private Boolean isUpdatePrice; // 是否改价
    
    @Column
    private Boolean isUpdateAddr; // 是否改地址
    
    @Column
    private Boolean isBaoGuo; // 是否真是地址
    
    @Column
    private String auxiliaryCondition; // 附加条件
    
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
    
    @Column
    private Boolean isUpdateTaskDetail; // 是否已经更新过详细信息
    
    @Column
    private Double publishingPointOneDay; // 一天可以赚取的发布点数
    
    @Column
    private Double securityPriceOneDayOnePPoint; // 一天一发布点需要的担保金投入
    
    public void calStatistics() {
        publishingPointOneDay = null;
        securityPriceOneDayOnePPoint = null;
        
        // 计算一天可以赚取的发布点数
        if (publishingPoint != null && timeLimit != null) {
            double p = publishingPoint.doubleValue(); // 默认赚取全部的发布点,立即完成
            int day = (int) (timeLimit.getTimeLimit() / 60 / 24);
            publishingPointOneDay = Double.valueOf(p / (day < 1 ? 1 : day));
        }
        
        // 计算一天一发布点需要的担保金投入
        if (securityPrice != null && publishingPointOneDay != null) {
            securityPriceOneDayOnePPoint = Double.valueOf(securityPrice.doubleValue()
                    / publishingPointOneDay.doubleValue());
        }
    }
    
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
     * @return the isAccountOnLine
     */
    public boolean isAccountVip() {
        return isAccountVip == null ? false : isAccountVip.booleanValue();
    }
    
    public Icon getIsAccountVipIcon() {
        if (isAccountVip()) {
            return blueSky.getIcon("vip.gif");
        } else {
            return null;
        }
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
     * @return the accountLevel
     */
    public Icon getAccountLevelIcon() {
        String accountLevel = getAccountLevel();
        if (StringUtils.isNotBlank(accountLevel)) {
            return blueSky.getIcon(accountLevel);
        } else if (getIsNew()) {
            return blueSky.getIcon("new.gif");
        } else {
            return null;
        }
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
     * @return the isAccountOnLine
     */
    public boolean isAccountOnLine() {
        return isAccountOnLine == null ? false : isAccountOnLine.booleanValue();
    }
    
    public Icon getIsAccountOnLineIcon() {
        if (isAccountOnLine()) {
            return blueSky.getIcon("online.gif");
        } else {
            return blueSky.getIcon("notonline.gif");
        }
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
     * @return the itemAddr
     */
    public String getItemAddr() {
        return itemAddr;
    }
    
    /**
     * @param itemAddr
     *            the itemAddr to set
     */
    public void setItemAddr(String itemAddr) {
        this.itemAddr = itemAddr;
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
    
    public String getPaymentTypeStr() {
        return paymentType == null ? "" : paymentType.name();
    }
    
    /**
     * @param paymentType
     *            the paymentType to set
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    
    public Double getSecurityPrice() {
        return securityPrice;
    }
    
    public void setSecurityPrice(Double securityPrice) {
        this.securityPrice = securityPrice;
    }
    
    public Double getPublishingPoint() {
        return publishingPoint;
    }
    
    public void setPublishingPoint(Double publishingPoint) {
        this.publishingPoint = publishingPoint;
    }
    
    /**
     * @return the isUpdatePrice
     */
    public Boolean getIsUpdatePrice() {
        return isUpdatePrice;
    }
    
    public Icon getIsUpdatePriceIcon() {
        return isUpdatePrice() ? blueSky.getIcon("gai.gif") : null;
    }
    
    public boolean isUpdatePrice() {
        return isUpdatePrice == null ? false : isUpdatePrice.booleanValue();
    }
    
    /**
     * @param isUpdatePrice
     *            the isUpdatePrice to set
     */
    public void setIsUpdatePrice(Boolean isUpdatePrice) {
        this.isUpdatePrice = isUpdatePrice;
    }
    
    /**
     * @return the isUpdateAddr
     */
    public Boolean getIsUpdateAddr() {
        return isUpdateAddr;
    }
    
    public Icon getIsUpdateAddrIcon() {
        return isUpdateAddr() ? blueSky.getIcon("DiZhi.gif") : null;
    }
    
    public boolean isUpdateAddr() {
        return isUpdateAddr == null ? false : isUpdateAddr.booleanValue();
    }
    
    /**
     * @param isUpdateAddr
     *            the isUpdateAddr to set
     */
    public void setIsUpdateAddr(Boolean isUpdateAddr) {
        this.isUpdateAddr = isUpdateAddr;
    }
    
    /**
     * @return the isBaoGuo
     */
    public Boolean getIsBaoGuo() {
        return isBaoGuo;
    }
    
    public Icon getIsBaoGuoIcon() {
        return isBaoGuo() ? blueSky.getIcon("BaoGuo.gif") : null;
    }
    
    public boolean isBaoGuo() {
        return isBaoGuo == null ? false : isBaoGuo.booleanValue();
    }
    
    /**
     * @param isBaoGuo
     *            the isBaoGuo to set
     */
    public void setIsBaoGuo(Boolean isBaoGuo) {
        this.isBaoGuo = isBaoGuo;
    }
    
    /**
     * @return the taskType
     */
    public TaskType getTaskType() {
        return taskType;
    }
    
    /** 获取任务类型图标 */
    public Icon getTaskTypeIcon() {
        Icon icon = null;
        switch (taskType) {
            case 实物:
                icon = blueSky.getIcon("realobject.gif");
                break;
            case 实购:
                icon = blueSky.getIcon("realobject_shop.gif");
                break;
            case 虚拟:
                icon = blueSky.getIcon("virtual.gif");
                break;
            case 虚购:
                icon = blueSky.getIcon("virtual_shop.gif");
                break;
            case 搭配:
                icon = blueSky.getIcon("dapei.gif");
                break;
            case 其它:
            default:
                return null;
        }
        return icon;
    }
    
    /**
     * @param taskType
     *            the taskType to set
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
    public TaskTimeLimit getTimeLimit() {
        return timeLimit;
    }
    
    public void setTimeLimit(TaskTimeLimit timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public String getTimeLimitString() {
        TaskTimeLimit tl = getTimeLimit();
        if (tl == null) {
            return "NAN";
        }
        return tl.getName();
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
        return praise == null ? "" : praise;
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
    
    public boolean isSearch() {
        return isSearch == null ? false : isSearch.booleanValue();
    }
    
    public Icon getIsSearchIcon() {
        return isSearch() ? blueSky.getIcon("SouSuo.gif") : null;
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
    
    public boolean isCollect() {
        return isCollect == null ? false : isCollect.booleanValue();
    }
    
    public Icon getIsCollectIcon() {
        return isCollect() ? blueSky.getIcon("ShouCang.gif") : null;
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
    
    public boolean isIDCard() {
        return isIDCard == null ? false : isIDCard.booleanValue();
    }
    
    public Icon getIsIDCardIcon() {
        return isIDCard() ? blueSky.getIcon("ShiMing.gif") : null;
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
    
    public boolean isSincerity() {
        return isSincerity == null ? false : isSincerity.booleanValue();
    }
    
    public Icon getIsSincerityIcon() {
        return isSincerity() ? blueSky.getIcon("ShangBao.gif") : null;
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
    
    public Icon getIsWangWangIcon() {
        return isWangWang() ? blueSky.getIcon("WangLiao.gif") : null;
    }
    
    public boolean isWangWang() {
        return isWangWang == null ? false : isWangWang.booleanValue();
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
    
    public Icon getIsReviewIcon() {
        return isReview() ? blueSky.getIcon("ShenHe.gif") : null;
    }
    
    public boolean isReview() {
        return isReview == null ? false : isReview.booleanValue();
    }
    
    /**
     * @param isReview
     *            the isReview to set
     */
    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }
    
    /**
     * @return the isUseQQ
     */
    public Boolean getIsUseQQ() {
        return isUseQQ;
    }
    
    public Icon getIsUseQQIcon() {
        return isUseQQ() ? blueSky.getIcon("qq.gif") : null;
    }
    
    public boolean isUseQQ() {
        return isUseQQ == null ? false : isUseQQ.booleanValue();
    }
    
    /**
     * @param isUseQQ
     *            the isUseQQ to set
     */
    public void setIsUseQQ(Boolean isUseQQ) {
        this.isUseQQ = isUseQQ;
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
    public void setTaskPublishingEvaluationTime(
            Date taskPublishingEvaluationTime) {
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
    
    /**
     * @return the isUpdateTaskDetail
     */
    public Boolean getIsUpdateTaskDetail() {
        return isUpdateTaskDetail;
    }
    
    public boolean isUpdateTaskDetail() {
        return isUpdateTaskDetail == null ? false
                : isUpdateTaskDetail.booleanValue();
    }
    
    /**
     * @param isUpdateTaskDetail
     *            the isUpdateTaskDetail to set
     */
    public void setIsUpdateTaskDetail(Boolean isUpdateTaskDetail) {
        this.isUpdateTaskDetail = isUpdateTaskDetail;
    }
    
    public Double getPublishingPointOneDay() {
        return publishingPointOneDay;
    }
    
    public void setPublishingPointOneDay(Double publishingPointOneDay) {
        this.publishingPointOneDay = publishingPointOneDay;
    }
    
    public Double getSecurityPriceOneDayOnePPoint() {
        return securityPriceOneDayOnePPoint;
    }
    
    public void setSecurityPriceOneDayOnePPoint(
            Double securityPriceOneDayOnePPoint) {
        this.securityPriceOneDayOnePPoint = securityPriceOneDayOnePPoint;
    }
    
    /*--------------非ben方法---------------*/
    /**
     * 判断此任务在被接手的情况下,是否快过期(期限为5内都算快过期)
     * 
     * @return true:快过期|false:没有快过期,或者就不是接手状态
     */
    public boolean isTaskerTimeout() {
        if (TaskStatus.等待绑定买号.equals(this.status)) {
            long tasktime = taskedTime.getTime();
            long newtime = new Date().getTime();
            long time = newtime - tasktime;
            if (time < 300_000) {
                return true;
            }
        }
        return false;
    }
}
