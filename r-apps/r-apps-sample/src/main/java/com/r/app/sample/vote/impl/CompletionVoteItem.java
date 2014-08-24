/**
 * 
 */
package com.r.app.sample.vote.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.VoteItemType;
import com.r.app.sample.vote.exception.VoteItemContextErrorException;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 填空问卷项<br />
 * 空格处以四个"_"作为占位符 也就是"____"
 * 
 * @author oky
 * 
 */
@XStreamAlias("completion")
public class CompletionVoteItem extends AbstractVoteItem implements VoteItem {

	public CompletionVoteItem() {
		super(VoteItemType.填空);
	}
	
	/** 正确答案1 */
	@XStreamAlias("answer1")
	private String answer1;
	/** 正确答案2 */
	@XStreamAlias("answer2")
	private String answer2;
	/** 正确答案3 */
	@XStreamAlias("answer3")
	private String answer3;
	/** 正确答案4 */
	@XStreamAlias("answer4")
	private String answer4;

	/** 问卷项提供者 */
	@XStreamAlias("provider")
	private String provider;

	@Override
	public List<Object> getAlternativeAnswers() {
		return null;
	}

	@Override
	public boolean checkAnswer(Object... objects) {
		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		try {
			if (!answer1.equals(objects[0])) {
				return false;
			}
			if (!answer2.equals(objects[1])) {
				return false;
			}
			if (!answer3.equals(objects[2])) {
				return false;
			}
			if (!answer4.equals(objects[3])) {
				return false;
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
		}
		return true;
	}

	@Override
	public void checkVoteItemContext() {
		int size = ("," + super.question + ",").split("____").length - 1;
		int num = 0;
		if (StringUtils.isNotBlank(answer1)) {
			num++;
		}
		if (StringUtils.isNotBlank(answer2)) {
			num++;
		}
		if (StringUtils.isNotBlank(answer3)) {
			num++;
		}
		if (StringUtils.isNotBlank(answer4)) {
			num++;
		}
		if (num != size) {
			throw new VoteItemContextErrorException("填空问卷项[答案]数量和问题中空缺数量有差异!");
		}
	}

	/** 返回问起数量 */
	public int getQuestionSize() {
		return ("," + super.question + ",").split("____").length - 1;
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
