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

    /** 技能类型 */
    SkillType getType();

    /** 技能释放目标 */
    SkillTarget getTarget();

    /** 获取技能CD(小于等于0就是此技能没有cd立即可以使用) */
    int getCD();

    /** 技能伤害效果 */
    SkillEffect getEffect();

    /** 获取技能效果(根据effect确实是百分数还是数值) */
    int getValue();
}
