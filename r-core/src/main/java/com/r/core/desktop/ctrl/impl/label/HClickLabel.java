/**
 * 
 */
package com.r.core.desktop.ctrl.impl.label;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.commons.lang3.StringUtils;

import com.r.core.desktop.ctrl.HBaseLabel;
import com.r.core.util.TaskUtil;

/**
 * 点击的标签<br />
 * label.getFontMetrics(label.getFont()).stringWidth(label.getText()) // 获取内容宽度
 * 
 * @author Administrator
 * 
 */
public class HClickLabel extends HBaseLabel implements MouseListener {
	private static final long serialVersionUID = -7146370829730685268L;

	private MouseAdapter mouseAdapter;
	private Color color; // 字体颜色
	private Color mouseinColor; // 鼠标悬浮时的颜色
	private String text; // 文本

	/** 默认构造函数 */
	public HClickLabel() {
		super();
		initListener();
	}

	/** 默认构造函数 */
	public HClickLabel(String text) {
		this(text, null, null);
	}

	/** 默认构造函数 */
	public HClickLabel(String text, Color color, Color mouseinColor) {
		super();
		this.text = text;
		this.color = color;
		this.mouseinColor = mouseinColor;
		initComponent();
		initListener();
	}

	private void initListener() {
		addMouseListener(this);
	}

	/**
	 * 初始化控件
	 * 
	 * @param runnable
	 * @param color
	 * @param text
	 */
	public void initComponent() {
		if (StringUtils.isNotBlank(this.text)) {
			setText(this.text);
		} else {
			this.text = getText();
		}
		if (this.color != null) {
			setForeground(this.color);
		} else {
			this.color = getForeground();
		}
		if (mouseAdapter != null) {
			this.addMouseListener(this.mouseAdapter);
		}
	}

	/** 获取文本 */
	public String getText() {
		return this.text;
	}

	/** 设置文本 */
	public void setText(String text) {
		if (StringUtils.isBlank(text)) {
			this.text = "";
		} else {
			this.text = text;
		}
		super.setText(this.text);
	}

	/** 获取文本颜色 */
	public Color getColor() {
		return this.color;
	}

	/** 设置文本颜色 */
	public void setColor(Color color) {
		if (color != null) {
			this.color = color;
		}
		setForeground(this.color);
	}

	/** 获得鼠标悬浮时的颜色 */
	public Color getMouseinColor() {
		return mouseinColor;
	}

	/** 设置鼠标悬浮时的颜色 */
	public void setMouseinColor(Color mouseinColor) {
		this.mouseinColor = mouseinColor;
	}

	/** 设置鼠标点击事件 */
	public void setClickAdapter(Runnable runnable, boolean startThread) {
		this.removeMouseListener(this.mouseAdapter);
		this.mouseAdapter = getClickMouseAdapter(runnable, startThread);
		addMouseListener(this.mouseAdapter);
	}

	/** 根据Runnable设置鼠标点击响应Adapter */
	private MouseAdapter getClickMouseAdapter(final Runnable runnable, final boolean startThread) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (HClickLabel.this.isEnabled()) {
					if (startThread) {
						TaskUtil.executeTask(runnable);
						return;
					}
					runnable.run();
				}
				super.mouseClicked(e);
			}
		};
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (enabled) {
			setForeground(this.color);
		} else {
			setForeground(Color.GRAY);
		}
		super.setEnabled(enabled);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if (mouseinColor != null) {
			setForeground(this.mouseinColor);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		setCursor(Cursor.getDefaultCursor());
		if (mouseinColor != null) {
			setForeground(this.color);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
