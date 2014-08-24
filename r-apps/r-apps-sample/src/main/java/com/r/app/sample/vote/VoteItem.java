package com.r.app.sample.vote;

import java.util.List;

/**
 * 问卷项<br />
 * 请根据VoteItemType类型来强制转换成相应类来进行细节的设定
 * 
 * @author oky
 *
 */
public interface VoteItem {

	/** 返回问卷项的唯一标识,如果存在相同的id,则后被加载的问卷项覆盖先被加载的问卷项 */
	String getId();

	/** 返回问卷项编号 */
	String getNo();

	/** 返回问卷项问题 */
	String getQuestion();

	/** 返回问卷项备选答案 */
	List<Object> getAlternativeAnswers();

	/** 返回问卷项类型 */
	VoteItemType getType();

	/** 校验问卷项内容(问题,备选答案,正确答案)是否不为空,如果校验不通过.请抛出VoteItemContextErrorException异常 */
	void checkVoteItemContext();

	/** 校验问卷项答案 */
	boolean checkAnswer(Object... objects);
}