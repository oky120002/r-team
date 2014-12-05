package com.r.qqcard.card.qqhome.impl;

import com.r.qqcard.card.qqhome.QQHomeRefineguide;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * ?
 * 
 * @author oky
 * 
 */
@XStreamAlias("refineguide")
public class QQHomeRefineguideImpl implements QQHomeRefineguide {
	@XStreamAsAttribute
	private int tid;// ?
	@XStreamAsAttribute
	private int lt;// ?
	@XStreamAsAttribute
	private int st;// ?
	@XStreamAsAttribute
	private int remainderNum;// ?
	@XStreamAsAttribute
	private int flag; // ?

	/**
	 * @return the tid
	 */
	@Override
	public int getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(int tid) {
		this.tid = tid;
	}

	/**
	 * @return the lt
	 */
	@Override
	public int getLt() {
		return lt;
	}

	/**
	 * @param lt
	 *            the lt to set
	 */
	public void setLt(int lt) {
		this.lt = lt;
	}

	/**
	 * @return the st
	 */
	@Override
	public int getSt() {
		return st;
	}

	/**
	 * @param st
	 *            the st to set
	 */
	public void setSt(int st) {
		this.st = st;
	}

	/**
	 * @return the remainderNum
	 */
	@Override
	public int getRemainderNum() {
		return remainderNum;
	}

	/**
	 * @param remainderNum
	 *            the remainderNum to set
	 */
	public void setRemainderNum(int remainderNum) {
		this.remainderNum = remainderNum;
	}

	/**
	 * @return the flag
	 */
	@Override
	public int getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

}
