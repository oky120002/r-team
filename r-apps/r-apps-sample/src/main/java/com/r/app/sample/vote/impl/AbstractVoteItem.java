/**
 * 
 */
package com.r.app.sample.vote.impl;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.VoteItemType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 问卷基类
 * 
 * @author Administrator
 *
 */
public abstract class AbstractVoteItem implements VoteItem {

	protected AbstractVoteItem(VoteItemType type) {
		super();
		this.type = type;
	}

	@XStreamAlias("id")
	protected String id;
	/** 问卷项编号 */
	@XStreamAlias("no")
	protected String no;
	/** 问卷项问题 */
	@XStreamAlias("question")
	protected String question;
	/** 问卷项类型 */
	@XStreamAlias("type")
	protected VoteItemType type;

	@Override
	public String getId() {
		return id;
	}

	/** 设置问卷项唯一标识 */
	public void setId(String id) {
		this.id = id;
	}

	/** 返回问卷项编号 */
	@Override
	public String getNo() {
		return no;
	}

	/** 设置问卷号编号 */
	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	/** 设置问卷项问题 */
	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public VoteItemType getType() {
		return type;
	}

	/** 设置问卷项类型 */
	public void setType(VoteItemType type) {
		this.type = type;
	}
}
