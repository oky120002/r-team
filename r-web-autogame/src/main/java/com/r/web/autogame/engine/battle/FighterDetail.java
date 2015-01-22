/**
 * 
 */
package com.r.web.autogame.engine.battle;

/**
 * 请实现此接口的类,返回的值是在结算所有情况的下的最终值. <br/>
 * 包括但不限于:基础属性,装备属性加成,状态, 战斗者
 * 
 * @author rain
 *
 */
public interface FighterDetail {
    /** 获取战斗者昵称 */
    String getName();

    // /-------------HP---
    /** 获取血量 */
    int getHp();

    /** 获取每回合hp回复量 */
    int getHpByRes();

    // /-------------MP---
    /** 获取技能只 */
    int getMp();

    /** 获取每回合mp回复量 */
    int getMpByRes();

    // /-------------CP---
    /** 获取每回合act回复量(Restore ACT) */
    int getAcTByRes();

    // /-------------攻击力---

    /** 获取最小物理攻击力 */
    int getAttackByPhysicalMin();

    /** 获取最大物理攻击力 */
    int getAttackByPhysicalMax();

    /** 获取最小穿刺攻击力 */
    int getAttackByPiercingMin();

    /** 获取最大穿刺攻击力 */
    int getAttackByPiercingMax();

    /** 获取最小魔法攻击力 */
    int getAttackByMagicMin();

    /** 获取最大魔法攻击力 */
    int getAttackByMagicMax();

    // /-------------防御力---

    /** 获取物理防御力 */
    int getDefenceByPhysical();

    /** 获取穿刺防御力 */
    int getDefenceByPiercing();

    /** 获取魔法防御力 */
    int getDefenceByMagic();
}
