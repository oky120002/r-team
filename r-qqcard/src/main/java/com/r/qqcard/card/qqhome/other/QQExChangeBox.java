/**
 * 
 */
package com.r.qqcard.card.qqhome.other;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author oky
 * 
 */
@XStreamAlias(value = "QQHOME")
public class QQExChangeBox {
    @XStreamAsAttribute
    private int code;
    @XStreamAsAttribute
    private int destid;

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the destid
     */
    public int getDestid() {
        return destid;
    }

    /**
     * @param destid
     *            the destid to set
     */
    public void setDestid(int destid) {
        this.destid = destid;
    }

}
