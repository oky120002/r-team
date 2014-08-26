package com.r.web.vote931.vote;

import java.util.ArrayList;
import java.util.List;

import com.r.web.vote931.vote.model.CompletionVoteItem;
import com.r.web.vote931.vote.model.MultipleOptionVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/***
 * 问卷项XML加载器容器
 * 
 * @author Administrator
 *
 */
@XStreamAlias("XmlLoader")
public class VoteItemXmlLoader {

	@XStreamImplicit(itemFieldName = "yesno")
	private List<YesOrNoVoteItem> yesnos = new ArrayList<YesOrNoVoteItem>();

	@XStreamImplicit(itemFieldName = "single")
	private List<SingleOptionVoteItem> singles = new ArrayList<SingleOptionVoteItem>();

	@XStreamImplicit(itemFieldName = "multiple")
	private List<MultipleOptionVoteItem> multiples = new ArrayList<MultipleOptionVoteItem>();

	@XStreamImplicit(itemFieldName = "completion")
	private List<CompletionVoteItem> completions = new ArrayList<CompletionVoteItem>();

	/** 添加问卷项 */
	public void add(VoteItem voteItem) {
		if (voteItem instanceof YesOrNoVoteItem) {
			yesnos.add((YesOrNoVoteItem) voteItem);
		}
		if (voteItem instanceof SingleOptionVoteItem) {
			singles.add((SingleOptionVoteItem) voteItem);
		}
		if (voteItem instanceof MultipleOptionVoteItem) {
			multiples.add((MultipleOptionVoteItem) voteItem);
		}
		if (voteItem instanceof CompletionVoteItem) {
			completions.add((CompletionVoteItem) voteItem);
		}
	}

	/** 返回问卷项数据 */
	public List<VoteItem> getVoteItems() {
		List<VoteItem> voteItmes = new ArrayList<VoteItem>();
		voteItmes.addAll(yesnos);
		voteItmes.addAll(singles);
		voteItmes.addAll(multiples);
		voteItmes.addAll(completions);
		return voteItmes;
	}

	/**
	 * @return the yesnos
	 */
	public List<YesOrNoVoteItem> getYesnos() {
		return yesnos;
	}

	/**
	 * @param yesnos
	 *            the yesnos to set
	 */
	public void setYesnos(List<YesOrNoVoteItem> yesnos) {
		this.yesnos = yesnos;
	}

	/**
	 * @return the singles
	 */
	public List<SingleOptionVoteItem> getSingles() {
		return singles;
	}

	/**
	 * @param singles
	 *            the singles to set
	 */
	public void setSingles(List<SingleOptionVoteItem> singles) {
		this.singles = singles;
	}

	/**
	 * @return the multiples
	 */
	public List<MultipleOptionVoteItem> getMultiples() {
		return multiples;
	}

	/**
	 * @param multiples
	 *            the multiples to set
	 */
	public void setMultiples(List<MultipleOptionVoteItem> multiples) {
		this.multiples = multiples;
	}

	/**
	 * @return the completions
	 */
	public List<CompletionVoteItem> getCompletions() {
		return completions;
	}

	/**
	 * @param completions
	 *            the completions to set
	 */
	public void setCompletions(List<CompletionVoteItem> completions) {
		this.completions = completions;
	}

}
