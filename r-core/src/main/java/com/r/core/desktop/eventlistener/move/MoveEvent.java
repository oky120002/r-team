/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener.move;

import java.awt.Window;

import com.r.core.desktop.eventlistener.EventObjectImpl;

/**
 * <移动事件实体>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MoveEvent extends EventObjectImpl {
    
    private static final long serialVersionUID = 4555561008262430869L;
    
    public MoveEvent(Window source) {
        super(source);
    }
    
    public Window getSource() {
        return (Window) source;
    }
    
    public String toString() {
        return getClass().getName() + "[source=" + source + "]";
    }
}
