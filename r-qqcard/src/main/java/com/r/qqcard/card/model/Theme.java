/**
 * 
 */
package com.r.qqcard.card.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * QQ魔法卡片主题
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "qqcard_theme")
public class Theme implements Serializable {
    private static final long serialVersionUID = -7055447944244135258L;
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    @Column
    private Integer themeid; // 主题id
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
    private Integer color; // 主题颜色
    @Column
    private String gift; // 合成成功后的QQ秀(用"|"分割)
    @Column
    private String text; // 描述
    @OneToMany
    @JoinColumn(name = "theme_id")
    private List<Card> cards; // 此主题拥有的所有卡片
    @Column
    private Integer themeType; // 主题类型(9:闪卡|...?)
    @Column
    private Integer version; // 版本
    @Column
    private Date time; // ?
    @Column
    private Date offtime; // 下架时间
    @Column
    private Integer flashSrcTid; // ?

    public Theme() {
        super();
    }

    /**
     * @param themeid
     *            主题id
     * @param name
     *            名称
     * @param difficulty
     *            合成难度
     * @param publishTime
     *            发布时间
     * @param pickRate
     *            卡牌来源(0:购买和变卡,1:变卡(不确定),100:礼物,800:抽卡)
     * @param enable
     *            是否启用
     * @param prize
     *            奖品?
     * @param score
     *            得分,完成后得到的经验值(不确定)
     * @param color
     *            主题颜色
     * @param gift
     *            合成成功后的QQ秀(用"|"分割)
     * @param text
     *            描述
     * @param cards
     *            此主题拥有的所有卡片
     * @param themeType
     *            主题类型(9:闪卡|...?)
     * @param version
     *            版本
     * @param time
     *            ?
     * @param offtime
     *            下架时间
     * @param flashSrcTid
     *            ?
     */
    public Theme(Integer themeid, String name, Integer difficulty, Date publishTime, Integer pickRate, Boolean enable, Integer prize, Integer score, Integer color, String gift, String text, List<Card> cards, Integer themeType, Integer version, Date time, Date offtime, Integer flashSrcTid) {
        super();
        this.themeid = themeid;
        this.name = name;
        this.difficulty = difficulty;
        this.publishTime = publishTime;
        this.pickRate = pickRate;
        this.enable = enable;
        this.prize = prize;
        this.score = score;
        this.color = color;
        this.gift = gift;
        this.text = text;
        this.cards = cards;
        this.themeType = themeType;
        this.version = version;
        this.time = time;
        this.offtime = offtime;
        this.flashSrcTid = flashSrcTid;
    }

    /** 获取主题 */
    public String getId() {
        return id;
    }

    /** 设置主题 */
    public void setId(String id) {
        this.id = id;
    }

    /** 获取主题ID */
    public Integer getThemeid() {
        return themeid;
    }

    /** 设置主题ID */
    public void setThemeid(Integer themeid) {
        this.themeid = themeid;
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
    public Integer getColor() {
        return color;
    }

    /** 获取QQ卡片主题颜色 */
    public static Color getColor(String color) {
        return Color.decode(color);
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
    public void setColor(Integer color) {
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

    /**
     * @return the themeType
     */
    public Integer getThemeType() {
        return themeType;
    }

    /**
     * @param themeType
     *            the themeType to set
     */
    public void setThemeType(Integer themeType) {
        this.themeType = themeType;
    }

    /**
     * @return the enable
     */
    public Boolean getEnable() {
        return enable;
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
