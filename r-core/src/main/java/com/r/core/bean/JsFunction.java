/**
 * 
 */
package com.r.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 此JS数据模式,没有进行正确方法校验<br />
 * 等待以后改用antlr语法解析器来解析,和格式校验 JS数据模型<br />
 * 如果在参数里面遇见","号.则会解析错误.需要特别注意<br/>
 * 解析样例 : "ptuiCB('4','2','','0','页面过期，请重试。*', '14006297');"
 * 
 * @author Administrator
 * 
 */
public class JsFunction {
    private String js;
    private String jsName;
    private List<String> pars = new ArrayList<String>();

    public JsFunction(String js) {
        if (StringUtils.isNotBlank(js)) {
            this.js = js;
            resolve();
        }
    }

    /** 解析jsfunction字符串 */
    private void resolve() {
        js = js.trim();

        // 如果最后一个符号是";"号.则去掉
        char lastChar = js.charAt(js.length() - 1);
        if (lastChar == ';') {
            js = js.substring(0, js.length() - 1);
        }

        // 截取方法名
        int indexOf = js.indexOf('(');
        this.jsName = js.substring(0, indexOf);

        // 去掉左右括号
        js = js.substring(indexOf + 1, js.length() - 1);

        // 提取参数
        String[] pars = js.split(",");
        for (String par : pars) {
            par = par.trim(); // 去掉左右空格
            par = par.substring(1, par.length() - 1); // 去掉左右引号(或者单引号)
            this.pars.add(par);
        }
    }

    /**
     * @return the jsName
     */
    public String getJsName() {
        return jsName;
    }

    /**
     * @return the pars
     */
    public List<String> getPars() {
        return pars;
    }

    /** 返回序号对应的参数,从1开始 */
    public String getPar(int index) {
        return this.pars.get(index - 1);
    }
}
