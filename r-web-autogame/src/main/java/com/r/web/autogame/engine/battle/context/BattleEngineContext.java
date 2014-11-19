/**
 * 
 */
package com.r.web.autogame.engine.battle.context;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 战斗引擎容器<br/>
 * 主要负责战斗计算
 * 
 * @author rain
 *
 */
public class BattleEngineContext extends BattleEngineContextConfigurator {
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(BattleEngineContext.class);

    /** 战斗引擎 */
    private static BattleEngineContext battleEngineContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        logger.debug("战斗引擎初始化......");
    }

    /** 返回战斗引擎唯一实体 */
    public static BattleEngineContext getContext() {
        return BattleEngineContext.battleEngineContext;
    }

}
