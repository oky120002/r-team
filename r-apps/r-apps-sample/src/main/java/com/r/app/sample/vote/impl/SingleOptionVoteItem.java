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
 * 单选问卷项
 * 
 * @author oky
 * 
 */
@XStreamAlias("single")
public class SingleOptionVoteItem extends AbstractVoteItem implements VoteItem {

	public SingleOptionVoteItem() {
		super(VoteItemType.单选);
	}

	/** 答案1描述 */
	@XStreamAlias("answer1")
	private String answer1;
	/** 答案2描述 */
	@XStreamAlias("answer2")
	private String answer2;
	/** 答案3描述 */
	@XStreamAlias("answer3")
	private String answer3;
	/** 答案4描述 */
	@XStreamAlias("answer4")
	private String answer4;
	/** 正确答案 */
	@XStreamAlias("answer")
	private Integer answer;
	/** 问卷项提供者 */
	@XStreamAlias("provider")
	private String provider;

	@Override
	public List<Object> getAlternativeAnswers() {
		List<Object> list = new ArrayList<Object>();
		list.add(answer1);
		list.add(answer2);
		list.add(answer3);
		list.add(answer4);
		return list;
	}

	@Override
	public boolean checkAnswer(Object... objects) {
		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		int ans = -1;
		Object obj = objects[0];
		if (obj instanceof String) {
			if (answer1.equals(obj)) {
				ans = 1;
			}
			if (answer2.equals(obj)) {
				ans = 2;
			}
			if (answer3.equals(obj)) {
				ans = 3;
			}
			if (answer4.equals(obj)) {
				ans = 4;
			}
		} else if (obj instanceof Number) {
			ans = ((Number) obj).intValue();
		} else {
			throw new AnswerTypeErrorException("单选题问卷项答案只能是备选答案之一");
		}

		return answer == ans;
	}

	@Override
	public void checkVoteItemContext() {
		if (StringUtils.isBlank(answer1)) {
			throw new VoteItemContextErrorException("问卷项[答案1描述]不能为空!");
		}
		if (StringUtils.isBlank(answer2)) {
			throw new VoteItemContextErrorException("问卷项[答案2描述]不能为空!");
		}
		if (StringUtils.isBlank(answer3)) {
			throw new VoteItemContextErrorException("问卷项[答案3描述]不能为空!");
		}
		if (StringUtils.isBlank(answer4)) {
			throw new VoteItemContextErrorException("问卷项[答案4描述]不能为空!");
		}
		if (answer == null) {
			throw new VoteItemContextErrorException("问卷项[答案]不能为空!");
		}
	}

	/**
	 * @return the answer1
	 */
	public String getAnswer1() {
		return answer1;
	}

	/**
	 * @param answer1
	 *            the answer1 to set
	 */
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	/**
	 * @return the answer2
	 */
	public String getAnswer2() {
		return answer2;
	}

	/**
	 * @param answer2
	 *            the answer2 to set
	 */
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	/**
	 * @return the answer3
	 */
	public String getAnswer3() {
		return answer3;
	}

	/**
	 * @param answer3
	 *            the answer3 to set
	 */
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	/**
	 * @return the answer4
	 */
	public String getAnswer4() {
		return answer4;
	}

	/**
	 * @param answer4
	 *            the answer4 to set
	 */
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	/**
	 * @return the answer
	 */
	public Integer getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(Integer answer) {
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