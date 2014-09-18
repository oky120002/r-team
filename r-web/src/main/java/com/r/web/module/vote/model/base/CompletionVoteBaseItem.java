/**
 * 
 */
package com.r.web.module.vote.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.web.module.vote.VoteBaseItem;
import com.r.web.module.vote.VoteItemType;
import com.r.web.module.vote.exception.VoteItemContextErrorException;

/**
 * 填空问卷项<br />
 * 空格处以四个"_"作为占位符 也就是"____"
 * 
 * @author oky
 * 
 */
@Entity
@Table(name = "vote_votebaseitem_completion")
@PrimaryKeyJoinColumn(name = "viid")
public class CompletionVoteBaseItem extends VoteBaseItemImpl implements VoteBaseItem, Serializable {
    private static final long serialVersionUID = -1488247346091698158L;
    private static final String PLACEHOLDER = "\\{\\}";

    public CompletionVoteBaseItem() {
        super(VoteItemType.completion);
    }

    /** 正确答案1 */
    @Column
    private String answer1;
    /** 正确答案2 */
    @Column
    private String answer2;
    /** 正确答案3 */
    @Column
    private String answer3;
    /** 正确答案4 */
    @Column
    private String answer4;
    /** 问卷项提供者 */
    @Column
    private String provider;

    /**
     * 检验传入的答案是否正确<br />
     * 请传入一个和空格数量相等的字符串
     */
    @Override
    public boolean checkAnswer(Object... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return false;
        }

        try {
            if (StringUtils.isNotBlank(answer1) && !answer1.equals(objects[0])) {
                return false;
            }
            if (StringUtils.isNotBlank(answer2) && !answer2.equals(objects[1])) {
                return false;
            }
            if (StringUtils.isNotBlank(answer3) && !answer3.equals(objects[2])) {
                return false;
            }
            if (StringUtils.isNotBlank(answer4) && !answer4.equals(objects[3])) {
                return false;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {
        }
        return true;
    }

    @Override
    public void checkVoteItemContext() throws VoteItemContextErrorException {
        super.checkVoteItemContext();
        int size = getQuestionSize();
        int num = 0;
        if (StringUtils.isNotBlank(answer1)) {
            num++;
        }
        if (StringUtils.isNotBlank(answer2)) {
            num++;
        }
        if (StringUtils.isNotBlank(answer3)) {
            num++;
        }
        if (StringUtils.isNotBlank(answer4)) {
            num++;
        }
        if (num != size) {
            throw new VoteItemContextErrorException("填空问卷项[答案]数量和问题中空缺数量有差异!");
        }
    }

    @Override
    public String getQuestion() {
        return super.getQuestion().replace("{}", "________");
    }

    /** 返回问题空格数量 */
    public int getQuestionSize() {
        return ("," + this.question + ",").split(PLACEHOLDER).length - 1;
    }

    public List<String> getCompletionAnswerLabels() {
        List<String> labels = new ArrayList<String>();
        int size = getQuestionSize();
        for (int i = 0; i < size; i++) {
            switch (i) {
            case 0:
                labels.add("A : ");
                break;
            case 1:
                labels.add("B : ");
                break;
            case 2:
                labels.add("C : ");
                break;
            case 3:
                labels.add("D : ");
                break;
            }
        }
        return labels;
    }

    /**
     * @return the answer1
     */
    public String getAnswer1() {
        return answer1;
    }

    /**
     * @param answer1
     *            the answer1 to set
     */
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    /**
     * @return the answer2
     */
    public String getAnswer2() {
        return answer2;
    }

    /**
     * @param answer2
     *            the answer2 to set
     */
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    /**
     * @return the answer3
     */
    public String getAnswer3() {
        return answer3;
    }

    /**
     * @param answer3
     *            the answer3 to set
     */
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    /**
     * @return the answer4
     */
    public String getAnswer4() {
        return answer4;
    }

    /**
     * @param answer4
     *            the answer4 to set
     */
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
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
