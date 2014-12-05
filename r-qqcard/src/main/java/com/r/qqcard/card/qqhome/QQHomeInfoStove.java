package com.r.qqcard.card.qqhome;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 炼卡信息
 * 
 * @author oky
 * 
 */
@XStreamAlias("stove")
public class QQHomeInfoStove {
	@XStreamAsAttribute
	private int id;
	@XStreamAsAttribute
	private long endtime; // 结束时间
	@XStreamAsAttribute
	private int lv; // 等级

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the endtime
	 */
	public long getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            the endtime to set
	 */
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the lv
	 */
	public int getLv() {
		return lv;
	}

	/**
	 * @param lv
	 *            the lv to set
	 */
	public void setLv(int lv) {
		this.lv = lv;
	}

}
