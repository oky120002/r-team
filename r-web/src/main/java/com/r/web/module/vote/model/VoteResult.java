/**
 * 
 */
package com.r.web.module.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷答案
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "voteresult")
public class VoteResult {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷编号 */
    @Column(unique = true)
    private Integer no;
    /** 关联问卷 */
    @ManyToOne(targetEntity = Vote.class, fetch = FetchType.LAZY)
    private Vote vote;

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

}
