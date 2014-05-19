package com.r.component.basicdata;

import java.util.Map;

/**
 * 基础数据定义
 * 
 * @author Rain
 *
 */
public interface Basicdata {

	/** 获得基础数据唯一ID */
	String getId();

	/** 获得基础数据名称 */
	String getName();

	/** 获得基础数据值 */
	String getValue();

	/** 获得基础数据描述 */
	String getDescription();

	/** 获得基础数据额外的 */
	Map<String, String> getOtherDatas();

	/**此基础数据能否展现到页面列表上*/
	boolean isCanShowListPage();
	
	/** 此基础数据能否编辑 */
	boolean isCanEdit();

	/** 此基础数据能否缓存 */
	boolean isCanCache();
}