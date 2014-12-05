package com.r.qqcard.card.qqhome;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * ?
 * 
 * @author oky
 * 
 */
@XStreamAlias("runeback")
public class QQHomeInfoRuneback {
	@XStreamAsAttribute
	private int id;
	@XStreamAsAttribute
	private int expire; // 有效期

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
	 * @return the expire
	 */
	public int getExpire() {
		return expire;
	}

	/**
	 * @param expire
	 *            the expire to set
	 */
	public void setExpire(int expire) {
		this.expire = expire;
	}

}
