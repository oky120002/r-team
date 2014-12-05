package com.r.qqcard.card.qqhome;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 卡片箱的卡片
 * 
 * @author oky
 * 
 */
@XStreamAlias("card")
public class QQHomeCardStore implements Comparable<QQHomeCardStore> {

    @XStreamAsAttribute
    private int id; // 卡片ID
    @XStreamAsAttribute
    private int type; // 卡片类型
    @XStreamAsAttribute
    private int st; // ?
    @XStreamAsAttribute
    private int slot; // 卡片所在箱子中的位置
    @XStreamAsAttribute
    private int status; // 状态

    public QQHomeCardStore() {

    }

    public QQHomeCardStore(int id, int type, int st, int slot, int status) {
        super();
        this.id = id;
        this.type = type;
        this.st = st;
        this.slot = slot;
        this.status = status;
    }

    /** 将卡箱卡片转换成换卡箱卡片 */
    public QQHomeCardChange convertToQQHomeCardChange(int slotDestId) {
        return new QQHomeCardChange(this.id, this.type, this.st, slotDestId, this.status, 0);
    }

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
     * @return the st
     */
    public int getSt() {
        return st;
    }

    /**
     * @return the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
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
        QQHomeCardStore other = (QQHomeCardStore) obj;
        if (id != other.id)
            return false;
        if (slot != other.slot)
            return false;
        return true;
    }

    @Override
    public int compareTo(QQHomeCardStore o) {
        return slot == o.getSlot() ? 0 : slot > o.getSlot() ? 1 : -1;
    }
}
