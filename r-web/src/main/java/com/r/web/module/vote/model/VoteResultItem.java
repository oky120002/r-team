package com.r.web.module.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.r.web.module.vote.model.base.VoteBaseItemImpl;

/**
 * 答案条目
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "voteresultitem")
public class VoteResultItem {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 关联问卷答案 */
    @ManyToOne
    @JoinColumn(name = "voteResult_id")
    private VoteResult voteResult;
    /** 基础问题项 */
    @ManyToOne
    @JoinColumn(name = "voteItem_id")
    private VoteBaseItemImpl voteItem;
    /** 是否回答正确 */
    @Column
    private Boolean isRight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VoteBaseItemImpl getVoteItem() {
        return voteItem;
    }

    public void setVoteItem(VoteBaseItemImpl voteItem) {
        this.voteItem = voteItem;
    }

    public Boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(Boolean isRight) {
        this.isRight = isRight;
    }

}
