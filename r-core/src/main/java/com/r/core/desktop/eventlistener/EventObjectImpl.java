/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener;

import java.util.EventObject;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class EventObjectImpl extends EventObject {
    private static final long serialVersionUID = -7367086905092496637L;
    
    public EventObjectImpl(Object source) {
        super(source);
    }
}
