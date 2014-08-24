/**
 * 
 */
package com.r.app.sample.vote.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.VoteItemType;
import com.r.app.sample.vote.exception.AnswerTypeErrorException;
import com.r.app.sample.vote.exception.VoteItemContextErrorException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 是非问卷项
 * 
 * @author oky
 * 
 */
@XStreamAlias("yesno")
public class YesOrNoVoteItem extends AbstractVoteItem implements VoteItem {

	public YesOrNoVoteItem() {
		super(VoteItemType.是非);
	}

	/** 正确答案描述 */
	@XStreamAlias("answerYes")
	private String answerYes;
	/** 错误答案描述 */
	@XStreamAlias("answerNo")
	private String answerNo;
	/** 正确答案 */
	@XStreamAlias("answer")
	private Boolean answer;
	/** 问卷项提供者 */
	@XStreamAlias("provider")
	private String provider;

	@Override
	public List<Object> getAlternativeAnswers() {
		List<Object> list = new ArrayList<Object>();
		list.add(answerYes);
		list.add(answerNo);
		return list;
	}

	@Override
	public boolean checkAnswer(Object... objects) {
		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		boolean ans = false;
		Object obj = objects[0];
		if (obj instanceof String) {
			ans = answerYes.equals(obj);
		} else if (obj instanceof Boolean) {
			ans = ((Boolean) obj).booleanValue();
		} else {
			throw new AnswerTypeErrorException("是非题问卷项答案只能是备选答案之一");
		}

		return answer == ans;
	}

	@Override
	public void checkVoteItemContext() {
		if (StringUtils.isBlank(answerYes)) {
			throw new VoteItemContextErrorException("问卷项[正确答案描述]不能为空!");
		}
		if (StringUtils.isBlank(answerNo)) {
			throw new VoteItemContextErrorException("问卷项[错误答案描述]不能为空!");
		}
		if (answer == null) {
			throw new VoteItemContextErrorException("问卷项[答案]不能为空!");
		}
	}

	/** 获取正确答案描述 */
	public String getAnswerYes() {
		return answerYes;
	}

	/** 设置正确答案描述 */
	public void setAnswerYes(String answerYes) {
		this.answerYes = answerYes;
	}

	/** 获取错误答案描述 */
	public String getAnswerNo() {
		return answerNo;
	}

	/** 设置错误答案描述 */
	public void setAnswerNo(String answerNo) {
		this.answerNo = answerNo;
	}

	/**
	 * @return the answer
	 */
	public Boolean getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
