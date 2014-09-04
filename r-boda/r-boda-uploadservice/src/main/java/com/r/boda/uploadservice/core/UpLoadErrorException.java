package com.r.boda.uploadservice.core;

import java.util.ArrayList;
import java.util.List;

import com.r.core.exceptions.SException;

public class UpLoadErrorException extends SException {
    private static final long serialVersionUID = -3366306307845731707L;

    private List<KV<String, String>> errorFileNames = new ArrayList<KV<String, String>>();

    public UpLoadErrorException() {
        super();
    }

    public UpLoadErrorException(String message, Throwable cause) {
        super(message, cause);
        addError("文件上传错误", message + cause.toString());
    }

    public UpLoadErrorException(String message) {
        super(message);
        addError("文件上传错误", message);
    }

    @Override
    protected String doGetErrorCode() {
        return "UPLOAD_ERROR_EXCEPTION";
    }

    @Override
    protected String doGetErrorMessage() {
        return "附件平台内部错误";
    }

    public void addError(String errorFileName, String errorMessage) {
        errorFileNames.add(KV.kv(errorFileName, errorMessage));
    }

    public List<KV<String, String>> getErrorFileNames() {
        return errorFileNames;
    }

    @Override
    public String getMessage() {
        StringBuilder errorMsg = new StringBuilder();
        for (KV<String, String> kv : errorFileNames) {
            errorMsg.append(kv.getKey() + " : " + kv.getValue()).append("\r\n");
        }
        return errorMsg.toString();
    }

}
