package com.r.app.sample.vote.view;

import com.r.app.sample.vote.VoteItem;

/**
 * 问卷项在面板上的统一行为
 * 
 * @author Administrator
 *
 */
public interface VoteItemPanel {

	/** 填充问卷项 */
	void fillVoteItem(VoteItem voteItem);

	/** 当前问题是否已经回答正确 */
	boolean checkAnswer();
}
