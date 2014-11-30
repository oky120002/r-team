/**
 * 
 */
package com.r.core.exceptions;

import java.lang.reflect.InvocationTargetException;

/**
 * 执行方法失败异常
 * 
 * @author rain
 *
 */
public class InvokeMethodException extends SException {
    private static final long serialVersionUID = -5776232300083288763L;

    @Override
    protected String doGetErrorCode() {
        return "Invoke_Method";
    }

    @Override
    protected String doGetErrorMessage() {
        return "执行方法失败";
    }

    public InvokeMethodException(Throwable cause) {
        super(getErrorMsgFromThrowable(cause), cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * 从异常解析出异常信息
     * 
     * @param cause
     *            异常
     * @return 异常信息
     */
    private static String getErrorMsgFromThrowable(Throwable cause) {
        if (cause instanceof InvocationTargetException) {
            return "调用方法或构造方法抛出异常";
        } else if (cause instanceof IllegalArgumentException) {
            return "向方法传递了一个不合法或不正确的参数";
        } else if (cause instanceof IllegalAccessException) {
            return "无法访问指定类、字段、方法或构造方法的定义";
        } else if (cause instanceof SecurityException) {
            return "由安全管理器抛出的异常，指示存在安全侵犯";
        } else if (cause instanceof NoSuchMethodException) {
            return "无法找到指定方法";
        } else {
            return cause.getMessage();
        }
    }
}
