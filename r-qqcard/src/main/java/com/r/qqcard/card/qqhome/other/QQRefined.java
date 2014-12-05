/**
 * 
 */
package com.r.qqcard.card.qqhome.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.r.qqcard.card.qqhome.QQHomeCardStore;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 * 取卡后的xml实体
 * 
 * @author oky
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQRefined {
	@XStreamAsAttribute
	private int capable;
	@XStreamAsAttribute
	private int code;
	@XStreamAsAttribute
	private int money;
	@XStreamAsAttribute
	private int exp;
	@XStreamAsAttribute
	private int stovenum;
	@XStreamAsAttribute
	private int rentstovenum;
	@XStreamAsAttribute
	private int lv;
	@XStreamAsAttribute
	private int lv_diff;
	@XStreamImplicit(itemFieldName = "card")
	private List<QQRefinedCard> cards = new ArrayList<QQRefinedCard>(); // 炼卡箱取卡完成后的卡片

	/**
	 * @return the capable
	 */
	public int getCapable() {
		return capable;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @return the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @return the stovenum
	 */
	public int getStovenum() {
		return stovenum;
	}

	/**
	 * @return the rentstovenum
	 */
	public int getRentstovenum() {
		return rentstovenum;
	}

	/**
	 * @return the lv
	 */
	public int getLv() {
		return lv;
	}

	/**
	 * @return the lv_diff
	 */
	public int getLv_diff() {
		return lv_diff;
	}

	/**
	 * @return the cards
	 */
	public Collection<QQRefinedCard> getCards() {
		return cards;
	}

	/**
	 * 转换成卡箱中的卡牌,且返回
	 * 
	 * @return
	 */
	public Collection<QQHomeCardStore> getStoreCards() {
		Collection<QQHomeCardStore> storeCards = new ArrayList<QQHomeCardStore>();
		for (QQRefinedCard card : this.cards) {
			storeCards.add(new QQHomeCardStore(card.getId(), card.getType(), 0, card.getSlot(), card.getStatus()));
		}
		return storeCards;
	}

}
