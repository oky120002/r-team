/**
 * 
 */
package com.r.web.autogame.engine.battle;

/**
 * 技能
 * 
 * @author rain
 *
 */
public interface Skill {
    /** 获取技能名称 */
    String getName();

    /** 获取技能描述 */
    String getDescribe();

    /** 技能释放目标 */
    SkillTarget getTarget();

    /** 技能效果(值️) */
    int getValueByEffect();

    /** 技能效果(百分比) */
    int getValueByPercentage();

}
