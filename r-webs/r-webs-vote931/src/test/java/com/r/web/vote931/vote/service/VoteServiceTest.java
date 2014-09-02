package com.r.web.vote931.vote.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.vote931.vote.bean.VoteOption;
import com.r.web.vote931.vote.model.AbsVoteItem;
import com.r.web.vote931.vote.model.CompletionVoteItem;
import com.r.web.vote931.vote.model.MultipleOptionVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.Vote;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring/*.xml" })
public class VoteServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceTest.class);

    @Resource(name = "vote.service.voteitem")
    private VoteItemService voteItemService;

    @Resource(name = "vote.service.vote")
    private VoteService voteService;

    @Test
    public void initDatas() {
        // 删除 所有的问卷项
        // int deleteAll = voteService.deleteAll();
        // logger.debug("删除" + deleteAll + "个问卷项");

        for (int i = 0; i < 30; i++) {
            // 新增
            YesOrNoVoteItem yesno = createYesOrNoVoteItem("测试问题" + i + " : 菇凉真名风小筝?", false, "是的", "不是的", "筝团 - 雨");
            SingleOptionVoteItem single = createSingleOptionVoteItem("测试问题" + i + " : 菇凉所属工会id是多少?", 2, "2086", "931", "2080", "99", "筝团 - 雨");
            MultipleOptionVoteItem multiple = createMultipleOptionVoteItem("测试问题" + i + " : 以下哪些歌曲是菇凉原创?", "2,3,4", "卜卦", "梦魇", "花非花", "西江月", "筝团 - 雨");
            CompletionVoteItem completione = createCompletionVoteItem("测试问题" + i + " : 菇凉在{}年获得YY年度最佳女主播,在2013年获得第二届《{}》冠军。", "2012", "公主驾到", null, null, "筝团 - 雨");
            voteItemService.save(yesno);
            voteItemService.save(single);
            voteItemService.save(multiple);
            voteItemService.save(completione);

            // 根据id查询
            AbsVoteItem voteIten = voteItemService.findById(single.getId());
            logger.info(voteIten.getQuestion());
            voteIten = voteItemService.findById(multiple.getId());
            logger.info(voteIten.getQuestion());

            // 根据编号查询
            voteIten = voteItemService.findByNo(yesno.getNo());
            logger.info(voteIten.getQuestion());
            voteIten = voteItemService.findByNo(completione.getNo());
            logger.info(voteIten.getQuestion());
        }

        // 生成问卷
        Vote vote = voteService.createVote(new VoteOption("测试-签名", "测试-标题", "测试-副标题", 25));

        logger.info("新生成问卷ID : " + vote.getId());
        logger.info(vote.getTitle());
        logger.info(vote.getSubTitle());
        logger.info(vote.getSignature());
        int i = 1;
        for (AbsVoteItem absVoteItem : vote) {
            logger.info(i++ + " : " + absVoteItem.getNo() + " : " + absVoteItem.getQuestion());
        }
    }

    private CompletionVoteItem createCompletionVoteItem(String question, String answer1, String answer2, String answer3, String answer4, String provider) {
        CompletionVoteItem completion = new CompletionVoteItem();
        completion.setQuestion(question);
        completion.setAnswer1(answer1);
        completion.setAnswer2(answer2);
        completion.setAnswer3(answer3);
        completion.setAnswer4(answer4);
        completion.setProvider(provider);
        completion.setNo(-1);
        completion.setCreateDate(new Date());
        completion.setIsEnable(Boolean.TRUE);
        completion.setRemark("多项选择测试问题");
        completion.checkVoteItemContext();
        return completion;
    }

    private MultipleOptionVoteItem createMultipleOptionVoteItem(String question, String answer, String answer1, String answer2, String answer3, String answer4, String provider) {
        MultipleOptionVoteItem multiple = new MultipleOptionVoteItem();
        multiple.setQuestion(question);
        multiple.setAnswer(answer);
        multiple.setAnswer1(answer1);
        multiple.setAnswer2(answer2);
        multiple.setAnswer3(answer3);
        multiple.setAnswer4(answer4);
        multiple.setProvider(provider);
        multiple.setNo(-1);
        multiple.setCreateDate(new Date());
        multiple.setIsEnable(Boolean.TRUE);
        multiple.setRemark("多项选择测试问题");
        multiple.checkVoteItemContext();
        return multiple;
    }

    /**
     * 创建是非问卷项
     * 
     * @param question
     *            问题
     * @param answer
     *            答案
     * @param answerYes
     *            yes的备选答案
     * @param answerNo
     *            no的备选答案
     * @param provider
     *            发布者
     * @return
     */
    private YesOrNoVoteItem createYesOrNoVoteItem(String question, boolean answer, String answerYes, String answerNo, String provider) {
        YesOrNoVoteItem yesno = new YesOrNoVoteItem();
        yesno.setQuestion(question);
        yesno.setAnswer(Boolean.valueOf(answer));
        yesno.setAnswerYes(answerYes);
        yesno.setAnswerNo(answerNo);
        yesno.setProvider(provider);
        yesno.setNo(-1);
        yesno.setCreateDate(new Date());
        yesno.setIsEnable(Boolean.TRUE);
        yesno.setRemark("是非题测试问题");
        yesno.checkVoteItemContext();
        return yesno;
    }

    /**
     * 创建单选问卷项
     * 
     * @param question
     *            问题
     * @param answer
     *            答案,备选答案的索引(从1开始)
     * @param answer1
     *            备选答案1
     * @param answer2
     *            备选答案2
     * @param answer3
     *            备选答案3
     * @param answer4
     *            备选答案4
     * @param provider
     *            发布者
     * @return 单选问卷项
     */
    private SingleOptionVoteItem createSingleOptionVoteItem(String question, int answer, String answer1, String answer2, String answer3, String answer4, String provider) {
        SingleOptionVoteItem single = new SingleOptionVoteItem();
        single.setQuestion(question);
        single.setAnswer(Integer.valueOf(answer));
        single.setAnswer1(answer1);
        single.setAnswer2(answer2);
        single.setAnswer3(answer3);
        single.setAnswer4(answer4);
        single.setProvider(provider);
        single.setNo(-1);
        single.setCreateDate(new Date());
        single.setIsEnable(Boolean.TRUE);
        single.setRemark("单项选择测试问题");
        single.checkVoteItemContext();
        return single;
    }
}
