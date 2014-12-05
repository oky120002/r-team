/**
 * 
 */
package com.r.qqcard.card.qqhome.other;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 一键贩卖卡片返回xml实体
 * 
 * @author oky
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQOneSellCard {
	@XStreamAsAttribute
	private int code; // ?
	@XStreamAsAttribute
	private int money; // 贩卖后所拥有的金币

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the money
	 */
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

}
