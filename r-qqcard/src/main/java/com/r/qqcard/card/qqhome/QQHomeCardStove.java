package com.r.qqcard.card.qqhome;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 炼卡箱卡片
 * 
 * @author oky
 * 
 */
@XStreamAlias("card")
public class QQHomeCardStove implements Comparable<QQHomeCardStove> {

    private static final String STEAL1 = "steal1"; // 偷卡位置1
    private static final String STEAL2 = "steal2"; // 偷卡位置2

    @XStreamAsAttribute
    private int id; // 卡片ID
    @XStreamAsAttribute
    private int type; // 卡片类型
    @XStreamAsAttribute
    private int slot; // 卡片所在箱子中的位置

    // 炼卡的属性
    @XStreamAsAttribute
    private int stove; // 炼卡位值
    @XStreamAsAttribute
    private long btime; // 开始炼卡时间
    @XStreamAsAttribute
    private long locktime; // 炼卡耗费时间
    @XStreamAsAttribute
    private int prop; // ?
    @XStreamAsAttribute
    private int flag; // 当前炼卡状态(0:等待炼卡|1:连卡中|2:炼卡完成等待取卡)

    // 偷卡的属性(下面用的整型的类,说明此属性只有在偷卡的情况下才不为null)
    @XStreamAsAttribute
    private Integer id2; // ?
    @XStreamAsAttribute
    private Integer opuin; // 第一个炉子的被偷炉的QQ号码 ?
    @XStreamAsAttribute
    private Integer opuin2; // ?
    @XStreamAsAttribute
    private String slottype; // ?

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /** 获取炼卡位置 */
    public int getStove() {
        return stove;
    }

    /** 获取开始炼卡时间 */
    public long getBtime() {
        return btime;
    }

    /** 获取炼卡耗费时间 */
    public long getLocktime() {
        return locktime;
    }

    /**
     * @return the prop
     */
    public int getProp() {
        return prop;
    }

    /**
     * 返回当前卡炉中卡片状态<br />
     * 
     * @return 0:等待炼卡|1:炼卡中|2:炼卡完成等待取卡
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @return the id2
     */
    public Integer getId2() {
        return id2;
    }

    /**
     * @return the opuin
     */
    public Integer getOpuin() {
        return opuin;
    }

    /**
     * @return the opuin2
     */
    public Integer getOpuin2() {
        return opuin2;
    }

    /**
     * @return the slottype
     */
    public String getSlottype() {
        return slottype;
    }

    /** 返回此位置是否是偷卡位置 */
    public boolean isSteal() {
        return STEAL1.equals(this.slottype) || STEAL2.equals(this.slottype);
    }

    /**
     * 改变当前炼卡炉状态
     * 
     * @param flag
     */
    public void changeFlag(int flag) {
        this.flag = flag;
        // XXX yaojing 根据flag改变其它的属性
        switch (this.flag) {

        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + slot;
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
        QQHomeCardStove other = (QQHomeCardStove) obj;
        if (id != other.id)
            return false;
        if (slot != other.slot)
            return false;
        return true;
    }

    @Override
    public int compareTo(QQHomeCardStove o) {
        return slot == o.getSlot() ? 0 : slot > o.getSlot() ? 1 : -1;
    }
}
