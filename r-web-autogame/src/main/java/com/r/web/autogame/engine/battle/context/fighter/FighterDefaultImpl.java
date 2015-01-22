/**
 * 
 */
package com.r.web.autogame.engine.battle.context.fighter;

import java.util.ArrayList;
import java.util.List;

import com.r.web.autogame.engine.battle.Fighter;
import com.r.web.autogame.engine.battle.Skill;

/**
 * 真逗这默认实现
 * 
 * @author rain
 *
 */
public class FighterDefaultImpl implements Fighter {

    private String name;
    private int str;
    private int end;
    private int spe;
    private int inte;
    private int spk;
    private int luc;
    private List<Skill> skills = new ArrayList<Skill>();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getStr() {
        return this.str;
    }
//
//    @Override
//    public int getEnd() {
//        return this.end;
//    }
//
//    @Override
//    public int getSpe() {
//        return this.spe;
//    }
//
//    @Override
//    public int getInt() {
//        return this.inte;
//    }
//
//    @Override
//    public int getSpk() {
//        return this.spk;
//    }
//
//    @Override
//    public int getLuc() {
//        return this.luc;
//    }
//
//    @Override
//    public Collection<Skill> getSkills() {
//        return this.skills;
//    }

    /** 设置智力 */
    public void setInte(int inte) {
        this.inte = inte;
    }

    /** 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 设置力量 */
    public void setStr(int str) {
        this.str = str;
    }

    /** 设置耐力 */
    public void setEnd(int end) {
        this.end = end;
    }

    /** 设置敏捷 */
    public void setSpe(int spe) {
        this.spe = spe;
    }

    /** 设置精神 */
    public void setSpk(int spk) {
        this.spk = spk;
    }

    /** 设置幸运 */
    public void setLuc(int luc) {
        this.luc = luc;
    }

    /** 设置技能 */
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

}
