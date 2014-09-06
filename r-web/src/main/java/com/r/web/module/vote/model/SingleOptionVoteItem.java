/**
 * 
 */
package com.r.web.module.vote.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.web.module.vote.VoteItem;
import com.r.web.module.vote.VoteItemType;
import com.r.web.module.vote.exception.AnswerTypeErrorException;
import com.r.web.module.vote.exception.VoteItemContextErrorException;

/**
 * 单选问卷项
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "voteitem_single")
@PrimaryKeyJoinColumn(name = "viid")
public class SingleOptionVoteItem extends AbsVoteItem implements VoteItem, Serializable {
	private static final long serialVersionUID = 9095859661204915202L;

	public SingleOptionVoteItem() {
		super(VoteItemType.single);
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
	private Integer answer;
	/** 问卷项提供者 */
	@Column
	private String provider;

	/**
	 * 检验传入的答案是否正确<br />
	 * 请传入一个1~4数字
	 */
	@Override
	public boolean checkAnswer(Object... objects) {
		if (ArrayUtils.isEmpty(objects)) {
			return false;
		}

		int ans = -1;
		Object obj = objects[0];
		if (obj instanceof Number) {
			ans = ((Number) obj).intValue();
		} else {
			throw new AnswerTypeErrorException("单选题问卷项答案只能是备选答案之一");
		}

		return answer == ans;
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
		if (answer == null) {
			throw new VoteItemContextErrorException("问卷项[答案]不能为空!");
		}
	}

	@Override
	public String getId() {
		return id;
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

	/** 设置答案,是1~4中的一个数字 */
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
