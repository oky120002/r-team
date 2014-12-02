/**
 * 
 */
package com.r.qqcard.basedata.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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

    public BaseData(String key, String value, String remark) {
        super();
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public BaseData(String key, int value, String remark) {
        this.key = key;
        this.value = Integer.toString(value);
        this.remark = remark;
    }

    public BaseData(String key, boolean value, String remark) {
        this.key = key;
        this.value = Boolean.toString(value);
        this.remark = remark;
    }

    /** 返回Key */
    public String getKey() {
        return key;
    }

    /** 设置key */
    public void setKey(String key) {
        this.key = key;
    }

    /** 设置值 */
    public void setValue(String value) {
        this.value = value;
    }

    /** 返回描述 */
    public String getRemark() {
        return remark;
    }

    /** 设置描述 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 返回值 */
    public String getValue() {
        return value;
    }

    /** 返回值 */
    public boolean getValueByBoolean() {
        return Boolean.valueOf(this.value).booleanValue();
    }

    /** 返回值 */
    public int getValueByInteger() {
        return Integer.valueOf(this.value).intValue();
    }

}
