/**
 * 
 */
package com.r.qqcard.card.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 卡片信息
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "qqcard_card")
public class Card {
    @Id
    private Integer id; // 卡片id
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme; // 主题
    @Column
    private String name; // 名称
    @Column
    private Integer price; // 价格
    @Column
    private Integer type; // 类型(0:基础卡片|1:合成出现的卡片|...未发现)
    @Column
    private Integer pickRate; // 选择范围?
    @Column
    private Boolean enable; // 是否启用
    @Column
    private Integer version; // 版本
    @Column
    private Integer time; // 炼卡时间
    @Column
    private Integer itemNo; // 物品编号?
    @Column
    private Integer mana; // 魔法值

    /** 获得QQ卡片id */
    public Integer getId() {
        return id;
    }

    /** 获取此卡片的主题 */
    public Theme getTheme() {
        return this.theme;
    }

    /** 获得QQ卡片名称 */
    public String getName() {
        return name;
    }

    /** 获得QQ卡片价格 */
    public Integer getPrice() {
        return price;
    }

    /** 获得QQ卡片类型(0:基础卡片|1:合成出现的卡片|...未发现) */
    public Integer getType() {
        return type;
    }

    /** 获得QQ卡片选择范围(不确定) */
    public Integer getPickRate() {
        return pickRate;
    }

    /** 获得QQ卡片是否启用 */
    public Boolean getEnable() {
        return enable;
    }

    /** 获得QQ卡片版本 */
    public Integer getVersion() {
        return version;
    }

    /** 获得QQ卡片炼卡时间 */
    public Integer getTime() {
        return time;
    }

    /** 获得物品编号(不确定) */
    public Integer getItemNo() {
        return itemNo;
    }

    /** 获得QQ卡片魔法值 */
    public Integer getMana() {
        return mana;
    }

    /** 设置id */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 设置主题 */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /** 设置卡片名称 */
    public void setName(String name) {
        this.name = name;
    }

    /** 设置价格 */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /** 设置卡片类型 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 设置范围 */
    public void setPickRate(Integer pickRate) {
        this.pickRate = pickRate;
    }

    /** 设置是否启用 */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /** 设置版本 */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /** 设置时间 */
    public void setTime(Integer time) {
        this.time = time;
    }

    /** 设置物品编号(不确定) */
    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    /** 设置魔法值 */
    public void setMana(Integer mana) {
        this.mana = mana;
    }

}
