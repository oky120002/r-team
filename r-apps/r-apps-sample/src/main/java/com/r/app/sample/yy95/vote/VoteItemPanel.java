/**
 * 
 */
package com.r.app.sample.yy95.vote;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;

import com.r.app.sample.vote.VoteItem;
import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBasePanel;

/**
 * 问卷项面板
 * 
 * @author Administrator
 *
 */
public class VoteItemPanel extends HBasePanel {
	private static final long serialVersionUID = -9212047050594860023L;

	/** 问卷项 */
	private VoteItem voteItem;

	public VoteItemPanel(VoteItem voteItem) {
		this.voteItem = voteItem;

		initStyle();
		initComponents();
	}

	private void initStyle() {
		
	}

	private void initComponents() {
		JLabel questionLabel = new JLabel(voteItem.getQuestion());
		questionLabel.setForeground(Color.RED);

		HBaseBox top = HBaseBox.createHorizontalBaseBox(questionLabel, HBaseBox.createHorizontalGlue());
		add(top, BorderLayout.NORTH);
	}
}
