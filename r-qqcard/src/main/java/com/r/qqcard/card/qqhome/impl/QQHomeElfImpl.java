package com.r.qqcard.card.qqhome.impl;

import org.apache.commons.lang3.StringEscapeUtils;

import com.r.qqcard.card.qqhome.QQHomeElf;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 宠物信息
 * 
 * @author oky
 * 
 */
@XStreamAlias("elf")
public class QQHomeElfImpl implements QQHomeElf {

	@XStreamAsAttribute
	private int bname; // ?
	@XStreamAsAttribute
	private int type; // 类型
	@XStreamAsAttribute
	private int lost; // ?
	@XStreamAsAttribute
	private long losttime; // ?
	@XStreamAsAttribute
	private int hunger; // ?
	@XStreamAsAttribute
	private int powertype; // ?
	@XStreamAsAttribute
	private int power; // ?
	@XStreamAsAttribute
	private int exp; // 经验值
	@XStreamAsAttribute
	private long hungertime; // ?
	@XStreamAsAttribute
	private String count; // ?
	@XStreamAsAttribute
	private int guard; // ?
	@XStreamAsAttribute
	private int lv; // 等级

	/**
	 * @return the bname
	 */
	@Override
	public int getBname() {
		return bname;
	}

	/**
	 * @param bname
	 *            the bname to set
	 */
	public void setBname(int bname) {
		this.bname = bname;
	}

	/**
	 * @return the type
	 */
	@Override
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the lost
	 */
	@Override
	public int getLost() {
		return lost;
	}

	/**
	 * @param lost
	 *            the lost to set
	 */
	public void setLost(int lost) {
		this.lost = lost;
	}

	/**
	 * @return the losttime
	 */
	@Override
	public long getLosttime() {
		return losttime;
	}

	/**
	 * @param losttime
	 *            the losttime to set
	 */
	public void setLosttime(long losttime) {
		this.losttime = losttime;
	}

	/**
	 * @return the hunger
	 */
	@Override
	public int getHunger() {
		return hunger;
	}

	/**
	 * @param hunger
	 *            the hunger to set
	 */
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	/**
	 * @return the powertype
	 */
	@Override
	public int getPowertype() {
		return powertype;
	}

	/**
	 * @param powertype
	 *            the powertype to set
	 */
	public void setPowertype(int powertype) {
		this.powertype = powertype;
	}

	/**
	 * @return the power
	 */
	@Override
	public int getPower() {
		return power;
	}

	/**
	 * @param power
	 *            the power to set
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * @return the exp
	 */
	@Override
	public int getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * @return the hungertime
	 */
	@Override
	public long getHungertime() {
		return hungertime;
	}

	/**
	 * @param hungertime
	 *            the hungertime to set
	 */
	public void setHungertime(long hungertime) {
		this.hungertime = hungertime;
	}

	/**
	 * @return the count
	 */
	@Override
	public String getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(String count) {
		this.count = StringEscapeUtils.unescapeHtml4(count);
	}

	/**
	 * @return the guard
	 */
	@Override
	public int getGuard() {
		return guard;
	}

	/**
	 * @param guard
	 *            the guard to set
	 */
	public void setGuard(int guard) {
		this.guard = guard;
	}

	/**
	 * @return the lv
	 */
	@Override
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
