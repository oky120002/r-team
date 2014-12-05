package com.r.qqcard.card.qqhome;

/**
 * 游戏账号信息
 * 
 * @author oky
 * 
 */
public interface QQHomeUser {

	/** 获取账号 */
	String getUin();

	/** 获取昵称 */
	String getNick();

	/** 获取性别 */
	String getSexString();

	/** 获取金币 */
	int getMoney();

	/** 获取经验值 */
	int getExp();

	/** 获取等级 */
	int getLv();

	/** 获取魔法值 */
	int getMana();

	/** 获取? */
	int getLvupbonus();

	/** 获取启用魔法卡片时间 */
	long getRegti();

	/** 获取最后登陆时间 */
	long getLastloginti();

	/** 获取可以抽卡张数 */
	int getRandchance();

	/** 获取最后抽卡时间 */
	long getLastrandti();

	/** 获取? */
	int getMissionid();

	/** 获取? */
	int getMissionstep();

	/** 获取? */
	int getMissionflag();

	/** 获取最后获取金币时间? */
	long getLastgetmoneyti();

	/** 获取是否红钻会员 */
	int getRedvip();

	/** 获取红钻等级 */
	int getRedlv();

	/** 获取? */
	int getMissionv3();

	/** 获取留言信息条数? */
	int getHasmsg();

	/** 获取是否年年费红钻 */
	int getYearvip();

	/** 获取宠物名称 */
	String getElfname();

	/** 值类型 */
	public static enum HomeUserType {
		Money, // 金币
		Randchance, // 可以抽卡张数
		Exp, // 经验值
		;
	}
}