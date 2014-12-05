package com.r.qqcard.card.qqhome.impl;

import org.apache.commons.lang3.StringEscapeUtils;

import com.r.qqcard.card.qqhome.QQHomeUser;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 账号信息
 * 
 * @author oky
 * 
 */
@XStreamAlias(value = "user", impl = QQHomeUserImpl.class)
public class QQHomeUserImpl implements QQHomeUser {
	@XStreamAsAttribute
	private String uin; // 账号
	@XStreamAsAttribute
	private String nick; // 昵称
	@XStreamAsAttribute
	private int sex; // 性别
	@XStreamAsAttribute
	private int money; // 金钱
	@XStreamAsAttribute
	private int exp; // 经验值
	@XStreamAsAttribute
	private int lv; // 等级
	@XStreamAsAttribute
	private int mana; // 魔法值
	@XStreamAsAttribute
	private int lvupbonus;// ?
	@XStreamAsAttribute
	private long regti; // 注册时间?
	@XStreamAsAttribute
	private long lastloginti; // 最后登陆时间
	@XStreamAsAttribute
	private int randchance; // 可以抽卡张数
	@XStreamAsAttribute
	private long lastrandti; // 最后抽卡时间
	@XStreamAsAttribute
	private int missionid; // ?
	@XStreamAsAttribute
	private int missionstep; // ?
	@XStreamAsAttribute
	private int missionflag; // ?
	@XStreamAsAttribute
	private long lastgetmoneyti; // 最后获取金钱时间?
	@XStreamAsAttribute
	private int redvip; // 红钻?
	@XStreamAsAttribute
	private int redlv; // 红钻等级?
	@XStreamAsAttribute
	private int missionv3; // ?
	@XStreamAsAttribute
	private int hasmsg; // 拥有消息的条数?
	@XStreamAsAttribute
	private int yearvip; // 年费红钻
	@XStreamAsAttribute
	private String elfname; // 宠物昵称

	/**
	 * @return the uin
	 */
	@Override
	public String getUin() {
		return uin;
	}

	/**
	 * @param uin
	 *            the uin to set
	 */
	public void setUin(String uin) {
		this.uin = uin;
	}

	/**
	 * @return the nick
	 */
	@Override
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	@Override
	public String getSexString() {
		return sex == 1 ? "女" : (sex == 2 ? "男" : "未知");
	}

	/**
	 * @return the money
	 */
	@Override
	public int getMoney() {
		return money;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
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

	/**
	 * @return the mana
	 */
	@Override
	public int getMana() {
		return mana;
	}

	/**
	 * @param mana
	 *            the mana to set
	 */
	public void setMana(int mana) {
		this.mana = mana;
	}

	/**
	 * @return the lvupbonus
	 */
	@Override
	public int getLvupbonus() {
		return lvupbonus;
	}

	/**
	 * @param lvupbonus
	 *            the lvupbonus to set
	 */
	public void setLvupbonus(int lvupbonus) {
		this.lvupbonus = lvupbonus;
	}

	/**
	 * @return the regti
	 */
	@Override
	public long getRegti() {
		return regti;
	}

	/**
	 * @param regti
	 *            the regti to set
	 */
	public void setRegti(long regti) {
		this.regti = regti;
	}

	/**
	 * @return the lastloginti
	 */
	@Override
	public long getLastloginti() {
		return lastloginti;
	}

	/**
	 * @param lastloginti
	 *            the lastloginti to set
	 */
	public void setLastloginti(long lastloginti) {
		this.lastloginti = lastloginti;
	}

	/**
	 * @return the randchance
	 */
	@Override
	public int getRandchance() {
		return randchance;
	}

	/**
	 * @param randchance
	 *            the randchance to set
	 */
	public void setRandchance(int randchance) {
		this.randchance = randchance;
	}

	/**
	 * @return the lastrandti
	 */
	@Override
	public long getLastrandti() {
		return lastrandti;
	}

	/**
	 * @param lastrandti
	 *            the lastrandti to set
	 */
	public void setLastrandti(long lastrandti) {
		this.lastrandti = lastrandti;
	}

	/**
	 * @return the missionid
	 */
	@Override
	public int getMissionid() {
		return missionid;
	}

	/**
	 * @param missionid
	 *            the missionid to set
	 */
	public void setMissionid(int missionid) {
		this.missionid = missionid;
	}

	/**
	 * @return the missionstep
	 */
	@Override
	public int getMissionstep() {
		return missionstep;
	}

	/**
	 * @param missionstep
	 *            the missionstep to set
	 */
	public void setMissionstep(int missionstep) {
		this.missionstep = missionstep;
	}

	/**
	 * @return the missionflag
	 */
	@Override
	public int getMissionflag() {
		return missionflag;
	}

	/**
	 * @param missionflag
	 *            the missionflag to set
	 */
	public void setMissionflag(int missionflag) {
		this.missionflag = missionflag;
	}

	/**
	 * @return the lastgetmoneyti
	 */
	@Override
	public long getLastgetmoneyti() {
		return lastgetmoneyti;
	}

	/**
	 * @param lastgetmoneyti
	 *            the lastgetmoneyti to set
	 */
	public void setLastgetmoneyti(long lastgetmoneyti) {
		this.lastgetmoneyti = lastgetmoneyti;
	}

	/**
	 * @return the redvip
	 */
	@Override
	public int getRedvip() {
		return redvip;
	}

	/**
	 * @param redvip
	 *            the redvip to set
	 */
	public void setRedvip(int redvip) {
		this.redvip = redvip;
	}

	/**
	 * @return the redlv
	 */
	@Override
	public int getRedlv() {
		return redlv;
	}

	/**
	 * @param redlv
	 *            the redlv to set
	 */
	public void setRedlv(int redlv) {
		this.redlv = redlv;
	}

	/**
	 * @return the missionv3
	 */
	@Override
	public int getMissionv3() {
		return missionv3;
	}

	/**
	 * @param missionv3
	 *            the missionv3 to set
	 */
	public void setMissionv3(int missionv3) {
		this.missionv3 = missionv3;
	}

	/**
	 * @return the hasmsg
	 */
	@Override
	public int getHasmsg() {
		return hasmsg;
	}

	/**
	 * @param hasmsg
	 *            the hasmsg to set
	 */
	public void setHasmsg(int hasmsg) {
		this.hasmsg = hasmsg;
	}

	/**
	 * @return the yearvip
	 */
	@Override
	public int getYearvip() {
		return yearvip;
	}

	/**
	 * @param yearvip
	 *            the yearvip to set
	 */
	public void setYearvip(int yearvip) {
		this.yearvip = yearvip;
	}

	/**
	 * @return the elfname
	 */
	@Override
	public String getElfname() {
		return elfname;
	}

	/**
	 * @param elfname
	 *            the elfname to set
	 */
	public void setElfname(String elfname) {
		this.elfname = StringEscapeUtils.unescapeHtml4(elfname);
	}

}
