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
 * 问卷结果条目
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "vote_voteresultitem")
public class VoteResultItem {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷结果条目编号 */
    @Column(unique = true)
    private Integer no;
    /** 关联问卷答案 */
    @ManyToOne
    @JoinColumn(name = "voteResult_id")
    private VoteResult voteResult;
    /** 基础问题项 */
    @ManyToOne
    @JoinColumn(name = "voteBaseItem_id")
    private VoteBaseItemImpl voteBaseItem;
    /** 是否回答正确 */
    @Column
    private Boolean isRight;

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

    public VoteResult getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(VoteResult voteResult) {
        this.voteResult = voteResult;
    }

    public VoteBaseItemImpl getVoteBaseItem() {
        return voteBaseItem;
    }

    public void setVoteBaseItem(VoteBaseItemImpl voteBaseItem) {
        this.voteBaseItem = voteBaseItem;
    }

    public Boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(Boolean isRight) {
        this.isRight = isRight;
    }

}
