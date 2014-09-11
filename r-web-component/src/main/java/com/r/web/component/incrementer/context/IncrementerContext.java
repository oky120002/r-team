package com.r.web.component.incrementer.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.web.component.incrementer.RainMaxValueIncrementer;

public class IncrementerContext extends IncrementerContextConfigurator implements InitializingBean {

    /** 对自身的引用 */
    private static IncrementerContext context = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
    }

    public static IncrementerContext getContext() {
        return context;
    }

    /** 获得默认的自增长器 */
    public RainMaxValueIncrementer getIncrementer() {
        return incrementers.get(DEFAULT_INCREMENTER_NAME);
    }

    /** 获得指定的自增长器 */
    public RainMaxValueIncrementer getIncrementer(String incrementerName) {
        return incrementers.get(incrementerName);
    }

    /** 默认自增长器编号归零,重新计算 */
    public void returnZero() {
        getIncrementer().clear();
    }

    /** 指定的自增长器编号归零,重新计算 */
    public void returnZero(String incrementerName) {
        getIncrementer(incrementerName).clear();
    }
}
