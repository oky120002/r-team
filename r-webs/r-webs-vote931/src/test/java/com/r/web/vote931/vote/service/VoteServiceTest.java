package com.r.web.vote931.vote.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.vote931.vote.model.AbsVoteItem;
import com.r.web.vote931.vote.model.CompletionVoteItem;
import com.r.web.vote931.vote.model.MultipleOptionVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring/*.xml" })
public class VoteServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(VoteServiceTest.class);

	@Resource(name = "mysqlSequence")
	private DataFieldMaxValueIncrementer incrementer;

	@Resource(name = "vote.service.vote")
	private VoteService voteService;

	@Test
	public void test() {
		// 删除
		int deleteAll = voteService.deleteAll();
		logger.debug("删除" + deleteAll + "个问卷项");

		// 新增
		YesOrNoVoteItem yesno = createYesOrNoVoteItem("菇凉真名风小筝?", false, "是的", "不是的", "筝团 - 雨");
		SingleOptionVoteItem single = createSingleOptionVoteItem("菇凉所属工会id是多少?", 2, "2086", "931", "2080", "99", "筝团 - 雨");
		MultipleOptionVoteItem multiple = createMultipleOptionVoteItem("以下哪些歌曲是菇凉原创?", "2,3,4", "卜卦", "梦魇", "花非花", "西江月", "筝团 - 雨");
		CompletionVoteItem completione = createCompletionVoteItem("菇凉在{}年获得YY年度最佳女主播,在2013年获得第二届《{}》冠军。", "2012", "公主驾到", null, null, "筝团 - 雨");
		voteService.save(yesno);
		voteService.save(single);
		voteService.save(multiple);
		voteService.save(completione);

		// 根据id查询
		AbsVoteItem voteIten = voteService.findById(single.getId());
		logger.debug(voteIten.getQuestion());
		voteIten = voteService.findById(multiple.getId());
		logger.debug(voteIten.getQuestion());

		// 根据编号查询
		voteIten = voteService.findByNo(yesno.getNo());
		logger.debug(voteIten.getQuestion());
		voteIten = voteService.findByNo(completione.getNo());
		logger.debug(voteIten.getQuestion());
	}

	private CompletionVoteItem createCompletionVoteItem(String question, String answer1, String answer2, String answer3, String answer4, String provider) {
		CompletionVoteItem completion = new CompletionVoteItem();
		completion.setQuestion(question);
		completion.setAnswer1(answer1);
		completion.setAnswer2(answer2);
		completion.setAnswer3(answer3);
		completion.setAnswer4(answer4);
		completion.setProvider(provider);
		completion.setNo(incrementer.nextStringValue());
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
		multiple.setNo(incrementer.nextStringValue());
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
		yesno.setNo(incrementer.nextStringValue());
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
		single.setNo(incrementer.nextStringValue());
		single.setCreateDate(new Date());
		single.setIsEnable(Boolean.TRUE);
		single.setRemark("单项选择测试问题");
		single.checkVoteItemContext();
		return single;
	}
}
