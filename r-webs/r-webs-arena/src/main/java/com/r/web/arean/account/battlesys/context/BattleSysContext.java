/**
 * 
 */
package com.r.web.arean.account.battlesys.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 战斗系统核心容器
 * 
 * @author Administrator
 * 
 */
public class BattleSysContext extends BattleSysConfigurator implements InitializingBean {
    private static BattleSysContext context = null; // 对自身的引用
    private static Logger logger = LoggerFactory.getLogger(BattleSysContext.class); // 日志

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        context = this;
    }

    /**
     * 获取消息中心唯一实体
     * 
     * @return
     */
    public static BattleSysContext getContext() {
        return context;
    }

    public void test() {
        logger.debug("test");
    }
}
