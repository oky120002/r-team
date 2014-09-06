package com.r.web.component.incrementer.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.r.core.util.AssertUtil;
import com.r.web.component.incrementer.RainMaxValueIncrementer;

public class IncrementerContextConfigurator implements InitializingBean {

    public static final String DEFAULT_INCREMENTER_NAME = "default";

    protected Map<String, RainMaxValueIncrementer> incrementers = new HashMap<String, RainMaxValueIncrementer>();

    @Override
    public void afterPropertiesSet() throws Exception {
        // 校验必要参数 - 自增长器集合不能为空
        AssertUtil.isNotEmpty("必须最少设置一个自增长器", incrementers);
        // 校验必要参数 - 必须设置一个默认的自增长name值
        AssertUtil.state("必须设置一个默认的自增长name值: \"" + DEFAULT_INCREMENTER_NAME + "\"", incrementers.containsKey(DEFAULT_INCREMENTER_NAME));
    }

    public Map<String, RainMaxValueIncrementer> getIncrementers() {
        return incrementers;
    }

    public void setIncrementers(Map<String, RainMaxValueIncrementer> incrementers) {
        this.incrementers = incrementers;
    }

}
