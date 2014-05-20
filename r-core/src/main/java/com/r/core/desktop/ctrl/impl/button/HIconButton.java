/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-12
 * <修改描述:>
 */
package com.r.core.desktop.ctrl.impl.button;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * 图片按钮
 * 
 * @author rain
 * @version [1.0, 2013-3-12]
 * @see javax.swing.JButton
 */
public class HIconButton extends JButton {
	private static final long serialVersionUID = 5495554055955970745L;

	/** 默认构造函数 */
	public HIconButton() {
		super();
	}

	/**
	 * 
	 * 创建一个有提示文字,图标,鼠标经过时图标和鼠标按下时图标的无文字按钮
	 * 
	 * @param toolTipText
	 *            提示文字
	 * @param icon
	 *            按钮图标数组 0:按钮图片 1:鼠标经过时图标 2:鼠标按下时图片
	 * 
	 * @return JImageButton 图标按钮
	 */
	public HIconButton(String toolTipText, Icon[] icon) {
		setToolTipText(toolTipText);
		setMargin(new Insets(0, 0, 0, 0));// 设置按钮边框和标签之间的间隔
		setContentAreaFilled(false);// 设置不绘制按钮的内容区域
		setBorderPainted(false);// 设置不绘制按钮的边框
		if (icon != null) {
			if (icon[0] != null) {
				setIcon(icon[0]); // 设置按钮图片
			}
			if (icon[1] != null) {
				setRolloverIcon(icon[1]); // 设置鼠标经过时图片
			}
			if (icon[2] != null) {
				setPressedIcon(icon[2]);// 设置鼠标按下时图片
			}
		}
	}

	/**
	 * 
	 * 创建一个有提示文字,图标,鼠标经过时图标和鼠标按下时图标的无文字按钮
	 * 
	 * @param toolTipText
	 *            提示文字
	 * @param icon
	 *            按钮图标
	 * @param rolloverIcon
	 *            鼠标经过时图标
	 * @param pressedIcon
	 *            鼠标按下时图标
	 * 
	 * @return JImageButton 图标按钮
	 */
	public HIconButton(String toolTipText, Icon icon, Icon rolloverIcon, Icon pressedIcon) {
		setToolTipText(toolTipText);
		setMargin(new Insets(0, 0, 0, 0));// 设置按钮边框和标签之间的间隔
		setContentAreaFilled(false);// 设置不绘制按钮的内容区域
		setBorderPainted(false);// 设置不绘制按钮的边框
		setIcon(icon); // 设置按钮图片
		setRolloverIcon(rolloverIcon); // 设置鼠标经过时图片
		setPressedIcon(pressedIcon);// 设置鼠标按下时图片
	}
}
