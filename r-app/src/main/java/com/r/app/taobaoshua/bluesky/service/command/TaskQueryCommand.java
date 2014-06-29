/**
 * 
 */
package com.r.app.taobaoshua.bluesky.service.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskTimeLimit;
import com.r.core.exceptions.SwitchPathException;

/**
 * @author oky
 * 
 */
public class TaskQueryCommand implements QueryCommand<Task> {
	private TaskStatus[] status = null; // 任务状态
	private TaskListOrder order = null; // 排序
	private int firstResult = -1; // 查询起始条数
	private int maxResults = -1; // 查询最大条数

	private Boolean isSearch = null; // 是否需要搜索
	private Boolean isCollect = null; // 是否收藏
	private Boolean isIDCard = null;// 是否实名认证
	private Boolean isSincerity = null; // 诚信商家
	private Boolean isWangWang = null; // 是否旺聊
	private Boolean isReview = null; // 是否需要审核
	private Boolean isUseQQ = null; // 是否有QQ参数
	private Boolean isUpdatePrice = null; // 是否改价
	private Boolean isUpdateTaskDetail = null; // 是否已经获取过任务详细信息
	private String taskerAccount = null; // 接手人账号
	private Boolean isUpdateAddr = null; // 是否改地址
	private Boolean isBaoGuo = null; // 是否真实发包
	private Set<TaskTimeLimit> taskTimeLimits = new HashSet<TaskTimeLimit>();

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> queryCollection(Session session) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> pars = new HashMap<String, Object>();
		hql.append(" from ").append(Task.class.getName()).append(" t where 1=1 ");

		// 条件
		if (ArrayUtils.isNotEmpty(status)) { // 任务状态
			hql.append(" and t.status in (:status) ");
		}

		if (isSearch != null) { // 是否需要搜索
			hql.append(" and t.isSearch = :isSearch ");
			pars.put("isSearch", isSearch);
		}
		if (isCollect != null) { // 是否收藏
			hql.append(" and t.isCollect = :isCollect ");
			pars.put("isCollect", isCollect);
		}
		if (isIDCard != null) { // 是否实名认证
			hql.append(" and t.isIDCard = :isIDCard ");
			pars.put("isIDCard", isIDCard);
		}
		if (isSincerity != null) { // 诚信商家
			hql.append(" and t.isSincerity = :isSincerity ");
			pars.put("isSincerity", isSincerity);
		}
		if (isWangWang != null) { // 是否旺聊
			hql.append(" and t.isWangWang = :isWangWang ");
			pars.put("isWangWang", isWangWang);
		}
		if (isReview != null) { // 是否需要审核
			hql.append(" and t.isReview = :isReview ");
			pars.put("isReview", isReview);
		}
		if (isUseQQ != null) { // 是否有QQ的参与
			hql.append(" and t.isUseQQ = :isUseQQ ");
			pars.put("isUseQQ", isUseQQ);
		}
		if (isUpdatePrice != null) { // 是否改价
			hql.append(" and t.isUpdatePrice = :isUpdatePrice ");
			pars.put("isUpdatePrice", isUpdatePrice);
		}
		if (isUpdateTaskDetail != null) { // 是否已经获取过任务详细信息
			hql.append(" and t.isUpdateTaskDetail = :isUpdateTaskDetail ");
			pars.put("isUpdateTaskDetail", isUpdateTaskDetail);
		}
		if (StringUtils.isNotBlank(taskerAccount)) { // 接手人
			hql.append(" and (t.taskerAccount = :taskerAccount or t.taskerAccount is null) ");
			pars.put("taskerAccount", taskerAccount);
		}
		if (isUpdateAddr != null) { // 是否改地址
			hql.append(" and t.isUpdateAddr = :isUpdateAddr ");
			pars.put("isUpdateAddr", isUpdateAddr);
		}
		if (isBaoGuo != null) { // 是否真实空包
			hql.append(" and t.isBaoGuo = :isBaoGuo ");
			pars.put("isBaoGuo", isBaoGuo);
		}

