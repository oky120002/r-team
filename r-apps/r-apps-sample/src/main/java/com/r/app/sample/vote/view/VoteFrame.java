/**
 * 
 */
package com.r.app.sample.vote.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.r.app.sample.vote.VoteItem;
import com.r.app.sample.vote.context.VoteContext;
import com.r.app.sample.vote.view.voteitem.VoteCompletionPanel;
import com.r.app.sample.vote.view.voteitem.VoteMultiplePanel;
import com.r.app.sample.vote.view.voteitem.VoteSinglePanel;
import com.r.app.sample.vote.view.voteitem.VoteYesOrNoPanel;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseFrame;

/**
 * 问卷对话框
 * 
 * @author Administrator
 *
 */
public class VoteFrame extends HBaseFrame implements ActionListener {
	private static final long serialVersionUID = 2484432900197055059L;

	private VoteContext context = VoteContext.init(); // 问卷容器
	private VoteItemPanel curVoteItemPanel; // 当前问卷项
	private VoteYesOrNoPanel yesnoPanel = new VoteYesOrNoPanel(); // 是否面板
	private VoteSinglePanel singlePanel = new VoteSinglePanel(); // 单选面板
	private VoteMultiplePanel multiplePanel = new VoteMultiplePanel(); // 多选面板
	private VoteCompletionPanel completionPanel = new VoteCompletionPanel(); // 填空面板
	private JButton nextBtn = new JButton("下一题");

	public VoteFrame(String title) {
		super(title);
		initStyle();
		initComponents();
		fillVoteItem(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case "nextVoteItem": // 下一题
			nextVoteItem();
			break;
		}
	}

	/** 下一题 */
	private void nextVoteItem() {
		// 记录答题状况
		if (curVoteItemPanel != null) {
			boolean checkAnswer = curVoteItemPanel.checkAnswer();
			System.out.println(checkAnswer);
		}

		// 进入下一题
		fillVoteItem(context.randomNextVoteItem());
	}

	/**
	 * 填充问卷项
	 * 
	 * @param voteItem
	 */
	private void fillVoteItem(VoteItem voteItem) {
		yesnoPanel.setVisible(false);
		singlePanel.setVisible(false);
		multiplePanel.setVisible(false);
		completionPanel.setVisible(false);
		if (voteItem != null) {
			switch (voteItem.getType()) {
			case 是非:
				yesnoPanel.fillVoteItem(voteItem);
				yesnoPanel.setVisible(true);
				curVoteItemPanel = yesnoPanel;
				break;
			case 单选:
				singlePanel.fillVoteItem(voteItem);
				singlePanel.setVisible(true);
				curVoteItemPanel = singlePanel;
				break;
			case 多选:
				multiplePanel.fillVoteItem(voteItem);
				multiplePanel.setVisible(true);
				curVoteItemPanel = multiplePanel;
				break;
			case 填空:
				completionPanel.fillVoteItem(voteItem);
				completionPanel.setVisible(true);
				curVoteItemPanel = completionPanel;
				break;
			}
		} else {
			curVoteItemPanel = null;
		}
	}

	private void initStyle() {
		setSize(new Dimension(600, 400));// 设置窗口大小
		setResizable(false);
		setLocationRelativeTo(null); // 移动到屏幕中部(上下左右)
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private void initComponents() {
		nextBtn.addActionListener(this);
		nextBtn.setActionCommand("nextVoteItem");
		JLabel top = new JLabel("菇凉问答题", JLabel.CENTER);
		Font f = top.getFont();
		top.setForeground(Color.RED);
		top.setFont(new Font(f.getName(), Font.BOLD, f.getSize() * 2));

		add(top, BorderLayout.NORTH);

		HBaseBox yesnoBox = HBaseBox.createHorizontalLeft(yesnoPanel);
		HBaseBox singleBox = HBaseBox.createHorizontalLeft(singlePanel);
		HBaseBox multipleBox = HBaseBox.createHorizontalLeft(multiplePanel);
		HBaseBox completionBox = HBaseBox.createHorizontalLeft(completionPanel);
		HBaseBox center = HBaseBox.createVerticalBaseBox(yesnoBox, singleBox, multipleBox, completionBox);
		add(center, BorderLayout.CENTER);

		HBaseBox buttomRight = HBaseBox.createHorizontalRight(nextBtn);
		HBaseBox buttom = HBaseBox.createVerticalBaseBox(buttomRight, new JLabel("..."));
		add(buttom, BorderLayout.SOUTH);
	}
}