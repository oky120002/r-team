/**
 * 
 */
package com.r.qqcard.basedata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rain
 *
 */
@Entity
@Table(name = "qqcard_basedata")
public class BaseData {
    @Id
    private String key;
    @Column
    private String value;
    @Column
    private String remark;

    public BaseData() {
        super();
    }

    /**
     * @param key
     * @param value
     * @param remark
     */
    public BaseData(String key, String value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    /**
     * @param key
     * @param value
     * @param remark
     */
    public BaseData(String key, boolean value, String remark) {
        this.key = key;
        this.value = Boolean.toString(value);
        this.remark = remark;
    }

    /**
     * @param key
     * @param value
     * @param remark
     */
    public BaseData(String key, int value, String remark) {
        this.key = key;
        this.value = Integer.toString(value);
        this.remark = remark;
    }

    /**
     * @param key
     * @param value
     * @param remark
     */
    public BaseData(String key, long value, String remark) {
        this.key = key;
        this.value = Long.toString(value);
        this.remark = remark;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return
     */
    public boolean getValueByBoolean() {
        return Boolean.valueOf(this.value).booleanValue();
    }

    /**
     * @return
     */
    public int getValueByInteger() {
        return Integer.valueOf(this.value).intValue();
    }

}
