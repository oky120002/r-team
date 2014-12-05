package com.r.qqcard.card.qqhome;


/** 游戏数据 */
public interface QQHome {
	/** 获取? */
	int getCode();

	/** 获取信息 */
	QQHomeInfo getInfo();

	/** 获取? */
	QQHomeRentSlot getRentSlot();

	/** 获取? */
	QQHomeRefineguide getRefineguide();

	/** 获取欢迎信息 */
	QQHomeHello getHello();

	/** 获取账号 */
	QQHomeUser getUser();

	/** 获取宠物 */
	QQHomeElf getElf();

	/** 获取换卡箱 */
	QQHomeBoxChange getChangeBox();

	/** 获取卡片箱 */
	QQHomeBoxStore getStoreBox();

	/** 获取炼卡箱 */
	QQHomeBoxStove getStoveBox();
}