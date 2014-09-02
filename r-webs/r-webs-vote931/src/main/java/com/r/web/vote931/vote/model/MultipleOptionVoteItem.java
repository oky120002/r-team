/**
 * 
 */
package com.r.web.vote931.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.web.vote931.vote.VoteItem;
import com.r.web.vote931.vote.VoteItemType;
import com.r.web.vote931.vote.exception.AnswerTypeErrorException;
import com.r.web.vote931.vote.exception.VoteItemContextErrorException;

/**
 * 多选问卷项
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "voteitem_multiple")
@PrimaryKeyJoinColumn(name = "viid")
public class MultipleOptionVoteItem extends AbsVoteItem implements VoteItem {
	public static final String SPLIT = ",";

	public MultipleOptionVoteItem() {
		super(VoteItemType.multiple);
	}

	/** 答案1描述 */
	@Column
	private String answer1;
	/** 答案2描述 */
	@Column
	private String answer2;
	/** 答案3描述 */
	@Column
	private String answer3;
	/** 答案4描述 */
	@Column
	private String answer4;
	/** 正确答案 */
	@Column
	private String answer; // 以数字1~4,以逗号分割的字符串,最多4个最少1个数字
	/** 问卷项提供者 */
	@Column
	private String provider;

	/**
	 * 检验传入的答案是否正确<br />
	 * 请传入四个不重复的1~4的数字或者传入一个由1~4以及用逗号分割的字符串(从小到达排序)
	 */
	@Override
	public boolean checkAnswer(Object... objects) {
		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		Object obj = objects[0];
		if (obj instanceof String) {
			return answer.equals(StringUtils.join(objects, SPLIT));
		} else if (obj instanceof Number) {
			int size = ("_" + answer + "_").split(SPLIT).length - 1;
			String[] strs = new String[size];
			try {
				strs[0] = String.valueOf(objects[0]);
				strs[1] = String.valueOf(objects[1]);
				strs[2] = String.valueOf(objects[2]);
				strs[3] = String.valueOf(objects[3]);
			} catch (ArrayIndexOutOfBoundsException e) { // 捕获数组越界异常
			}
			return answer.equals(StringUtils.join(strs, SPLIT));
		} else {
			throw new AnswerTypeErrorException("多选题问卷项答案只能是备选答案之一");
		}
	}

	@Override
	public void checkVoteItemContext() throws VoteItemContextErrorException {
		super.checkVoteItemContext();
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
		if (StringUtils.isEmpty(answer)) {
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

	public String getAnswer() {
		return answer;
	}

	/** 设置答案,以数字1~4,以逗号分割的字符串,最多4个最少1个数字,且从小到达排序 */
	public void setAnswer(String answer) {
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
