/**
 * 
 */
package com.r.app.sample.vote.view.voteitem;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.exception.VoteItemNullException;
import com.r.app.sample.vote.exception.VoteItemTypeErrorException;
import com.r.app.sample.vote.impl.SingleOptionVoteItem;
import com.r.app.sample.vote.view.VoteItemPanel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;

/**
 * @author Administrator
 *
 */
public class VoteSinglePanel extends HBasePanel implements VoteItemPanel {
	private static final long serialVersionUID = -2105002244976147062L;

	private SingleOptionVoteItem single; // 单选问卷项
	private JLabel questionLabel = new JLabel(); // 问题
	private JLabel noLabel = new JLabel(); // 编号
	private JRadioButton answer1 = new JRadioButton(); // 答案1描述
	private JRadioButton answer2 = new JRadioButton(); // 答案2描述
	private JRadioButton answer3 = new JRadioButton(); // 答案3描述
	private JRadioButton answer4 = new JRadioButton(); // 答案4描述

	public VoteSinglePanel() {
		super();
		initComponents();
	}

	@Override
	public void fillVoteItem(VoteItem voteItem) {
		// 清空状态
		if (voteItem instanceof SingleOptionVoteItem) {
			single = (SingleOptionVoteItem) voteItem;
			single.checkVoteItemContext();
		} else {
			throw new VoteItemTypeErrorException("问卷项类型错误,此问卷项是\"单选\"类型的问卷项,不是\"" + voteItem.getType().name() + "\"类型的问卷项");
		}

		// 清空原始数据
		this.questionLabel.setText(null);
		this.noLabel.setText(null);
		this.answer1.setText(null);
		this.answer2.setText(null);
		this.answer3.setText(null);
		this.answer4.setText(null);
		this.answer1.setSelected(false);
		this.answer2.setSelected(false);
		this.answer3.setSelected(false);
		this.answer4.setSelected(false);

		// 设置现在数据
		this.questionLabel.setText(single.getQuestion());
		this.noLabel.setText(single.getNo());
		this.answer1.setText(single.getAnswer1());
		this.answer2.setText(single.getAnswer2());
		this.answer3.setText(single.getAnswer3());
		this.answer4.setText(single.getAnswer4());

	}

	@Override
	public boolean checkAnswer() {
		if (single == null) {
			throw new VoteItemNullException("问卷项为空,请先向此问卷项面板填充\"单选\"类型的问卷项");
		}
		if (answer1.isSelected()) {
			return single.checkAnswer(1);
		}
		if (answer2.isSelected()) {
			return single.checkAnswer(2);
		}
		if (answer3.isSelected()) {
			return single.checkAnswer(3);
		}
		if (answer4.isSelected()) {
			return single.checkAnswer(4);
		}
		return false;
	}

	private void initComponents() {
		ButtonGroup group = new ButtonGroup();
		group.add(answer1);
		group.add(answer2);
		group.add(answer3);
		group.add(answer4);

		HBaseBox questionBox = HBaseBox.createHorizontalLeft(questionLabel);
		HBaseBox noBox = HBaseBox.createHorizontalLeft(new JLabel("问题编号 : "), noLabel);
		HBaseBox yesnoBox = HBaseBox.createHorizontalLeft(answer1, answer2, answer3, answer4);
		HBaseBox center = HBaseBox.createVerticalBaseBox(questionBox, noBox, yesnoBox);
		add(center, BorderLayout.CENTER);
	}

}
