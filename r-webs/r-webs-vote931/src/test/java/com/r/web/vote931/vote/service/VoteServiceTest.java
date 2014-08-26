package com.r.web.vote931.vote.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.vote931.vote.model.AbsVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class VoteServiceTest {

    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Test
    public void test() {
        // 删除
        int deleteAll = voteService.deleteAll();
        System.out.println("删除" + deleteAll + "个问卷项");

        // 新增
        SingleOptionVoteItem single = voteService.createSingleOptionVoteItem("菇凉所属工会id是多少?", 2, "2086", "931", "2080", "99", "筝团 - 雨");
        YesOrNoVoteItem yesno = voteService.createYesOrNoVoteItem("菇凉真名风小筝?", false, "是的", "不是的", "筝团 - 雨");

        // 根据id查询
        AbsVoteItem voteIten = voteService.findById(single.getId());
        System.out.println(voteIten.getQuestion());

        // 根据编号查询
        voteIten = voteService.findByNo(yesno.getNo());
        System.out.println(voteIten.getQuestion());
    }
}
