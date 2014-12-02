package com.r.qqcard.context;

import org.springframework.beans.factory.InitializingBean;

/**
 * QQ卡片辅助程序容器
 * 
 * @author rain
 *
 */
public class QQCardContext extends QQCardContextConfigurator implements InitializingBean {
    /** 对自身的引用 */
    private static QQCardContext context = null;

    /** 返回容器的唯一引用 */
    public static QQCardContext getContext() {
        return context;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
    }
}
