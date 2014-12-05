package com.r.qqcard.card.qqhome;

import java.util.List;

/**
 * 信息
 * 
 * @author oky
 * 
 */
public interface QQHomeInfo {

	/** ? */
	QQHomeInfoRuneback getInfoRuneback();

	/** 获得炼卡信息 */
	List<QQHomeInfoStove> getInfoStoves();

}