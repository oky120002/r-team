/**
 * 
 */
package com.r.web.module.vote.discard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.r.core.util.RandomUtil;
import com.r.core.util.XStreamUtil;
import com.r.web.module.vote.VoteBaseItem;
import com.r.web.module.vote.VoteItemType;

/**
 * @author oky
 * 
 */
public class VoteContext1 {

    private static VoteContext1 context; // 问答容器
    private List<VoteBaseItem> votes = new ArrayList<VoteBaseItem>(); // 按照加载顺序保存问卷项
    private Map<String, VoteBaseItem> voteItems = new HashMap<String, VoteBaseItem>(); // 按照id保存问卷项
    private Map<VoteItemType, List<VoteBaseItem>> voteItemTypes = new HashMap<VoteItemType, List<VoteBaseItem>>();// 按照问卷项类型保存问卷项

    /** 初始化方法 */
    public static VoteContext1 init() {
        if (VoteContext1.context == null) {
            VoteContext1.context = new VoteContext1();
            VoteContext1.context.loadVotes();
        }
        return VoteContext1.getInstance();
    }

    public static VoteContext1 getInstance() {
        return VoteContext1.context;
    }

    /** 加载问答数据 */
    private void loadVotes() {
        // 先从本地加载xml,以后阿里云服务器后,从网络加载

//        for (VoteItemType type : VoteItemType.values()) {
//            VoteContext1.context.voteItemTypes.put(type, new ArrayList<VoteItem>());
//        }

        String xml = null;
        try {
            xml = FileUtils.readFileToString(new File("vote/vote.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        VoteItemXmlLoader xmlLoader = XStreamUtil.fromXML(VoteItemXmlLoader.class, xml);

        for (VoteBaseItem vi : xmlLoader.getVoteItems()) {
            votes.add(vi);
            voteItems.put(vi.getId(), vi);
            voteItemTypes.get(vi.getType()).add(vi);
        }
    }

    /** 随机返回问卷项集合 */
    public List<VoteBaseItem> randomVoteItems(int size) {
        int voteSize = votes.size();
        List<VoteBaseItem> voteList = new ArrayList<VoteBaseItem>();
        if (voteSize > 0) {
            for (int i = 0; i < size && i < voteSize; i++) {
                voteList.add(randomNextVoteItem());
            }
        }
        return voteList;
    }

    /** 随机返回下一个问卷项 */
    public VoteBaseItem randomNextVoteItem() {
        return votes.get(RandomUtil.randomInteger(0, votes.size()));
    }

    /** 问卷项个数 */
    public int getVoteItemSize() {
        return this.votes.size();
    }
}
