/**
 * 
 */
package com.r.qqcard.card.model;

import java.io.Serializable;

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
public class Compose implements Serializable {
    private static final long serialVersionUID = -219713206850628833L;
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
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
    private Long time; // 消耗时间
    @Column
    private Integer state; // 状态

    public Compose() {
        super();
    }

    /**
     * @param theme
     *            所在主题
     * @param cardTar
     *            合成的目标卡片
     * @param card1
     *            素材1
     * @param card2
     *            素材2
     * @param card3
     *            素材3
     * @param time
     *            消耗时间
     * @param state
     *            状态
     */
    public Compose(Theme theme, Card cardTar, Card card1, Card card2, Card card3, Long time, Integer state) {
        super();
        this.theme = theme;
        this.cardTar = cardTar;
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.time = time;
        this.state = state;
    }

    /** 获取主键 */
    public String getId() {
        return id;
    }

    /** 设置主键 */
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
    public Long getTime() {
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
    public void setTime(Long time) {
        this.time = time;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Compose other = (Compose) obj;
        if (theme == null) {
            if (other.theme != null)
                return false;
        } else if (!theme.equals(other.theme))
            return false;
        return true;
    }

}
