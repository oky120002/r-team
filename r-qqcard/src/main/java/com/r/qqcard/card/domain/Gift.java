/**
 * 
 */
package com.r.qqcard.card.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QQ秀
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "qqcard_gift")
public class Gift {
    @Id
    private Integer id; // id
    @Column
    private Integer type; // 类型
    @Column
    private String name; // 名称
    @Column
    private String szTypeID; // ?

    /** 获取id */
    public Integer getId() {
        return id;
    }

    /** 设置id */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取类型 */
    public Integer getType() {
        return type;
    }

    /** 设置类型 */
    public void setType(Integer type) {
        this.type = type;
    }

    /** 获取名称 */
    public String getName() {
        return name;
    }

    /** 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /***/
    public String getSzTypeID() {
        return szTypeID;
    }

    /***/
    public void setSzTypeID(String szTypeID) {
        this.szTypeID = szTypeID;
    }

}
