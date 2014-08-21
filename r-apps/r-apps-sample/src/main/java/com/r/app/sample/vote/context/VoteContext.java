/**
 * 
 */
package com.r.app.sample.vote.context;

import java.util.Collection;

import com.r.app.sample.vote.Vote;
import com.r.app.sample.vote.VoteItem;

/**
 * @author oky
 * 
 */
public class VoteContext {
    private static VoteContext context; // 问答容器

    private Collection<VoteItem> votes; // 所有问题

    /** 初始化方法 */
    public static void init() {
        VoteContext.context = new VoteContext();
    }

    public static VoteContext getInstance() {
        return VoteContext.context;
    }

    /** 加载问答数据 */
    private void loadVotes() {
        // 先从本地加载xml,以后阿里云服务器后,从网络加载

    }

    /** 随机返回一个问卷 */
    public Vote randomVote() {
        return null;
    }

    /** 随机返回一个问题 */
    public VoteItem randomVoteItem() {
        return null;
    }
}
