/**
 * 
 */
package com.r.web.autogame.engine.battle;

/**
 * 技能释放目标
 * 
 * @author rain
 *
 */
public enum SkillTarget {
    All("全体", Integer.MAX_VALUE), //
    Self("自身", 1), //
    TeamUnit("自方个体", 1), //
    TeamAll("自方全体", Integer.MAX_VALUE), //
    EnemyUnit("敌方个体", 1), //
    EnemyAll("敌方全体", Integer.MAX_VALUE), //
    ;

    /** 技能释放目标中文名称 */
    private String targetName;
    /** 技能释放目标个数 */
    private int targetNumber;

    /**
     * 技能释放目标
     * 
     * @param targetName
     *            目标中文名称
     */
    SkillTarget(String targetName, int targetNumber) {
        this.targetName = targetName;
        this.targetNumber = targetNumber;
    }

    /** 获取目标中文名称 */
    public String getTargetName() {
        return targetName;
    }

    /** 获取目标个数 */
    public int getTargetNumber() {
        return targetNumber;
    }

}
