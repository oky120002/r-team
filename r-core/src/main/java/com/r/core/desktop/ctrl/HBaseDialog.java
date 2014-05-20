/**
 * 修  改   人:  Administrator
 * 修改时间:  2013-1-3
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;

import com.r.core.desktop.ctrl.plugin.HDragPlugin;
import com.r.core.desktop.eventlistener.move.MoveListener;
import com.r.core.desktop.eventpropagation.Event;
import com.r.core.desktop.eventpropagation.EventCallBack;
import com.r.core.desktop.eventpropagation.EventContainer;
import com.r.core.desktop.eventpropagation.EventShow;
import com.r.core.desktop.eventpropagation.EventSign;

/**
 * 对话框
 * 
 * @author oky
 * 
 */
public class HBaseDialog extends JDialog implements EventShow {
	private static final long serialVersionUID = 4792630537336329581L;

	/** 事件容器 */
	private EventContainer eventContainer = null;

	/** 拖动插件 */
	protected HDragPlugin dragPlugin = new HDragPlugin(this);

	public HBaseDialog(String title) {
		super((Frame) null, title);
		eventContainer = new EventContainer(this.getClass());
		baseFramMouseInit();
	}

	public HBaseDialog(Frame owner, String title, boolean isModel) {
		super(owner, title, isModel);
		eventContainer = new EventContainer(this.getClass());
		baseFramMouseInit();
	}

	public HBaseDialog(Dialog dialog, String title, boolean isModel) {
		super(dialog, title, isModel);
		eventContainer = new EventContainer(this.getClass());
		baseFramMouseInit();
	}

	/** 初始化方法 */
	private void baseFramMouseInit() {
		// 鼠标点击事件
		addMouseListener(dragPlugin);
		// 鼠标拖动事件
		addMouseMotionListener(dragPlugin);
	}

	/** 显示/隐藏组件 */
	public void toggle() {
		if (this.isVisible()) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
	}

	/**
	 * @return 返回窗口能否被拖动
	 */
	public boolean isCanDrag() {
		return dragPlugin.isCanDrag();
	}

	/**
	 * @param isCanDrag
	 *            设置窗口能否被拖动
	 */
	public void setCanDrag(boolean isCanDrag) {
		dragPlugin.setCanDrag(isCanDrag);
	}

	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		if (comp instanceof EventShow) {
			addEventShow((EventShow) comp);
		}
		super.addImpl(comp, constraints, index);
	}

	@Override
	public void executeEvent(Event event, Object object, EventCallBack callBack) {
		this.eventContainer.execEvent(this, event, object, callBack);
	}

	@Override
	public void addEventShow(EventShow shwoEvent) {
		eventContainer.addShowEvent(shwoEvent);
	}

	@Override
	public void removeEventShow(EventShow eventShow) {
		eventContainer.removeShowEvent(eventShow);
	}

	/** 添加移动事件 */
	public void addMoveListener(MoveListener moveListener) {
		this.dragPlugin.addMoveListener(moveListener);
	}

	@Override
	public void setEventUid(String uid) {
		eventContainer.setUid(uid);
	}

	@Override
	public String getEventUid() {
		return eventContainer.getUid();
	}

	@Override
	@EventSign(value = "EventBasic.init")
	public void eventInit(Event evetn, Object object) {
		for (EventShow subShowEvent : eventContainer.getSubEventShows()) {
			subShowEvent.eventInit(evetn, object);
		}
	}

	@Override
	@EventSign(value = "EventBasic.start")
	public void eventStart(Event evetn, Object object) {
		for (EventShow subShowEvent : eventContainer.getSubEventShows()) {
			subShowEvent.eventStart(evetn, object);
		}
	}

	@Override
	@EventSign(value = "EventBasic.exit")
	public void eventExit(Event evetn, Object object) {
		for (EventShow subShowEvent : eventContainer.getSubEventShows()) {
			subShowEvent.eventExit(evetn, object);
		}
	}

	@Override
	@EventSign(value = "EventBasic.continue")
	public void eventContinue(Event evetn, Object object) {
		for (EventShow subShowEvent : eventContainer.getSubEventShows()) {
			subShowEvent.eventContinue(evetn, object);
		}
	}

	@Override
	@EventSign(value = "EventBasic.pause")
	public void eventPause(Event evetn, Object object) {
		for (EventShow subShowEvent : eventContainer.getSubEventShows()) {
			subShowEvent.eventPause(evetn, object);
		}
	}
}
