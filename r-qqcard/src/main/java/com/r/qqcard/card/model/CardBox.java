/**
 * 
 */
package com.r.qqcard.card.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.r.core.util.StrUtil;

/**
 * @author rain
 *
 */
@Entity
@Table(name = "qqcard_cardbox")
public class CardBox {

    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    @Column
    private Integer slot; // slot,卡片在卡箱中的位置
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card; // 卡片
    @Column
    private Integer status; // 状态(0:正常,4:空)
    @Column
    private Integer type;// 卡片类型(0:基础卡片,1:合成的卡片)
    @Column
    private Integer cardBoxType; // 箱子类型(0:换卡箱,1:储藏箱)
    @Column
    private Integer gold; // 金币

    public CardBox() {
        super();
    }

    /** 获取主键 */
    public String getId() {
        return id;
    }

    /** 设置主键 */
    public void setId(String id) {
        this.id = id;
    }

    /** 获取slot,卡片在卡箱中的位置 */
    public Integer getSlot() {
        return slot;
    }

    /** 设置slot,卡片在卡箱中的位置 */
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    /** 获取卡片 */
    public Card getCard() {
        return card;
    }

    /** 设置卡片 */
    public void setCard(Card card) {
        this.card = card;
    }

    /** 获取状态(0:正常,4:空) */
    public Integer getStatus() {
        return status;
    }

    /** 设置状态(0:正常,4:空) */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 获取卡片类型(0:基础卡片,1:合成的卡片) */
    public Integer getType() {
        return type;
    }

    /** 设置卡片类型(0:基础卡片,1:合成的卡片) */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取箱子类型(0:换卡箱,1:保险箱) */
    public Integer getCardBoxType() {
        return cardBoxType;
    }

    /** 设置箱子类型(0:换卡箱,1:保险箱) */
    public void setCardBoxType(Integer cardBoxType) {
        this.cardBoxType = cardBoxType;
    }

    /** 获取卡片金币 */
    public Integer getGold() {
        return gold;
    }

    /** 设置卡片金币 */
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return StrUtil.formart("{}({}￥,{},{}★)", this.card.getName(), this.card.getPrice(), this.card.getTheme().getName(), this.card.getTheme().getDifficulty());
    }

}
