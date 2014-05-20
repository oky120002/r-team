/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-11-30
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Component;

import javax.swing.JLabel;

import com.r.core.desktop.eventpropagation.Event;
import com.r.core.desktop.eventpropagation.EventCallBack;
import com.r.core.desktop.eventpropagation.EventContainer;
import com.r.core.desktop.eventpropagation.EventShow;
import com.r.core.desktop.eventpropagation.EventSign;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author rain
 * @version [版本号, 2012-11-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HBaseLabel extends JLabel implements EventShow {
	private static final long serialVersionUID = -7040793683942715882L;

	/** 事件容器 */
	private EventContainer eventContainer = null;

	/** 默认构造函数 */
	public HBaseLabel() {
		super();
		eventContainer = new EventContainer(this.getClass());
	}

	/** 默认构造函数 */
	public HBaseLabel(String text) {
		super(text);
		eventContainer = new EventContainer(this.getClass());
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
	public void addEventShow(EventShow eventShwo) {
		eventContainer.addShowEvent(eventShwo);
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
