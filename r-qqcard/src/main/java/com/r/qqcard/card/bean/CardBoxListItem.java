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
    /** 卡片id */
    private int cardid;
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
    /** 金币 */
    private int gold;

    public CardBoxListItem(CardBox cardBox) {
        this.cardid = cardBox.getCard().getCardid();
        this.cardBoxid = cardBox.getId();
        this.slot = cardBox.getSlot().intValue();
        this.cardName = cardBox.getCard().getName();
        this.themeName = cardBox.getCard().getTheme().getName();
        this.cardBoxType = cardBox.getCardBoxType().intValue();
        this.star = cardBox.getCard().getTheme().getDifficulty();
        this.gold = cardBox.getCard().getPrice();
    }

    /** 获取卡片id */
    public int getCardid() {
        return cardid;
    }

    /** 设置卡片id */
    public void setCardid(int cardid) {
        this.cardid = cardid;
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

    /** 获取卡片价格 */
    public int getGold() {
        return gold;
    }

    /** 设置卡片价格 */
    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return StrUtil.formart("{}({}￥,{},{}★)", this.cardName, this.gold, this.themeName, this.star);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCardBoxid() == null) ? 0 : getCardBoxid().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CardBoxListItem other = (CardBoxListItem) obj;
        if (getCardBoxid() == null) {
            if (other.getCardBoxid() != null)
                return false;
        } else if (!getCardBoxid().equals(other.getCardBoxid()))
            return false;
        return true;
    }
}
