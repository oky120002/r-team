/**
 * 
 */
package com.r.qqcard.card.domain.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringEscapeUtils;

import com.r.core.exceptions.ScriptResolveException;
import com.r.core.exceptions.SwitchPathException;
import com.r.qqcard.card.domain.Card;
import com.r.qqcard.card.domain.Compose;
import com.r.qqcard.card.domain.Gift;
import com.r.qqcard.card.domain.Theme;

/**
 * 此JS数据模式,没有进行正确方法校验<br />
 * 等待以后改用antlr语法解析器来解析,和格式校验 JS数据模型<br />
 * 
 * @author Administrator
 * 
 */
public class CardInfo {
    private Map<Integer, Compose> composes = new HashMap<Integer, Compose>(); // 合成规则(key:themeId)
    private Map<Integer, Card> cards = new HashMap<Integer, Card>(); // qq卡片(key:cardId)
    private Map<Integer, Theme> themes = new HashMap<Integer, Theme>(); // qq卡片主题(key:themeId)
    private List<Gift> gifts = new ArrayList<Gift>(); // qq秀

    public CardInfo(String js) {
        super();
        resolve(js);
    }

    /** 解析 */
    @SuppressWarnings("unchecked")
    private void resolve(String js) {

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        try {
            engine.eval(js);
        } catch (ScriptException e) {
            throw new ScriptResolveException("卡片信息V3解析错误", e);
        }

        List<Object> composes = (List<Object>) engine.get("theme_compose_list"); // 合成规则
        List<Object> themes = (List<Object>) engine.get("theme_card_list"); // 卡片主题信息
        List<Object> gifts = (List<Object>) engine.get("gift_list"); // QQ秀信息
        List<Object> cards = (List<Object>) engine.get("card_list"); // 卡片信息

        // 开始解析
        resolveTheme(themes);
        resolveCard(cards);
        resolveCompose(composes);
        resolveGift(gifts);
    }

    /** 解析合成规则 */
    @SuppressWarnings("unchecked")
    private void resolveCompose(List<Object> composesList) {
        for (Object object : composesList) {
            List<Object> items = (List<Object>) object;

            Theme theme = toTheme(toInteger(items.get(0)));
            if (theme != null) { // 必须存在此合成主题
                int state = toInteger(items.get(1));
                Card card = toCard(toInteger(items.get(2)));
                Card card1 = toCard(toInteger(items.get(3)));
                Card card2 = toCard(toInteger(items.get(4)));
                Card card3 = toCard(toInteger(items.get(5)));
                long time = toLong(items.get(6));
                this.composes.put(Integer.valueOf(theme.getId()), new Compose(theme, card, card1, card2, card3, time, state));
            }
        }
    }

    /** 解析卡片主题 */
    @SuppressWarnings("unchecked")
    private void resolveTheme(List<Object> themesList) {
        for (Object object : themesList) {
            List<Object> items = (List<Object>) object;

            int themeid = toInteger(items.get(0));
            String name = toString(items.get(1));
            int difficulty = toInteger(items.get(2));
            Date publishTime = toDate(items.get(3));
            int pickRate = toInteger(items.get(4));
            boolean enable = toBoolean(items.get(5));
            int prize = toInteger(items.get(6));
            int score = toInteger(items.get(7));
            String color = String.valueOf(items.get(8));
            String gift = toString(items.get(9));
            String text = toString(items.get(10));
            // List<Card> cards = new ArrayList<Card>();
            // for (Object obj : (List<Object>) items.get(11)) {
            // int cardid = toInteger(obj);
            // cards.add(this.cards.get(Integer.valueOf(cardid)));
            // }
            int themeType = toInteger(items.get(12)); // 这里QQ注释不明确
            int version = toInteger(items.get(13)); // 这里QQ注释不明确
            Date time = toDate(items.get(14)); // 这里QQ注释不明确,但能猜测
            Date offtime = toDate(items.get(15)); // 下架时间
            int flashSrcTid = -1;// toInteger(split[17]); // 这里QQ注释不明确
            Theme qqTheme = new Theme(themeid, name, difficulty, publishTime, pickRate, enable, prize, score, color, gift, text, null, themeType, version, time, offtime, flashSrcTid);
            this.themes.put(Integer.valueOf(qqTheme.getId()), qqTheme);
        }
    }

