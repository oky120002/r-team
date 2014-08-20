package com.r.app.sample.yy95;

import com.r.core.support.KV;
import com.r.core.util.RandomUtil;

public class StrIntKV extends KV<String, Integer> {

    private String nickname;

    private Integer maxValue;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 随机返回两个数字间的数字
     * 
     * @param multiple
     *            翻倍的系数
     * @return
     */
    public Integer getRandomMinAndMaxValue(int multiple) {
        if (value == null && maxValue == null) {
            return null;
        } else if (value != null && maxValue != null) {
            if (value.intValue() == maxValue.intValue()) {
                return value.intValue() * multiple;
            } else {
                return RandomUtil.randomInteger(value.intValue() * multiple, maxValue.intValue() * multiple);
            }
        } else {
            return (value == null ? maxValue : value).intValue() * multiple;
        }
    }

    /** 创建"键值对" */
    public static StrIntKV accountKV(String key, Integer value, Integer maxValue, String nickname) {
        StrIntKV kv = new StrIntKV();
        kv.setKey(key);
        kv.setValue(value);
        kv.setMaxValue(maxValue);
        kv.setNickname(nickname);
        return kv;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
