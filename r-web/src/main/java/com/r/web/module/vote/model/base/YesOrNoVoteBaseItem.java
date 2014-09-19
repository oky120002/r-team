/**
 * 
 */
package com.r.web.module.vote.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.web.module.vote.VoteBaseItem;
import com.r.web.module.vote.VoteItemType;
import com.r.web.module.vote.exception.AnswerTypeErrorException;
import com.r.web.module.vote.exception.VoteItemContextErrorException;

/**
 * 是非问卷项
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "vote_votebaseitem_yesno")
@PrimaryKeyJoinColumn(name = "viid")
public class YesOrNoVoteBaseItem extends VoteBaseItemImpl implements VoteBaseItem, Serializable {
    private static final long serialVersionUID = 222635932556751177L;

    public YesOrNoVoteBaseItem() {
        super(VoteItemType.yesno);
    }

    /** 正确答案描述 */
    @Column
    private String answerYes;
    /** 错误答案描述 */
    @Column
    private String answerNo;
    /** 正确答案 */
    @Column
    private Boolean answer;
    /** 问卷项提供者 */
    @Column
    private String provider;

    /**
     * 检验传入的答案是否正确<br />
     * <li>布尔类型:请传入一个boolean值</li><br/>
     * <li>字符串类型:"1"或者"true"时,进行true校验,其它进行false校验</li><br/>
     * <li>数字类型:小于等于0进行false校验,大于0进行true校验</li><br/>
     */
    @Override
    public boolean checkAnswer(Object... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }

        boolean ans = false;
        Object obj = objects[0];
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            String strObj = String.valueOf(obj);
            ans = ("1".equals(strObj) || "true".equals(strObj));
        } else if (obj instanceof Boolean) {
            ans = ((Boolean) obj).booleanValue();
        } else if (obj instanceof Number) {
            int intObj = ((Number) obj).intValue();
            ans = (intObj > 0);
        } else {
            throw new AnswerTypeErrorException("是非题问卷项答案只能[字符串],[布尔]和[数字]类型之一");
        }

        return answer == ans;
    }

    @Override
    public void checkVoteItemContext() throws VoteItemContextErrorException {
        super.checkVoteItemContext();
        if (StringUtils.isBlank(answerYes)) {
            throw new VoteItemContextErrorException("问卷项[正确答案描述]不能为空!");
        }
        if (StringUtils.isBlank(answerNo)) {
            throw new VoteItemContextErrorException("问卷项[错误答案描述]不能为空!");
        }
        if (answer == null) {
            throw new VoteItemContextErrorException("问卷项[答案]不能为空!");
        }
    }

    /** 获取正确答案描述 */
    public String getAnswerYes() {
        return answerYes;
    }

    /** 设置正确答案描述 */
    public void setAnswerYes(String answerYes) {
        this.answerYes = answerYes;
    }

    /** 获取错误答案描述 */
    public String getAnswerNo() {
        return answerNo;
    }

    /** 设置错误答案描述 */
    public void setAnswerNo(String answerNo) {
        this.answerNo = answerNo;
    }

    /**
     * @return the answer
     */
    public Boolean getAnswer() {
        return answer;
    }

    /** 设置答案 */
    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    /**
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider
     *            the provider to set
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