    /** 解析QQ秀 */
    @SuppressWarnings("unchecked")
    private void resolveGift(List<Object> giftsList) {
        for (Object object : giftsList) {
            List<Object> items = (List<Object>) object;
            int giftid = toInteger(items.get(0));
            int type = toInteger(items.get(1));
            String szTypeID = toString(items.get(2));
            String name = toString(items.get(3));
            this.gifts.add(new Gift(giftid, type, name, szTypeID));
        }
    }

    /** 解析卡片信息 */
    @SuppressWarnings("unchecked")
    private void resolveCard(List<Object> cardsList) {
        for (Object object : cardsList) {
            List<Object> items = (List<Object>) object;

            int cardid = toInteger(items.get(0));
            Theme theme = toTheme(toInteger(items.get(1)));
            String name = toString(items.get(2));
            int price = toInteger(items.get(3));
            int type = toInteger(items.get(4));
            int pickRate = toInteger(items.get(5));
            boolean enable = toBoolean(items.get(6));
            int version = toInteger(items.get(7));
            long time = toLong(items.get(8));
            int itemNo = toInteger(items.get(9));
            int mana = -1; // XXX yaojing 这里直接把魔法值设置为-1,因为不知道这个值有什么用,且不好解析
            Card qqCard = new Card(cardid, theme, name, price, type, pickRate, enable, version, time, itemNo, mana);
            this.cards.put(Integer.valueOf(qqCard.getId()), qqCard);
        }
    }

    /** 获取合成规则 */
    public Collection<Compose> getComposes() {
        return composes.values();
    }

    /** 获取卡牌信息 */
    public Collection<Card> getCards() {
        return this.cards.values();
    }

    /** 获取卡牌主题信息 */
    public Collection<Theme> getThemes() {
        return this.themes.values();
    }

    /** 获取卡片主题炼卡完成后的QQ秀信息 */
    public Collection<Gift> getGifts() {
        return gifts;
    }

    private Card toCard(int cardId) {
        return this.cards.get(Integer.valueOf(cardId));
    }

    private Theme toTheme(int themeId) {
        return this.themes.get(Integer.valueOf(themeId));
    }

    /** 转换类型为Integer */
    private int toInteger(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof String) {
            return Integer.valueOf(String.valueOf(obj)).intValue();
        }

        throw new SwitchPathException("找不到对应的类型来进行数据类型转换");
    }

    /** 转换类型为String */
    private String toString(Object obj) {
        return StringEscapeUtils.unescapeHtml4(String.valueOf(obj));
    }

    /** 转换类型为布尔 */
    private boolean toBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(1).intValue() == 1;
        }
        if (obj instanceof Double) {
            return Double.valueOf(1).intValue() == 1;
        }
        return "1".equals(String.valueOf(obj)) || Boolean.valueOf(String.valueOf(obj)).booleanValue();
    }

    /** 转换类型为Long */
    private long toLong(Object obj) {
        if (obj instanceof Double) {
            return ((Double) obj).longValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        if (obj instanceof Long) {
            return Long.valueOf(String.valueOf(obj)).longValue();
        }
        throw new SwitchPathException("找不到对应的类型来进行数据类型转换");
    }

    /**
     * 转换字符串为时间
     * 
     * @param time
     * @return 如果小于1毫秒,则返回null,说明没有此时间
     */
    private Date toDate(Object time) {
        if (time instanceof Double) {
            Double value = (Double) time;
            if (value.intValue() < 1) {
                return null;
            }
            return new Date(value.longValue() * 1000);
        }
        if (time instanceof Date) {
            return (Date) time;
        }
        if (time instanceof String) {
            Long value = Long.valueOf(String.valueOf(time));
            if (value.intValue() < 1) {
                return null;
            }
            return new Date(value.longValue() * 1000);
        }
        if (time instanceof Integer) {
            Integer value = (Integer) time;
            if (value.intValue() < 1) {
                return null;
            }
            return new Date(value.longValue() * 1000);
        }

        if (time instanceof Long) {
            return new Date(((Long) time).longValue() * 1000);
        }

        throw new SwitchPathException("找不到对应的类型来进行数据类型转换");
    }
}
