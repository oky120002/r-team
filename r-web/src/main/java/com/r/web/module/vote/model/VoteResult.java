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
 * 问卷结果
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
    /** 问卷结果编号 */
    @Column(unique = true)
    private Integer no;
    /** 关联问卷 */
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;
    /** 问卷结果条目集合 */
    @OneToMany
    @JoinColumn(name = "voteResult_id")
    private List<VoteResultItem> voteResultItems;
    /** 备注 */
    @Column
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /** 获取编号 */
    public Integer getNo() {
        return no;
    }

    /** 设置编号 */
    public void setNo(Integer no) {
        this.no = no;
    }

    /** 获取问卷 */
    public Vote getVote() {
        return vote;
    }

    /** 设置问卷 */
    public void setVote(Vote vote) {
        this.vote = vote;
    }

    /** 获取问卷结果条目集合 */
    public List<VoteResultItem> getVoteResultItems() {
        return voteResultItems;
    }

    /** 设置问卷结果条目集合 */
    public void setVoteResultItems(List<VoteResultItem> voteResultItems) {
        this.voteResultItems = voteResultItems;
    }

    /** 获取备注 */
    public String getRemark() {
        return remark;
    }

    /** 设置备注 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
