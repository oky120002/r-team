package com.r.app.zhuangxiubang.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    @Column
    private String bianhao;
    /** 是否可以接标 */
    @Column
    private Boolean isBm;
    @Column
    private String loupan;
    @Column
    private String huxing;
    @Column
    private String yaoqiu;
    @Column
    private Date createDate;
    @Column
    private Boolean isReaded;
    
    /**应标状态*/
    @Column
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    /**是否可以接标*/
    public Boolean getIsBm() {
        return isBm;
    }

    /**设置是否可以接标*/
    public void setIsBm(Boolean isBm) {
        this.isBm = isBm;
    }

    public String getLoupan() {
        return loupan;
    }

    public void setLoupan(String loupan) {
        this.loupan = loupan;
    }

    public String getHuxing() {
        return huxing;
    }

    public void setHuxing(String huxing) {
        this.huxing = huxing;
    }

    public String getYaoqiu() {
        return yaoqiu;
    }

    public void setYaoqiu(String yaoqiu) {
        this.yaoqiu = yaoqiu;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Boolean isReaded) {
        this.isReaded = isReaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
