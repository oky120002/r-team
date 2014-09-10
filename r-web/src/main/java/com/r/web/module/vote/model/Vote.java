/**
 * 
 */
package com.r.web.module.vote.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class Vote {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷编号 */
    @Column(unique = true)
    protected Integer no;
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
    @OneToMany
    @JoinColumn(name = "vote_id")
    private List<VoteItem> voteItems;
    /** 问卷结果集合 */
    @OneToMany
    @JoinColumn(name = "vote_id")
    private List<VoteResult> voteResults;

    /** 备注,意见 */
    @Column(length = 1024)
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public List<VoteItem> getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(List<VoteItem> voteItems) {
        this.voteItems = voteItems;
    }

    public List<VoteResult> getVoteResults() {
        return voteResults;
    }

    public void setVoteResults(List<VoteResult> voteResults) {
        this.voteResults = voteResults;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
