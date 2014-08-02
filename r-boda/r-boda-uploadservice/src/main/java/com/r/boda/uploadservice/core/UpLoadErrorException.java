package com.r.boda.uploadservice.core;

import java.util.ArrayList;
import java.util.List;

import com.r.core.exceptions.SException;

public class UpLoadErrorException extends SException {
    private static final long serialVersionUID = -3366306307845731707L;

    private List<KV<String, String>> errorFileNames = new ArrayList<KV<String, String>>();

    public UpLoadErrorException(String message, int mark) {
        super(message, mark);
    }

    public UpLoadErrorException(String message, Object... objects) {
        super(message, objects);
    }

    public UpLoadErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpLoadErrorException(String message) {
        super(message);
    }

    public UpLoadErrorException(Exception e) {
        super(e.getMessage());
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

}
