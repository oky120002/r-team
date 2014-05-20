/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.eventlistener.change;

import com.r.core.desktop.eventlistener.EventObjectImpl;

/**
 * <改变事件实体>
 * 
 * @author  rain
 * @version  [版本号, 2013-3-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ChangeEvent<T> extends EventObjectImpl {
    
    private static final long serialVersionUID = 4555561008262430869L;
    
    /**改变后的目标*/
    protected transient T target;
    
    private ChangeEvent(Object source) {
        super(source);
    }
    
    public ChangeEvent(T source, T target) {
        super(new Object());
        this.source = source;
        this.target = target;
    }
    
    @SuppressWarnings("unchecked")
    public T getSource() {
        return (T) source;
    }
    
    public T getTarget() {
        return target;
    }
    
    public String toString() {
        return getClass().getName() + "[source=" + source + ", target="
                + target + "]";
    }

}
