/**
 * 
 */
package com.r.web.autogame.engine.battle.box;

/**
 * 行动力
 * 
 * @author rain
 *
 */
public interface Acter {
    /** 每回合行动力回复量 */
    float getActByRes();

    /** 获取满足行动的行动力数值 */
    int getAct();
    
    
}
