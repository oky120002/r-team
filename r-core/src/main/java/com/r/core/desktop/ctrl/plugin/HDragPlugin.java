package com.r.core.desktop.ctrl.plugin;

import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.r.core.desktop.eventlistener.EventListenerImpl;
import com.r.core.desktop.eventlistener.move.MoveEvent;
import com.r.core.desktop.eventlistener.move.MoveListener;

/**
 * 拖动窗口
 * 
 * @author oky
 * 
 */
public class HDragPlugin implements MouseListener, MouseMotionListener {

	/** 自定义事件 */
	private EventListenerImpl eventListener = new EventListenerImpl();

	/** 窗口是被正在被拖动.initMouseDrag()方法使用 */
	private boolean isDrag = false;

	/** 窗口能否被拖动 */
	private boolean isCanDrag = true;

	/** 窗口被拖动时的X坐标和Y坐标 */
	private int mousePressedY, mousePressedX;

	private Window window;

	/** <默认构造函数> */
	public HDragPlugin() {
		super();
	}

	public HDragPlugin(Window window) {
		this.window = window;
	}

	/**
	 * @return 返回 isCanDrag
	 */
	public boolean isCanDrag() {
		return isCanDrag;
	}

	/**
	 * @param 对isCanDrag进行赋值
	 */
	public void setCanDrag(boolean isCanDrag) {
		this.isCanDrag = isCanDrag;
	}

	// 鼠标按键在组件上单击（按下并释放）时调用。
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	// 鼠标按键在组件上按下时调用。
	@Override
	public void mousePressed(MouseEvent e) {
		if (isCanDrag) {
			isDrag = true;
			mousePressedX = e.getX();
			mousePressedY = e.getY();
		}
	}

	// 鼠标按钮在组件上释放时调用。
	@Override
	public void mouseReleased(MouseEvent e) {
		if (isCanDrag) {
			isDrag = false;
			eventListener.fireMoveListener(new MoveEvent(window));
		}
	}

	// 鼠标进入到组件上时调用。
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	// 鼠标离开组件时调用。
	@Override
	public void mouseExited(MouseEvent e) {

	}

	// 鼠标按键在组件上按下并拖动时调用
	@Override
	public void mouseDragged(MouseEvent e) {
		if (isCanDrag && isDrag) {
			int x = window.getLocation().x + e.getX() - mousePressedX; // x坐标
			int y = window.getLocation().y + e.getY() - mousePressedY; // y坐标
			window.setLocation(x, y);
		}
	}

	// 鼠标光标移动到组件上但无按键按下时调用。
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/** 增加移动事件 */
	public void addMoveListener(MoveListener moveListener) {
		this.eventListener.addMoveListener(moveListener);
	}
}