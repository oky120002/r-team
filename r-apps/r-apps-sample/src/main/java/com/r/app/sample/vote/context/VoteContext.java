/**
 * 
 */
package com.r.app.sample.vote.context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.VoteItemType;
import com.r.app.sample.vote.VoteItemXmlLoader;
import com.r.core.util.RandomUtil;
import com.r.core.util.XStreamUtil;

/**
 * @author oky
 * 
 */
public class VoteContext {
	private static VoteContext context; // 问答容器
	private List<VoteItem> votes = new ArrayList<VoteItem>(); // 加载顺序保存问卷项
	private Map<String, VoteItem> voteItems = new HashMap<String, VoteItem>(); // 按照id保存问卷项
	private Map<VoteItemType, List<VoteItem>> voteItemTypes = new HashMap<VoteItemType, List<VoteItem>>();// 按照问卷项类型保存问卷项

	/** 初始化方法 */
	public static VoteContext init() {
		if (VoteContext.context == null) {
			VoteContext.context = new VoteContext();
			VoteContext.context.loadVotes();
		}
		return VoteContext.getInstance();
	}

	public static VoteContext getInstance() {
		return VoteContext.context;
	}

	/** 加载问答数据 */
	private void loadVotes() {
		// 先从本地加载xml,以后阿里云服务器后,从网络加载

		for (VoteItemType type : VoteItemType.values()) {
			VoteContext.context.voteItemTypes.put(type, new ArrayList<VoteItem>());
		}

		String xml = null;
		try {
			xml = FileUtils.readFileToString(new File("vote/vote.xml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		VoteItemXmlLoader xmlLoader = XStreamUtil.fromXML(VoteItemXmlLoader.class, xml);

		for (VoteItem vi : xmlLoader.getVoteItems()) {
			votes.add(vi);
			voteItems.put(vi.getId(), vi);
			voteItemTypes.get(vi.getType()).add(vi);
		}
	}

	/** 随机返回问卷项集合 */
	public List<VoteItem> randomVoteItems(int size) {
		int voteSize = votes.size();
		List<VoteItem> voteList = new ArrayList<VoteItem>();
		if (voteSize > 0) {
			for (int i = 0; i < size && i < voteSize; i++) {
				voteList.add(randomNextVoteItem());
			}
		}
		return voteList;
	}

	/** 随机返回下一个问卷项 */
	public VoteItem randomNextVoteItem() {
		return votes.get(RandomUtil.randomInteger(0, votes.size()));
	}

	/** 问卷项个数 */
	public int getVoteItemSize() {
		return this.votes.size();
	}
}