		// 时限
		if (CollectionUtils.isNotEmpty(taskTimeLimits)) {
			hql.append(" and t.timeLimit in (:timeLimits) ");
			pars.put("timeLimits", taskTimeLimits);
		}

		// 排序
		if (order != null) {
			switch (order) {
			case 发布点从高到低:
			case 每天发布点从高到低:
				hql.append(" order by t." + order.getFieldName() + " desc, t.number asc ");
				break;
			case 默认:
				hql.append(" order by t.number asc ");
				break;
			default:
				throw new SwitchPathException("排序Switch跳转到未知的分支");
			}
		} else {
			hql.append(" order by t.number asc ");
		}

		// 开始查询
		Query query = session.createQuery(hql.toString());
		if (ArrayUtils.isNotEmpty(status)) { // 任务状态
			query.setParameterList("status", status);
		}
		query.setProperties(pars);
		if (0 <= this.firstResult && 0 < this.maxResults && this.firstResult <= this.maxResults) {
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

	/**
	 * @return the status
	 */
	public TaskStatus[] getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TaskStatus... status) {
		this.status = status;
	}

	/**
	 * @return the order
	 */
	public TaskListOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(TaskListOrder order) {
		this.order = order;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxResults;
	}

	public void setPage(int firstResult, int maxResults) {
		this.firstResult = firstResult;
		this.maxResults = maxResults;
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
	 * @return the isUseQQ
	 */
	public Boolean getIsUseQQ() {
		return isUseQQ;
	}

	/**
	 * @param isUseQQ
	 *            the isUseQQ to set
	 */
	public void setIsUseQQ(Boolean isUseQQ) {
		this.isUseQQ = isUseQQ;
	}

	public Boolean getIsUpdatePrice() {
		return isUpdatePrice;
	}

	public void setIsUpdatePrice(Boolean isUpdatePrice) {
		this.isUpdatePrice = isUpdatePrice;
	}

	/**
	 * @return the isUpdateTaskDetail
	 */
	public Boolean getIsUpdateTaskDetail() {
		return isUpdateTaskDetail;
	}

	/**
	 * @param isUpdateTaskDetail
	 *            the isUpdateTaskDetail to set
	 */
	public void setIsUpdateTaskDetail(Boolean isUpdateTaskDetail) {
		this.isUpdateTaskDetail = isUpdateTaskDetail;
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
	 * @return the isUpdateAddr
	 */
	public Boolean getIsUpdateAddr() {
		return isUpdateAddr;
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

	/**
	 * @param isBaoGuo
	 *            the isBaoGuo to set
	 */
	public void setIsBaoGuo(Boolean isBaoGuo) {
		this.isBaoGuo = isBaoGuo;
	}

	public void addTaskTimeLimits(TaskTimeLimit... taskTimeLimits) {
		if (ArrayUtils.isNotEmpty(taskTimeLimits)) {
			for (TaskTimeLimit taskTimeLimit : taskTimeLimits) {
				if (taskTimeLimit != null) {
					this.taskTimeLimits.add(taskTimeLimit);
				}
			}
		}
	}

	public void removeTaskTimeLimits(TaskTimeLimit... taskTimeLimits) {
		if (ArrayUtils.isNotEmpty(taskTimeLimits)) {
			for (TaskTimeLimit taskTimeLimit : taskTimeLimits) {
				if (taskTimeLimit != null) {
					this.taskTimeLimits.remove(taskTimeLimit);
				}
			}
		}
	}

	/** 排序枚举 */
	public static enum TaskListOrder {
		默认(null, null), //
		发布点从高到低("publishingPoint", "desc"), //
		每天发布点从高到低("publishingPointOneDay", "desc"), //
		;
		private String fieldName;
		private String esc;

		/**
		 * @return the fieldName
		 */
		public String getFieldName() {
			return fieldName;
		}

		/**
		 * @return the esc
		 */
		public String getEsc() {
			return esc;
		}

		TaskListOrder(String fieldName, String esc) {
			this.fieldName = fieldName;
			this.esc = esc;
		}
	}
}
