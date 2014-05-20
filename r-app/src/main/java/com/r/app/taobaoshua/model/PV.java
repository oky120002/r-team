/**
 * 
 */
package com.r.app.taobaoshua.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 淘宝来路流量区数据模型
 * 
 * @author Administrator
 *
 */
public class PV implements Comparable<PV> {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String id; // 任务编号
	private String publisher; // 任务发布者
	private String sumFlow; // 总访问流量
	private String surplusFlow; // 剩余访问流量
	private String releaseTime; // 发布时间
	private String commodityNumber; // 商品数量
	private String url; // 接受url

	private boolean isStayFor5Minutes = false; // 是否停留5分钟
	private boolean isIntoStoreAndSearch = false; // 是否进店再搜索

	private Date tempReleaseTime; // 发布时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(String sumFlow) {
		this.sumFlow = sumFlow;
	}

	public String getSurplusFlow() {
		return surplusFlow;
	}

	public void setSurplusFlow(String surplusFlow) {
		this.surplusFlow = surplusFlow;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
		try {
			this.tempReleaseTime = sdf.parse(releaseTime);
		} catch (ParseException e) {
			this.tempReleaseTime = new Date();
		}
	}

	public String getCommodityNumber() {
		return commodityNumber;
	}

	public void setCommodityNumber(String commodityNumber) {
		this.commodityNumber = commodityNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isStayFor5Minutes() {
		return isStayFor5Minutes;
	}

	public void setStayFor5Minutes(boolean isStayFor5Minutes) {
		this.isStayFor5Minutes = isStayFor5Minutes;
	}

	public boolean isIntoStoreAndSearch() {
		return isIntoStoreAndSearch;
	}

	public void setIntoStoreAndSearch(boolean isIntoStoreAndSearch) {
		this.isIntoStoreAndSearch = isIntoStoreAndSearch;
	}

	public Date getTempReleaseTime() {
		return tempReleaseTime;
	}

	public void setTempReleaseTime(Date tempReleaseTime) {
		this.tempReleaseTime = tempReleaseTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PV other = (PV) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("任务编号 : ").append(this.id).append("\r\n");
		sb.append("任务发布者 : ").append(this.publisher).append("\r\n");
		sb.append("访问次数/流量 : ").append(this.sumFlow).append('/').append(this.surplusFlow).append("\r\n");
		sb.append("发布时间 : ").append(this.releaseTime);
		if (isStayFor5Minutes || isIntoStoreAndSearch) {
			sb.append('(');
			if (isStayFor5Minutes) {
				sb.append("停留5分钟");
			}
			if (isIntoStoreAndSearch) {
				sb.append(" , ").append("进店再搜索");
			}
			sb.append(')');
		}
		sb.append("\r\n");
		sb.append("商品数 : ").append(this.commodityNumber).append("\r\n");
		sb.append("接收url : ").append(this.url).append("\r\n");
		return sb.toString();
	}

	@Override
	public int compareTo(PV o) {
		return this.tempReleaseTime.compareTo(o.getTempReleaseTime());
	}

}
