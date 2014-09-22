/**
 * 
 */
package com.r.web.module.vote.bean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.r.web.module.vote.VoteItemType;
import com.r.web.module.vote.exception.AnswerTypeErrorException;
import com.r.web.module.vote.model.VoteResultItem;
import com.r.web.module.vote.model.base.VoteBaseItemImpl;
import com.r.web.module.vote.service.VoteBaseItemService;

/**
 * @author rain
 *
 */
public class VoteResultOption extends VoteOption {

    private HttpServletRequest request;

    public VoteResultOption(HttpServletRequest request) {
        this.request = request;
    }

    /** 返回问卷ID */
    public String getVoteId() {
        return request.getParameter("vid");
    }

    /** 返回备注 */
    public String getRemark() {
        return request.getParameter("remark");
    }

    /** 问卷结果项 */
    public List<VoteResultItem> getVoteResultItems(VoteBaseItemService voteBaseItemService) {
        List<VoteResultItem> voteResultItems = new ArrayList<VoteResultItem>();
        String[] viids = request.getParameterValues("viid");
        String[] types = request.getParameterValues("type");

        int size = viids.length;
        for (int index = 0; index < size; index++) {
            VoteBaseItemImpl voteBaseItem = voteBaseItemService.find(viids[index]);
            VoteResultItem voteResultItem = new VoteResultItem();
            voteResultItem.setNo(index + 1);
            voteResultItem.setVoteBaseItem(voteBaseItem);
            if (VoteItemType.yesno.name().equals(types[index])) { // 单选题
                String answer = request.getParameter(viids[index] + "_answer");
                voteResultItem.setIsRight(voteBaseItem.checkAnswer(answer));
                voteResultItems.add(voteResultItem);
                continue;
            }
            if (VoteItemType.single.name().equals(types[index])) { // 单选题
                
                voteResultItems.add(voteResultItem);
                continue;
            }
            if (VoteItemType.multiple.name().equals(types[index])) { // 多选题

                voteResultItems.add(voteResultItem);
                continue;
            }
            if (VoteItemType.completion.name().equals(types[index])) { // 填空题

                voteResultItems.add(voteResultItem);
                continue;
            }
            throw new AnswerTypeErrorException("问卷结果项答案类型错误!");
        }
        return voteResultItems;
    }
}
