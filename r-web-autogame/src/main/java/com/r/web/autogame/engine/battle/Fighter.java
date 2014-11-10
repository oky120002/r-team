/**
 * 
 */
package com.r.web.autogame.engine.battle;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 战斗者基础属性<br/>
 * 力量:决定物理攻击力,格挡成功后效果<br/>
 * 敏捷:决定穿刺攻击力,闪避几率,格挡几率<br/>
 * 智力:决定魔法攻击力,魔法回复速度,魔法抗性(减少魔法伤害),<br/>
 * 精神:<br/>
 * 耐力:<br/>
 * 
 * @author rain
 *
 */
public interface Fighter {

    /** 获取昵称 */
    String getNickName();

    /** 获取力量(Strength) */
    int getStr();

    /** 获取耐力(Endurance) */
    int getEnd();

    /** 获取智力(Intelligence) */
    int getInt();

    /** 获取敏捷(Speed) */
    int getSpe();

    /** 获取装备 */
    Map<EquipPos, Equip> getEquips();

    /** 获取技能 */
    Collection<Skill> getSkills();

    /** 获取行动策略 */
    List<Strategy> getStrategies();
}
