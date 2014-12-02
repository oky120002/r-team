/**
 * 
 */
package com.r.qqcard.card.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * QQ魔法卡片合成规则
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "qqcard_compose")
public class Compose {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id; // id
    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme; // 所在主题
    @ManyToOne
    @JoinColumn(name = "cardtar_id")
    private Card cardTar; // 合成的目标卡片
    @ManyToOne
    @JoinColumn(name = "card1_id")
    private Card card1; // 合成卡片1
    @ManyToOne
    @JoinColumn(name = "card2_id")
    private Card card2; // 合成卡片2
    @ManyToOne
    @JoinColumn(name = "card3_id")
    private Card card3; // 合成卡片3
    @Column
    private Integer time; // 消耗时间
    @Column
    private Integer state; // 状态

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 获取合成的主题 */
    public Theme getTheme() {
        return theme;
    }

    /** 获取合成的目标卡片 */
    public Card getCardTar() {
        return cardTar;
    }

    /** 获取合成的素材卡片1 */
    public Card getCard1() {
        return card1;
    }

    /** 获取合成的素材卡片2 */
    public Card getCard2() {
        return card2;
    }

    /** 获取合成的素材卡片3 */
    public Card getCard3() {
        return card3;
    }

    /** 获取合成素材需要消耗的时间 */
    public Integer getTime() {
        return time;
    }

    /** 返回状态 */
    public Integer getState() {
        return state;
    }

    /** 设置状态 */
    public void setState(Integer state) {
        this.state = state;
    }

    /** 设置要合成的卡片所在的主题 */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /** 设置要合成的卡片 */
    public void setCardTar(Card cardTar) {
        this.cardTar = cardTar;
    }

    /** 设置素材1 */
    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    /** 设置素材2 */
    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    /** 设置素材3 */
    public void setCard3(Card card3) {
        this.card3 = card3;
    }

    /** 设置要消耗的时间 */
    public void setTime(Integer time) {
        this.time = time;
    }

}
