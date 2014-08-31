/**
 * 
 */
package com.r.web.vote931.vote.bean;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 开始问卷时的选项,主要起一个传值的作用
 * 
 * @author Administrator
 *
 */
public class VoteOption {

	private HttpServletRequest request;

	private String signature;
	private String title;
	private int visize;

	public VoteOption() {
		super();
		capture();
	}

	public VoteOption(HttpServletRequest request) {
		this.request = request;
		capture();
	}

	/** 抓取数据 */
	private void capture() {
		String tempSignature = (request == null ? null : this.request.getParameter("signature"));
		String tempTitle = (request == null ? null : this.request.getParameter("title"));
		String tempvisize = (request == null ? null : this.request.getParameter("visize"));

		this.signature = (StringUtils.isBlank(tempSignature) ? "这个人很懒，没有留下签名。" : tempSignature);
		this.title = (StringUtils.isBlank(tempTitle) ? "这个人很懒，没有留下问卷标题。" : tempTitle);
		this.visize = (NumberUtils.isNumber(tempvisize) ? Integer.valueOf(tempvisize) : 25);
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the visize
	 */
	public int getVisize() {
		return visize;
	}

	/**
	 * @param visize
	 *            the visize to set
	 */
	public void setVisize(int visize) {
		this.visize = visize;
	}

}
