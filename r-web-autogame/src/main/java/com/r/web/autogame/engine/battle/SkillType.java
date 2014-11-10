/**
 * 
 */
package com.r.web.autogame.engine.battle;

/**
 * 技能的攻击类型<br/>
 * 暂时定为 物理,魔法,穿刺三种.
 * 
 * @author rain
 *
 */
public enum SkillType {
    PhysicalAttack("物理攻击"), //
    MagicAttack("魔法攻击"), //
    PiercingAttack("穿刺攻击"), //
    ;

    /** 技能攻击类型名称 */
    private String name;

    /** 获取技能攻击类型的名称 */
    public String getName() {
        return name;
    }

    SkillType(String name) {
        this.name = name;
    }

}
