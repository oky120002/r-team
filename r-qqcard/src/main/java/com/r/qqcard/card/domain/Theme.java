/**
 * 
 */
package com.r.qqcard.card.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * QQ魔法卡片主题
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "qqcard_theme")
public class Theme {
    @Id
    private Integer id; // id
    @Column
    private String name; // 名称
    @Column
    private Integer difficulty; // 合成难度
    @Column
    private Date publishTime; // 发布时间
    @Column
    private Integer pickRate; // 卡牌来源(0:购买和变卡|1:变卡(?)|100:礼物|800:抽卡)?
    @Column
    private Boolean enable; // 是否启用
    @Column
    private Integer prize; // 奖品?
    @Column
    private Integer score; // 得分(可能是完成后得到的经验值?)
    @Column
    private String color; // 主题颜色
    @Column
    private String gift; // 合成成功后的QQ秀(用"|"分割)
    @Column
    private String text; // 描述
    @OneToMany
    @JoinColumn(name = "theme_id")
    private List<Card> cards; // 此主题拥有的所有卡片
    @Column
    private Integer nid; // 当前卡片状态(9:闪卡|...?)
    @Column
    private Integer type; // ?
    @Column
    private Integer version; // 版本
    @Column
    private Date time; // ?
    @Column
    private Date offtime; // 下架时间
    @Column
    private Integer flashSrcTid; // ?

    /** 获得QQ卡片主题id */
    public Integer getId() {
        return id;
    }

    /** 获得QQ卡片主题名称 */
    public String getName() {
        return name;
    }

    /** 获得QQ卡片主题合成难度 */
    public Integer getDifficulty() {
        return difficulty;
    }

    /** 获得QQ卡片主题发布时间 */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * ?<br />
     * 卡牌来源<br />
     * 0:购买和变卡|1:变卡(?)|100:礼物|800:抽卡 <br />
     * 
     * @return 卡牌来源
     */
    public Integer getPickRate() {
        return pickRate;
    }

    /** 获得QQ卡片主题是否启用 */
    public Boolean isEnable() {
        return enable;
    }

    /** 获得QQ卡片主题奖品? */
    public Integer getPrize() {
        return prize;
    }

    /** 获得QQ卡片主题得分(可能是完成后得到的经验值?) */
    public Integer getScore() {
        return score;
    }

    /**
     * 获得QQ卡片主题颜色
     */
    public String getColor() {
        return color;
    }

    /** 获得QQ卡片主题完成后获得的QQ秀(以"|"分割) */
    public String getGift() {
        return gift;
    }

    /** 获得QQ卡片主题描述 */
    public String getText() {
        return text;
    }

    /** 获得QQ卡片主题下的所有QQ卡片 */
    public Collection<Card> getCards() {
        return this.cards;
    }

    /** 设置id */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 设置合成难度 */
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    /** 设置发布时间 */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /** 设置卡牌来源(0:购买和变卡|1:变卡(?)|100:礼物|800:抽卡) */
    public void setPickRate(Integer pickRate) {
        this.pickRate = pickRate;
    }

    /** 设置是否启用 */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /** 设置价格 */
    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    /** 设置经验 */
    public void setScore(Integer score) {
        this.score = score;
    }

    /** 设置颜色 */
    public void setColor(String color) {
        this.color = color;
    }

    /** 合成成功后的QQ秀(用"|"分割) */
    public void setGift(String gift) {
        this.gift = gift;
    }

    /** 设置描述 */
    public void setText(String text) {
        this.text = text;
    }

    /** 设置拥有的卡片 */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    /***/
    public void setNid(Integer nid) {
        this.nid = nid;
    }

    /** 设置卡片类型 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 设置版本 */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /** 设置当前卡片状态(9:闪卡|...?) */
    public void setTime(Date time) {
        this.time = time;
    }

    /** 下架时间 */
    public void setOfftime(Date offtime) {
        this.offtime = offtime;
    }

    /** */
    public void setFlashSrcTid(Integer flashSrcTid) {
        this.flashSrcTid = flashSrcTid;
    }

    /** 获得当前卡片状态(9:闪卡|...?) */
    public Integer getNid() {
        return nid;
    }

    /** 获得QQ卡片主题类型 */
    public Integer getType() {
        return type;
    }

    /** 获得QQ卡片主题版本 */
    public Integer getVersion() {
        return version;
    }

    /** 获得QQ卡片主题? */
    public Date getTime() {
        return time;
    }

    /** 获得下架时间 */
    public Date getOfftime() {
        return offtime;
    }

    /***/
    public Integer getFlashSrcTid() {
        return flashSrcTid;
    }

}
