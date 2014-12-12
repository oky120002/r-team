/**
 * 
 */
package com.r.qqcard.card.bean;

import com.r.core.util.StrUtil;
import com.r.qqcard.card.model.CardBox;

/**
 * 卡片列表控件数据项
 * 
 * @author rain
 *
 */
public class CardBoxListItem {
    /** 卡片箱子id */
    private String cardBoxid;
    /** 卡片在卡箱中的位置 */
    private int slot;
    /** 卡片名称 */
    private String cardName;
    /** 卡片主题名称 */
    private String themeName;
    /** 箱子类型(0:换卡箱,1:储藏箱) */
    private int cardBoxType;
    /** 星级 */
    private int star;

    public CardBoxListItem(CardBox cardBox) {
        this.cardBoxid = cardBox.getId();
        this.slot = cardBox.getSlot().intValue();
        this.cardName = cardBox.getCard().getName();
        this.themeName = cardBox.getCard().getTheme().getName();
        this.cardBoxType = cardBox.getCardBoxtype().intValue();
        this.star = cardBox.getCard().getTheme().getDifficulty();
    }

    /** 获取卡片箱子id */
    public String getCardBoxid() {
        return cardBoxid;
    }

    /** 获取卡片在卡箱中的位置 */
    public int getSlot() {
        return slot;
    }

    /** 后去卡片名称 */
    public String getCardName() {
        return cardName;
    }

    /** 获取主题名称 */
    public String getThemeName() {
        return themeName;
    }

    /** 获取箱子类型(0:换卡箱,1:储藏箱) */
    public int getCardBoxType() {
        return cardBoxType;
    }

    /** 获取星级 */
    public int getStar() {
        return star;
    }

    /** 设置卡片箱子id */
    public void setCardBoxid(String cardBoxid) {
        this.cardBoxid = cardBoxid;
    }

    /** 设置卡片在卡箱中的位置 */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /** 设置卡片名称 */
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /** 设置片主题名称 */
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    /** 设置箱子类型(0:换卡箱,1:储藏箱) */
    public void setCardBoxType(int cardBoxType) {
        this.cardBoxType = cardBoxType;
    }

    /** 设置星级 */
    public void setStar(int star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return StrUtil.formart("{}[{} - {}★]", this.cardName, this.themeName, this.star);
    }

}
