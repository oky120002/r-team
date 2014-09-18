/**
 * 
 */
package com.r.web.module.vote.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问卷答案
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "vote_voteresult")
public class VoteResult {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问卷答案编号 */
    @Column(unique = true)
    private Integer no;
    /** 关联问卷 */
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
    /** 问卷结果条目集合 */
    @OneToMany
    @JoinColumn(name = "voteResult_id")
    private List<VoteResultItem> soteResultItems;

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
