/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener.move;

import java.util.EventListener;

/**
 * <移动事件>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface MoveListener extends EventListener {
    /**移动完成后*/
    public void endMoved(MoveEvent changeEvent);
}
