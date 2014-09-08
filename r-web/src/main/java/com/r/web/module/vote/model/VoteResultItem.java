package com.r.web.module.vote.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.r.web.module.vote.model.base.VoteBaseItemImpl;

/**
 * 答案条目
 * 
 * @author Administrator
 *
 */
public class VoteResultItem {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;
    /** 问题编号 */
    @Column
    private Integer no;
    /** 问题项 */
    @Column
    private VoteBaseItemImpl voteItem;
    
    
}
