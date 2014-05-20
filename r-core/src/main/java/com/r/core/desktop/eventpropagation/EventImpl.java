/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-2-16
 * <修改描述:>
 */
package com.r.core.desktop.eventpropagation;

/**
 * 事件实体的默认实现
 * 
 * @author rain
 * @version [1.0, 2013-2-16]
 */
public class EventImpl implements EventShow {

	/** 事件容器 */
	protected EventContainer eventContainer = null;

	/** 默认构造函数 */
	public EventImpl() {
		super();
		eventContainer = new EventContainer(this.getClass());
	}

	@Override
	public void executeEvent(Event event, Object object, EventCallBack callBack) {
		this.eventContainer.execEvent(this, event, object, callBack);
	}

	@Override
	public void addEventShow(EventShow eventShwo) {
		this.eventContainer.addShowEvent(eventShwo);
	}

	@Override
	public void removeEventShow(EventShow eventShow) {
		eventContainer.removeShowEvent(eventShow);
	}

	@Override
	public void setEventUid(String uid) {
		this.eventContainer.setUid(uid);
	}

	@Override
	public String getEventUid() {
		return this.eventContainer.getUid();
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
