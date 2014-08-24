package com.r.app.sample.vote.context;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.r.app.sample.vote.VoteItemXmlLoader;
import com.r.app.sample.vote.impl.CompletionVoteItem;
import com.r.app.sample.vote.impl.MultipleOptionVoteItem;
import com.r.app.sample.vote.impl.SingleOptionVoteItem;
import com.r.app.sample.vote.impl.YesOrNoVoteItem;
import com.r.core.util.RandomUtil;
import com.r.core.util.XStreamUtil;

public class VoteContextTest {

	@Test
	public void test() throws IOException {

		YesOrNoVoteItem yesno = new YesOrNoVoteItem();
		yesno.setId(RandomUtil.uuid());
		yesno.setQuestion("菇凉工会id是2086吗?");
		yesno.setAnswerYes("是的");
		yesno.setAnswerNo("不是的");
		yesno.setAnswer(false);
		yesno.setProvider("９３１✦﹍雨『闪耀筝团』-『守护柒烟』");

		YesOrNoVoteItem yesno2 = new YesOrNoVoteItem();
		yesno2.setId(RandomUtil.uuid());
		yesno2.setQuestion("菇凉工会id是931吗?");
		yesno2.setAnswerYes("是的");
		yesno2.setAnswerNo("不是的");
		yesno2.setAnswer(true);
		yesno.setProvider("９３１✦﹍雨『闪耀筝团』-『守护柒烟』");

		SingleOptionVoteItem single1 = new SingleOptionVoteItem();
		single1.setId(RandomUtil.uuid());
		single1.setQuestion("菇凉一共获得女神季周冠军几次?");
		single1.setAnswer1("0次");
		single1.setAnswer2("1次");
		single1.setAnswer3("2次");
		single1.setAnswer4("3次");
		single1.setAnswer(3);
		single1.setProvider("９３１✦﹍雨『闪耀筝团』-『守护柒烟』");

		MultipleOptionVoteItem multiple1 = new MultipleOptionVoteItem();
		multiple1.setId(RandomUtil.uuid());
		multiple1.setQuestion("以下哪些歌曲是菇凉的原唱?");
		multiple1.setAnswer1("西江月");
		multiple1.setAnswer2("梦魇");
		multiple1.setAnswer3("梦唐残歌");
		multiple1.setAnswer4("卜卦");
		multiple1.setAnswer(0, 1, 2);
		multiple1.setProvider("９３１✦﹍雨『闪耀筝团』-『守护柒烟』");

		CompletionVoteItem completion1 = new CompletionVoteItem();
		completion1.setId(RandomUtil.uuid());
		completion1.setQuestion("菇凉真名叫____，艺名____，获得过____年YY年度最佳女主播，2013年12月荣获酷狗《____》。");
		completion1.setAnswer1("覃沐曦");
		completion1.setAnswer2("风小筝");
		completion1.setAnswer3("2012");
		completion1.setAnswer4("年度音乐人");
		completion1.setProvider("９３１✦﹍雨『闪耀筝团』-『守护柒烟』");

		VoteItemXmlLoader xmlLoader = new VoteItemXmlLoader();
		xmlLoader.add(yesno);
		xmlLoader.add(yesno2);
		xmlLoader.add(single1);
		xmlLoader.add(multiple1);
		xmlLoader.add(completion1);

		System.out.println(XStreamUtil.toXml(xmlLoader));

		//

		VoteItemXmlLoader fromXML = XStreamUtil.fromXML(VoteItemXmlLoader.class, FileUtils.readFileToString(new File("vote/vote.xml")));
		System.out.println("----------------");
		for (YesOrNoVoteItem yonv : fromXML.getYesnos()) {
			yonv.checkVoteItemContext();
			System.out.println(yonv.getQuestion());
			System.out.println(yonv.checkAnswer(true));
			System.out.println(yonv.getProvider());
		}
		System.out.println("----------------");
		for (SingleOptionVoteItem sov : fromXML.getSingles()) {
			sov.checkVoteItemContext();
			System.out.println(sov.getQuestion());
			System.out.println(sov.checkAnswer(3));
			System.out.println(sov.getProvider());
		}
		System.out.println("----------------");
		for (MultipleOptionVoteItem mov : fromXML.getMultiples()) {
			mov.checkVoteItemContext();
			System.out.println(mov.getQuestion());
			System.out.println(mov.checkAnswer(0, 1, 2));
			System.out.println(mov.checkAnswer(0, 2));
			System.out.println(mov.getProvider());
		}
		System.out.println("----------------");
		for (CompletionVoteItem cv : fromXML.getCompletions()) {
			cv.checkVoteItemContext();
			System.out.println(cv.getQuestion());
			System.out.println(cv.checkAnswer("覃沐曦", "风小筝", "2012", "年度音乐人"));
			System.out.println(cv.checkAnswer("覃沐曦", "风小筝", "2012", "年度音乐人1"));
			System.out.println(cv.getProvider());
		}
		System.out.println("----------------");
	}

}
