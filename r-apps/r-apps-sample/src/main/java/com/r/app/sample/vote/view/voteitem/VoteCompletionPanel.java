/**
 * 
 */
package com.r.app.sample.vote.view.voteitem;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.exception.VoteItemTypeErrorException;
import com.r.app.sample.vote.impl.CompletionVoteItem;
import com.r.app.sample.vote.view.VoteItemPanel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;

/**
 * @author Administrator
 *
 */
public class VoteCompletionPanel extends HBasePanel implements VoteItemPanel {
	private static final long serialVersionUID = -3976828180061316003L;

	private CompletionVoteItem completion; // 填空问卷项
	private JTextArea questionTextArea = new JTextArea(); // 问题
	private JLabel noLabel = new JLabel(); // 编号
	private JTextField answer1 = new JTextField(); // 答案1描述
	private JTextField answer2 = new JTextField(); // 答案2描述
	private JTextField answer3 = new JTextField(); // 答案3描述
	private JTextField answer4 = new JTextField(); // 答案4描述

	private HBaseBox answerBox1 = HBaseBox.createHorizontalLeft(new JLabel("问题1 : "), answer1);
	private HBaseBox answerBox2 = HBaseBox.createHorizontalLeft(new JLabel("问题2 : "), answer2);
	private HBaseBox answerBox3 = HBaseBox.createHorizontalLeft(new JLabel("问题3 : "), answer3);
	private HBaseBox answerBox4 = HBaseBox.createHorizontalLeft(new JLabel("问题4 : "), answer4);

	public VoteCompletionPanel() {
		super();
		initComponents();
	}

	@Override
	public void fillVoteItem(VoteItem voteItem) {
		// 清空状态
		if (voteItem instanceof CompletionVoteItem) {
			completion = (CompletionVoteItem) voteItem;
			completion.checkVoteItemContext();
		} else {
			throw new VoteItemTypeErrorException("问卷项类型错误,此问卷项是\"是非\"类型的问卷项,不是\"" + voteItem.getType().name() + "\"类型的问卷项");
		}

		questionTextArea.setText(null);
		noLabel.setText(null);
		answer1.setText(null);
		answer2.setText(null);
		answer3.setText(null);
		answer4.setText(null);
		answerBox1.setVisible(false);
		answerBox2.setVisible(false);
		answerBox3.setVisible(false);
		answerBox4.setVisible(false);

		questionTextArea.setText(completion.getQuestion());
		noLabel.setText(completion.getNo());
		int questionSize = completion.getQuestionSize();
		switch (questionSize) {
		case 4:
			answerBox4.setVisible(true);
		case 3:
			answerBox3.setVisible(true);
		case 2:
			answerBox2.setVisible(true);
		case 1:
			answerBox1.setVisible(true);
		}
	}

	@Override
	public boolean checkAnswer() {
		Object[] answers = new String[completion.getQuestionSize()];
		switch (completion.getQuestionSize()) {
		case 4:
			answers[3] = answer4.getText();
		case 3:
			answers[2] = answer3.getText();
		case 2:
			answers[1] = answer2.getText();
		case 1:
			answers[0] = answer1.getText();
		}

		return completion.checkAnswer(answers);
	}

	private void initComponents() {
		questionTextArea.setRows(3);
		questionTextArea.setColumns(50);
		questionTextArea.setEditable(false);
		questionTextArea.setLineWrap(true);

		HBaseBox questionBox = HBaseBox.createHorizontalLeft(questionTextArea);
		HBaseBox noBox = HBaseBox.createHorizontalLeft(new JLabel("问题编号 : "), noLabel);
		HBaseBox center = HBaseBox.createVerticalBaseBox(questionBox, noBox, answerBox1, answerBox2, answerBox3, answerBox4);
		add(center, BorderLayout.CENTER);
	}

}
