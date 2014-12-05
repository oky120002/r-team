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
@XStreamAlias("card")
public class QQRefinedCard {
    // <card id="4324" uin="14006297" location="1" slot="29" status="0"
    // time="1391995771" cardname="绱浣欐櫀" cardpri="610" type="1"
    // themename="浜崕鏃фⅵ" themeid="292"></card>

    @XStreamAsAttribute
    private int id;
    @XStreamAsAttribute
    private String uin;
    @XStreamAsAttribute
    private int location;
    @XStreamAsAttribute
    private int slot;
    @XStreamAsAttribute
    private int status;
    @XStreamAsAttribute
    private long time;
    @XStreamAsAttribute
    private String cardname;
    @XStreamAsAttribute
    private int cardpri;
    @XStreamAsAttribute
    private int type;
    @XStreamAsAttribute
    private String themename;
    @XStreamAsAttribute
    private int themeid;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the uin
     */
    public String getUin() {
        return uin;
    }

    /**
     * @return the location
     */
    public int getLocation() {
        return location;
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

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @return the cardname
     */
    public String getCardname() {
        return cardname;
    }

    /**
     * @return the cardpri
     */
    public int getCardpri() {
        return cardpri;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the themename
     */
    public String getThemename() {
        return themename;
    }

    /**
     * @return the themeid
     */
    public int getThemeid() {
        return themeid;
    }

}
