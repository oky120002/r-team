package com.r.qqcard.card.qqhome.impl;

import com.r.qqcard.card.qqhome.QQHomeHello;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 欢迎信息
 * 
 * @author oky
 * 
 */
@XStreamAlias("hello")
public class QQHomeHelloImpl implements QQHomeHello {

	/** 登陆时间 */
	@XStreamAsAttribute
	private long time;

	/**
	 * @return the time
	 */
	@Override
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

}
