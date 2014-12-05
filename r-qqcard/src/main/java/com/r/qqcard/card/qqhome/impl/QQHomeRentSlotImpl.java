package com.r.qqcard.card.qqhome.impl;

import com.r.qqcard.card.qqhome.QQHomeRentSlot;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * ?
 * 
 * @author oky
 * 
 */
@XStreamAlias("rent_slot")
public class QQHomeRentSlotImpl implements QQHomeRentSlot {

	@XStreamAsAttribute
	private int rentCount; // ?
	@XStreamAsAttribute
	private int maxCount; // ?

	/**
	 * @return the rentCount
	 */
	@Override
	public int getRentCount() {
		return rentCount;
	}

	/**
	 * @param rentCount
	 *            the rentCount to set
	 */
	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}

	/**
	 * @return the maxCount
	 */
	@Override
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * @param maxCount
	 *            the maxCount to set
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

}
