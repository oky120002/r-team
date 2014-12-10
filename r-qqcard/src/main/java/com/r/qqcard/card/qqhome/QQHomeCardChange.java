package com.r.qqcard.card.qqhome;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 换卡箱的卡片
 * 
 * @author oky
 * 
 */
@XStreamAlias("card")
public class QQHomeCardChange implements Comparable<QQHomeCardChange> {

    @XStreamAsAttribute
    private int id; // 卡片ID
    @XStreamAsAttribute
    private int type; // 卡片类型(0:基础卡片,1:合成的卡片)
    @XStreamAsAttribute
    private int st; // ?
    @XStreamAsAttribute
    private int slot; // 卡片所在箱子中的位置
    @XStreamAsAttribute
    private int status; // 状态[0:正常|4:空]
    @XStreamAsAttribute
    private int unlock; // 是否加锁

    public QQHomeCardChange() {
        super();
    }

    public QQHomeCardChange(int id, int type, int st, int slot, int status, int unlock) {
        super();
        this.id = id;
        this.type = type;
        this.st = st;
        this.slot = slot;
        this.status = status;
        this.unlock = unlock;
    }

    /** 将换卡箱卡片转换成卡箱卡片 */
    public QQHomeCardStore convertToQQHomeCardStore(int slotDestid) {
        return new QQHomeCardStore(this.id, this.type, 0, slotDestid, 0);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the st
     */
    public int getSt() {
        return st;
    }

    /**
     * @param st
     *            the st to set
     */
    public void setSt(int st) {
        this.st = st;
    }

    /** 获得卡片所在箱子中的位置 */
    public int getSlot() {
        return slot;
    }

    /**
     * @param slot
     *            the slot to set
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the unlock
     */
    public int getUnlock() {
        return unlock;
    }

    /**
     * @param unlock
     *            the unlock to set
     */
    public void setUnlock(int unlock) {
        this.unlock = unlock;
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
        QQHomeCardChange other = (QQHomeCardChange) obj;
        if (id != other.id)
            return false;
        if (slot != other.slot)
            return false;
        return true;
    }

    @Override
    public int compareTo(QQHomeCardChange o) {
        return slot == o.getSlot() ? 0 : slot > o.getSlot() ? 1 : -1;
    }
}
