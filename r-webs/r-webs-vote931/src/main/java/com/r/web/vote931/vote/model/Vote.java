/**
 * 
 */
package com.r.web.vote931.vote.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "vote")
public class Vote implements Iterable<AbsVoteItem> {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷标题 */
    @Column
    private String title;
    /** 副标题 */
    @Column
    private String subTitle;
    /** 答题人/签名 */
    @Column
    private String signature;
    /** 问卷生成时间 */
    @Column
    private Date createDate;
    /** 启用/禁用 */
    @Column
    private Boolean isEnable;
    /** 问卷项集合 */
    @ManyToMany(targetEntity = AbsVoteItem.class, fetch = FetchType.EAGER)
    private List<AbsVoteItem> voteItems;
    /** 备注,意见 */
    @Column(length = 1024)
    private String remark;

    /** 获得问卷唯一标识符 */
    public String getId() {
        return id;
    }

    /** 设置问卷唯一标识符 */
    public void setId(String id) {
        this.id = id;
    }

    /** 获得问卷标题 */
    public String getTitle() {
        return title;
    }

    /** 设置标题 */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 获取副标题 */
    public String getSubTitle() {
        return subTitle;
    }

    /** 设置副标题 */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /** 获得问卷签名(也就是谁来回答这个问题) */
    public String getSignature() {
        return signature;
    }

    /** 设置签名 */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /** 获得问卷生成时间 */
    public Date getCreateDate() {
        return createDate;
    }

    /** 设置问卷生成时间 */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /** 获得此问卷拥有的问卷项 */
    public List<AbsVoteItem> getVoteItems() {
        return voteItems;
    }

    /** 获得此问卷是否启用 */
    public Boolean getIsEnable() {
        return isEnable;
    }

    /** 获得此问卷是否启用 */
    public boolean isEnable() {
        return isEnable == null ? false : isEnable.booleanValue();
    }

    /** 设置次问卷是否启用 */
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /** 设置问卷项 */
    public void setVoteItems(List<AbsVoteItem> voteItems) {
        this.voteItems = voteItems;
    }

    /** 获取备注 */
    public String getRemark() {
        return remark;
    }

    /** 设置备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Iterator<AbsVoteItem> iterator() {
        return voteItems == null ? null : voteItems.iterator();
    }
}
