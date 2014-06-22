/**
 * 
 */
package com.r.app.taobaoshua.bluesky.service.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskStatus;

/**
 * @author oky
 * 
 */
public class TaskQueryCommand implements QueryCommand<Task> {
	private TaskStatus[] status = null; // 任务状态
	private int order = -1; // 排序 1:发布点从高到低 , 2: 一天可以赚取的发布点数从高到低 , 其它:不排序
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

		// 排序
		if (-1 < order) {
			switch (order) {
			case 1:
				hql.append(" order by t.publishingPoint desc ");
				break;
			case 2:
				hql.append(" order by t.publishingPointOneDay desc ");
				break;
			}
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
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            排序 1:发布点从高到低 , 2: 一天可以赚取的发布点数从高到低 , 其它:不排序
	 */
	public void setOrder(int order) {
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

}
