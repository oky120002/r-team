/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 战斗引擎配置容器<br/>
 * 主要负责战斗引擎各项参数得配置和管理
 * 
 * @author rain
 *
 */
public class BattleEngineContextConfigurator implements InitializingBean {
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(BattleEngineContextConfigurator.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("战斗引擎参数初始化......");

    }

}
