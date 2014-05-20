/**
 * 描          述:  <展示模块>
 * 修  改   人:  rain
 * 修改时间:  2012-11-12
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Component;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang.ArrayUtils;

import com.r.core.desktop.eventpropagation.Event;
import com.r.core.desktop.eventpropagation.EventCallBack;
import com.r.core.desktop.eventpropagation.EventContainer;
import com.r.core.desktop.eventpropagation.EventShow;
import com.r.core.desktop.eventpropagation.EventSign;

/**
 * <顶层面板>
 * 
 * @author rain
 * @version [版本号, 2012-11-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HBasePanel extends JPanel implements EventShow {
	private static final long serialVersionUID = 3763512195783698252L;

	/** 事件容器 */
	private EventContainer eventContainer = null;

	public HBasePanel() {
		super();
		eventContainer = new EventContainer(this.getClass());
	}

	public HBasePanel(LayoutManager layout) {
		super(layout);
		eventContainer = new EventContainer(this.getClass());
	}

	public HBasePanel(JComponent... components) {
		this();
		if (ArrayUtils.isNotEmpty(components)) {
			for (JComponent jComponent : components) {
				add(jComponent);
			}
		}
	}

	/** 显示/隐藏组件 */
	public void toggle() {
		if (this.isVisible()) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
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
	public void addEventShow(EventShow eventShow) {
		eventContainer.addShowEvent(eventShow);
	}

	@Override
	public void removeEventShow(EventShow eventShow) {
		eventContainer.removeShowEvent(eventShow);
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
