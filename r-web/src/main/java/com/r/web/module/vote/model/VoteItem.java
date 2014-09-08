package com.r.web.module.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.r.web.module.vote.model.base.VoteBaseItemImpl;

@Entity
@Table(name = "voteitem")
public class VoteItem {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷项编号,用来排序 */
    @Column
    private Integer no;
    /** 关联问卷 */
    @ManyToOne(targetEntity = Vote.class, fetch = FetchType.LAZY)
    private Vote vote;
    /** 关联的基本问卷项 */
    @ManyToOne(targetEntity = Vote.class, fetch = FetchType.LAZY)
    private VoteBaseItemImpl voteBaseItem;

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

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public VoteBaseItemImpl getVoteBaseItem() {
        return voteBaseItem;
    }

    public void setVoteBaseItem(VoteBaseItemImpl voteBaseItem) {
        this.voteBaseItem = voteBaseItem;
    }

}
