package com.r.web.vote931.vote.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.web.vote931.vote.model.AbsVoteItem;
import com.r.web.vote931.vote.model.SingleOptionVoteItem;
import com.r.web.vote931.vote.model.YesOrNoVoteItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-spring/*.xml" })
public class VoteServiceTest {

	@Resource(name = "mysqlSequence")
	private DataFieldMaxValueIncrementer incrementer;

	@Resource(name = "vote.service.vote")
	private VoteService voteService;

	@Test
	public void test() {
		// 删除
		int deleteAll = voteService.deleteAll();
		System.out.println("删除" + deleteAll + "个问卷项");

		// 新增
		SingleOptionVoteItem single = createSingleOptionVoteItem("菇凉所属工会id是多少?", 2, "2086", "931", "2080", "99", "筝团 - 雨");
		YesOrNoVoteItem yesno = createYesOrNoVoteItem("菇凉真名风小筝?", false, "是的", "不是的", "筝团 - 雨");
		voteService.create(single);
		voteService.create(yesno);

		// 根据id查询
		AbsVoteItem voteIten = voteService.findById(single.getId());
		System.out.println(voteIten.getQuestion());

		// 根据编号查询
		voteIten = voteService.findByNo(yesno.getNo());
		System.out.println(voteIten.getQuestion());
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
		single.checkVoteItemContext();
		return single;
	}
}
