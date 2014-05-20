/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener.change;

import java.util.EventListener;

/**
 * <改变事件>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ChangeListener<T> extends EventListener {
    /**改变后*/
    public void valueChanged(ChangeEvent<T> changeEvent);
}
