package com.r.web.module.vote.discard;

import java.util.ArrayList;
import java.util.List;

import com.r.web.module.vote.VoteBaseItem;
import com.r.web.module.vote.model.base.CompletionVoteBaseItem;
import com.r.web.module.vote.model.base.MultipleOptionVoteBaseItem;
import com.r.web.module.vote.model.base.SingleOptionVoteBaseItem;
import com.r.web.module.vote.model.base.YesOrNoVoteBaseItem;
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
	private List<YesOrNoVoteBaseItem> yesnos = new ArrayList<YesOrNoVoteBaseItem>();

	@XStreamImplicit(itemFieldName = "single")
	private List<SingleOptionVoteBaseItem> singles = new ArrayList<SingleOptionVoteBaseItem>();

	@XStreamImplicit(itemFieldName = "multiple")
	private List<MultipleOptionVoteBaseItem> multiples = new ArrayList<MultipleOptionVoteBaseItem>();

	@XStreamImplicit(itemFieldName = "completion")
	private List<CompletionVoteBaseItem> completions = new ArrayList<CompletionVoteBaseItem>();

	/** 添加问卷项 */
	public void add(VoteBaseItem voteItem) {
		if (voteItem instanceof YesOrNoVoteBaseItem) {
			yesnos.add((YesOrNoVoteBaseItem) voteItem);
		}
		if (voteItem instanceof SingleOptionVoteBaseItem) {
			singles.add((SingleOptionVoteBaseItem) voteItem);
		}
		if (voteItem instanceof MultipleOptionVoteBaseItem) {
			multiples.add((MultipleOptionVoteBaseItem) voteItem);
		}
		if (voteItem instanceof CompletionVoteBaseItem) {
			completions.add((CompletionVoteBaseItem) voteItem);
		}
	}

	/** 返回问卷项数据 */
	public List<VoteBaseItem> getVoteItems() {
		List<VoteBaseItem> voteItmes = new ArrayList<VoteBaseItem>();
		voteItmes.addAll(yesnos);
		voteItmes.addAll(singles);
		voteItmes.addAll(multiples);
		voteItmes.addAll(completions);
		return voteItmes;
	}

	/**
	 * @return the yesnos
	 */
	public List<YesOrNoVoteBaseItem> getYesnos() {
		return yesnos;
	}

	/**
	 * @param yesnos
	 *            the yesnos to set
	 */
	public void setYesnos(List<YesOrNoVoteBaseItem> yesnos) {
		this.yesnos = yesnos;
	}

	/**
	 * @return the singles
	 */
	public List<SingleOptionVoteBaseItem> getSingles() {
		return singles;
	}

	/**
	 * @param singles
	 *            the singles to set
	 */
	public void setSingles(List<SingleOptionVoteBaseItem> singles) {
		this.singles = singles;
	}

	/**
	 * @return the multiples
	 */
	public List<MultipleOptionVoteBaseItem> getMultiples() {
		return multiples;
	}

	/**
	 * @param multiples
	 *            the multiples to set
	 */
	public void setMultiples(List<MultipleOptionVoteBaseItem> multiples) {
		this.multiples = multiples;
	}

	/**
	 * @return the completions
	 */
	public List<CompletionVoteBaseItem> getCompletions() {
		return completions;
	}

	/**
	 * @param completions
	 *            the completions to set
	 */
	public void setCompletions(List<CompletionVoteBaseItem> completions) {
		this.completions = completions;
	}

}
