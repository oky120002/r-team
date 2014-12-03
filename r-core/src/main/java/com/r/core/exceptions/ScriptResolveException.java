/**
 * 
 */
package com.r.core.exceptions;

/**
 * 脚本解析(执行)异常
 * 
 * @author rain
 *
 */
public class ScriptResolveException extends SException {
    private static final long serialVersionUID = -5900497598679311937L;

    @Override
    protected String doGetErrorCode() {
        return "SCRIPT_RESOLVE_EXCEPTION";
    }

    @Override
    protected String doGetErrorMessage() {
        return "脚本解析异常";
    }

    /**
     * @param message
     * @param cause
     */
    public ScriptResolveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ScriptResolveException(String message) {
        super(message);
    }

}
