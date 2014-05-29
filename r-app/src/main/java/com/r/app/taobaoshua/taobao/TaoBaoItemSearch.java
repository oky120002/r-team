package com.r.app.taobaoshua.taobao;

/**
 * 淘宝搜索条件
 * 
 * @author oky
 * 
 */
public interface TaoBaoItemSearch {

	/** 搜索关键字 */
	String getSearchKey();

	/** 最低价格 */
	int getPriceMin();

	/** 最高价格 */
	int getPriceMax();

	/** 所在地 */
	String getLocation();

	/** 是否天猫店 */
	boolean isTmall();
}