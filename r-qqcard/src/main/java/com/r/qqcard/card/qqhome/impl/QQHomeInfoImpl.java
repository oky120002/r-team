package com.r.qqcard.card.qqhome.impl;
import java.util.ArrayList;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeInfo;
import com.r.qqcard.card.qqhome.QQHomeInfoRuneback;
import com.r.qqcard.card.qqhome.QQHomeInfoStove;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 信息
 * 
 * @author oky
 * 
 */
@XStreamAlias("info")
public class QQHomeInfoImpl implements QQHomeInfo {
	@XStreamAsAttribute
	private QQHomeInfoRuneback infoRuneback; // ?
	@XStreamImplicit(itemFieldName = "stove")
	private List<QQHomeInfoStove> infoStoves = new ArrayList<QQHomeInfoStove>(); // 炼卡信息

	/**
	 * @return the infoRuneback
	 */
	@Override
	public QQHomeInfoRuneback getInfoRuneback() {
		return infoRuneback;
	}

	/**
	 * @param infoRuneback
	 *            the infoRuneback to set
	 */
	public void setInfoRuneback(QQHomeInfoRuneback infoRuneback) {
		this.infoRuneback = infoRuneback;
	}

	/**
	 * @return the infoStoves
	 */
	@Override
	public List<QQHomeInfoStove> getInfoStoves() {
		return infoStoves;
	}

	/**
	 * @param infoStoves
	 *            the infoStoves to set
	 */
	public void setInfoStoves(List<QQHomeInfoStove> infoStoves) {
		this.infoStoves = infoStoves;
	}

}
