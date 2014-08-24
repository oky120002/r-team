/**
 * 
 */
package com.r.app.sample.vote.view.voteitem;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.exception.VoteItemNullException;
import com.r.app.sample.vote.exception.VoteItemTypeErrorException;
import com.r.app.sample.vote.impl.MultipleOptionVoteItem;
import com.r.app.sample.vote.view.VoteItemPanel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;

/**
 * @author Administrator
 *
 */
public class VoteMultiplePanel extends HBasePanel implements VoteItemPanel {
	private static final long serialVersionUID = -4860594570474253513L;

	private MultipleOptionVoteItem multiple; // 多选问卷项
	private JLabel questionLabel = new JLabel(); // 问题
	private JLabel noLabel = new JLabel(); // 编号
	private JCheckBox answer1 = new JCheckBox(); // 答案1描述
	private JCheckBox answer2 = new JCheckBox(); // 答案2描述
	private JCheckBox answer3 = new JCheckBox(); // 答案3描述
	private JCheckBox answer4 = new JCheckBox(); // 答案4描述

	public VoteMultiplePanel() {
		super();
		initComponents();
	}

	@Override
	public void fillVoteItem(VoteItem voteItem) {
		// 清空状态
		if (voteItem instanceof MultipleOptionVoteItem) {
			multiple = (MultipleOptionVoteItem) voteItem;
			multiple.checkVoteItemContext();
		} else {
			throw new VoteItemTypeErrorException("问卷项类型错误,此问卷项是\"多选\"类型的问卷项,不是\"" + voteItem.getType().name() + "\"类型的问卷项");
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
		this.questionLabel.setText(multiple.getQuestion());
		this.noLabel.setText(multiple.getNo());
		this.answer1.setText(multiple.getAnswer1());
		this.answer2.setText(multiple.getAnswer2());
		this.answer3.setText(multiple.getAnswer3());
		this.answer4.setText(multiple.getAnswer4());

	}

	@Override
	public boolean checkAnswer() {
		if (multiple == null) {
			throw new VoteItemNullException("问卷项为空,请先向此问卷项面板填充\"多选\"类型的问卷项");
		}
		Object[] value = new Integer[] { -1, -1, -1, -1 };
		if (answer1.isSelected()) {
			value[0] = 1;
		}
		if (answer2.isSelected()) {
			value[1] = 2;
		}
		if (answer3.isSelected()) {
			value[2] = 3;
		}
		if (answer4.isSelected()) {
			value[3] = 4;
		}

		return multiple.checkAnswer(value);
	}

	private void initComponents() {
		HBaseBox questionBox = HBaseBox.createHorizontalLeft(questionLabel);
		HBaseBox noBox = HBaseBox.createHorizontalLeft(new JLabel("问题编号 : "), noLabel);
		HBaseBox multipleBox = HBaseBox.createHorizontalLeft(answer1, answer2, answer3, answer4);
		HBaseBox center = HBaseBox.createVerticalBaseBox(questionBox, noBox, multipleBox);
		add(center, BorderLayout.CENTER);
	}

}
