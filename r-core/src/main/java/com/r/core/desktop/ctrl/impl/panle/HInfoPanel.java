package com.r.core.desktop.ctrl.impl.panle;

import java.awt.BorderLayout;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.apache.commons.lang3.StringUtils;

import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.eventpropagation.Event;
import com.r.core.desktop.eventpropagation.EventSign;

/**
 * 消息信息面板
 * 
 * @author rain
 * @version [版本号, 2012-11-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HInfoPanel extends HBasePanel {
	private static final long serialVersionUID = -961961459614228116L;

	/** 信息框 */
	private JTextArea jtaInfo = new JTextArea();

	/** 是否滚动信息框 */
	private boolean isScroll = true;

	/** <默认构造函数> */
	public HInfoPanel() {
		super();
		initComponent();
	}

	/** <默认构造函数> */
	public HInfoPanel(String eventUid) {
		super();
		if (StringUtils.isNotBlank(eventUid)) {
			setEventUid(eventUid);
		}
		initComponent();
	}

	/** 初始化控件 */
	private void initComponent() {
		// 设置页面控件布局类型
		setLayout(new BorderLayout());

		// 初始化命令文本框
		jtaInfo.setEditable(false); // 只读
		jtaInfo.setLineWrap(true); // 设置换行策略
		add(new JScrollPane(jtaInfo), BorderLayout.CENTER);

	}

	/** 添加焦点监听器 */
	@Override
	public void addFocusListener(FocusListener focusListener) {
		jtaInfo.addFocusListener(focusListener);
	}

	/** 增加鼠标事件 */
	@Override
	public void addMouseListener(MouseListener mouseListener) {
		jtaInfo.addMouseListener(mouseListener);
	}

	/** 信息打印事件 */
	@EventSign("InfoPanel.PrintInfo")
	public void execPrintInfoEvent(Event evetn, Object object) {
		printlnInfo(String.valueOf(object));
	}

	/** 设置信息框是否自动滚动事件 */
	@EventSign("InfoPanel.IsScroll")
	public void execIsScrollEvent(Event evetn, Object object) {
		if (Boolean.TRUE.equals(object)) {
			setScroll(true);
		} else {
			setScroll(false);
		}
	}

	/** 滚动信息框到底部 */
	@EventSign("InfoPanel.ScrollToButtom")
	public void execScrollToButtomEvent(Event evetn, Object object) {
		scrollToButtom();
	}

	/** 打印信息 */
	public void printlnInfo(String message) {
		if (StringUtils.isNotEmpty(message)) {
			jtaInfo.append(message + "\r\n");
			if (isScroll) {
				scrollToButtom();
			}
		}
	}

	/** 是否滚动信息框 */
	public boolean isScroll() {
		return isScroll;
	}

	/** 设置是否滚动信息框 */
	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	/** 滚动信息框到底部 */
	public void scrollToButtom() {
		Document doc = jtaInfo.getDocument();
		jtaInfo.select(doc.getLength(), doc.getLength());
	}
}
