/**
 * 
 */
package com.r.qqcard.card.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    @Column
    private Integer giftid; // QQ秀id
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
     * 构建QQ秀
     * 
     * @param giftid
     *            QQ秀id
     * @param type
     *            类型
     * @param name
     *            名称
     * @param szTypeID
     *            ?
     */
    public Gift(Integer giftid, Integer type, String name, String szTypeID) {
        super();
        this.giftid = giftid;
        this.type = type;
        this.name = name;
        this.szTypeID = szTypeID;
    }

    /** 获取主键 */
    public String getId() {
        return id;
    }

    /** 设置主键 */
    public void setId(String id) {
        this.id = id;
    }

    /** 获取QQ秀主键 */
    public Integer getGiftid() {
        return giftid;
    }

    /** 设置QQ秀主键 */
    public void setGiftid(Integer giftid) {
        this.giftid = giftid;
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
