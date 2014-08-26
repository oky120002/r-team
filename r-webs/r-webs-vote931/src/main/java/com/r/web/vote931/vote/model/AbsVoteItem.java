package com.r.web.vote931.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.r.web.vote931.vote.VoteItem;
import com.r.web.vote931.vote.VoteItemType;
import com.r.web.vote931.vote.exception.VoteItemContextErrorException;

@Entity
@Table(name = "voteitem")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbsVoteItem implements VoteItem {

    public AbsVoteItem(VoteItemType type) {
        super();
        this.type = type;
    }

    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    protected String id;
    /** 问卷项编号 */
    @Column
    protected String no;
    /** 问卷项问题 */
    @Column
    protected String question;
    /** 类型 */
    @Column
    @Enumerated(EnumType.STRING)
    protected VoteItemType type;

    @Override
    public String getId() {
        return id;
    }

    /** 设置问卷项唯一标识 */
    public void setId(String id) {
        this.id = id;
    }

    /** 返回问卷项编号 */
    @Override
    public String getNo() {
        return no;
    }

    /** 设置问卷号编号 */
    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    /** 设置问卷项问题 */
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public VoteItemType getType() {
        return type;
    }

    public void setType(VoteItemType type) {
    }

    @Override
    public void checkVoteItemContext() {
        if (StringUtils.isBlank(no)) {
            throw new VoteItemContextErrorException("问卷项[编号]不能为空!");
        }
        if (StringUtils.isBlank(question)) {
            throw new VoteItemContextErrorException("问卷项[问题]不能为空!");
        }
        if (type == null) {
            throw new VoteItemContextErrorException("问卷项[类型]不能为空!");
        }
    }
}
