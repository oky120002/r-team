package com.r.app.sample.vote.view.voteitem;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.exception.VoteItemNullException;
import com.r.app.sample.vote.exception.VoteItemTypeErrorException;
import com.r.app.sample.vote.impl.YesOrNoVoteItem;
import com.r.app.sample.vote.view.VoteItemPanel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;

public class VoteYesOrNoPanel extends HBasePanel implements VoteItemPanel {
	private static final long serialVersionUID = 2824585785772974630L;

	private YesOrNoVoteItem yesno; // 是非问卷项
	private JLabel questionLabel = new JLabel(); // 问题
	private JLabel noLabel = new JLabel(); // 编号
	private JRadioButton yesRadio = new JRadioButton(); // 正确答案描述
	private JRadioButton noRadio = new JRadioButton(); // 错误答案描述

	public VoteYesOrNoPanel() {
		super();
		initComponents();
	}

	@Override
	public void fillVoteItem(VoteItem voteItem) {
		// 清空状态
		if (voteItem instanceof YesOrNoVoteItem) {
			yesno = (YesOrNoVoteItem) voteItem;
			yesno.checkVoteItemContext();
		} else {
			throw new VoteItemTypeErrorException("问卷项类型错误,此问卷项是\"是非\"类型的问卷项,不是\"" + voteItem.getType().name() + "\"类型的问卷项");
		}

		// 清空原始数据
		this.questionLabel.setText(null);
		this.noLabel.setText(null);
		this.yesRadio.setText(null);
		this.noRadio.setText(null);
		this.yesRadio.setSelected(false);
		this.noRadio.setSelected(false);

		// 设置现在数据
		this.questionLabel.setText(yesno.getQuestion());
		this.noLabel.setText(yesno.getNo());
		this.yesRadio.setText(yesno.getAnswerYes());
		this.noRadio.setText(yesno.getAnswerNo());
	}

	@Override
	public boolean checkAnswer() {
		if (yesno == null) {
			throw new VoteItemNullException("问卷项为空,请先向此问卷项面板填充\"是非\"类型的问卷项");
		}
		if (yesRadio.isSelected()) {
			return yesno.checkAnswer(true);
		}
		if (noRadio.isSelected()) {
			return yesno.checkAnswer(false);
		}
		return false;
	}

	private void initComponents() {
		ButtonGroup group = new ButtonGroup();
		group.add(yesRadio);
		group.add(noRadio);

		HBaseBox questionBox = HBaseBox.createHorizontalLeft(questionLabel);
		HBaseBox noBox = HBaseBox.createHorizontalLeft(new JLabel("问题编号 : "), noLabel);
		HBaseBox answerBox = HBaseBox.createHorizontalLeft(yesRadio, noRadio);
		HBaseBox center = HBaseBox.createVerticalBaseBox(questionBox, noBox, answerBox);
		add(center, BorderLayout.CENTER);
	}
}
