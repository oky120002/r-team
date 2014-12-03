/**
 * 
 */
package com.r.qqcard.card.domain;

import java.io.Serializable;

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
public class Gift implements Serializable {
    private static final long serialVersionUID = 8899763199940874195L;
    @Id
    private Integer id; // id
    @Column
    private Integer type; // 类型
    @Column
    private String name; // 名称
    @Column
    private String szTypeID; // ?

    public Gift() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     * @param type
     * @param name
     * @param szTypeID
     */
    public Gift(Integer id, Integer type, String name, String szTypeID) {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.szTypeID = szTypeID;
    }

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
