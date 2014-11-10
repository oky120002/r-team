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
    /** 技能唯一识别码 */
    String getId();

    /** 获取技能名称 */
    String getName();

    /** 获取技能描述 */
    String getDescribe();

    /** 技能释放目标 */
    SkillTarget getTarget();

    /** 技能类型 */
    SkillType getType();

    /** 获取技能效果(百分数) */
    int getValueByPercent();

    /** 获取技能效果(数值) */
    int getValueByNumber();
}
