/**
 * 
 */
package com.r.app.sample.vote.impl;

import com.r.app.sample.vote.VoteItem;

/**
 * 问题
 * 
 * @author oky
 * 
 */
public class VoteItemImpl implements VoteItem {
    private String question; // 问题

    @Override
    public String getQuestion() {
        return question;
    }

    /** 设置 问题 */
    public void setQuestion(String question) {
        this.question = question;
    }
    
    
}
