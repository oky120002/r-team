package com.yy.udbsdk;

final class UdbSdkCalls {
    static {
        try {
            // System.loadLibrary("crypto");
            // System.loadLibrary("UdbAndroidSdk");
            System.load("D:\\develop\\workspace-sts-3.6.3.RELEASE\\r-team\\r-qqcard\\libUdbAndroidSdk.so");
        } catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {
            localUnsatisfiedLinkError.printStackTrace();
        } 
    }

    public static native void closeConn();

    public static native int doForgetPassword(String paramString);

    public static native Ret_PicCode doInitPiccode();

    public static native Ret_DoLogin doLogin(String paramString1, String paramString2, boolean paramBoolean);

    public static native Ret_DoLogin doLoginBySavedTicket(String paramString, byte[] paramArrayOfByte, long paramLong1, long paramLong2);

    public static native Ret_DoLogin doLoginByYYTicket(String paramString, byte[] paramArrayOfByte);

    public static native int doModifyPassword(String paramString1, String paramString2, String paramString3);

    public static native Ret_PicCode doPicModifyPwd(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

    public static native Ret_PicCode doPicRegister(String paramString1, String paramString2, String paramString3, String paramString4);

    public static native int doPreRegister(String paramString);

    public static native Ret_DoReg doRegister(String paramString1, long paramLong, String paramString2, String paramString3);

    public static native boolean doSmsGetVerifyForPwd(String paramString1, String paramString2);

    public static native boolean doSmsGetVerifyForReg(String paramString1, String paramString2);

    public static native boolean doSmsPwd(String paramString1, String paramString2, String paramString3);

    public static native Ret_DoReg doSmsReg(String paramString1, String paramString2, String paramString3, long paramLong);

    public static native Ret_DoReg doSmsRegister(String paramString1, long paramLong, String paramString2);

    public static native int getErrno();

    public static native String getErrorStr();

    public static native String getSdkVersion();

    public static native String getSmsGateway(String paramString);

    public static native Ret_Token getToken(String paramString);

    public static native String getUNameSuffix();

    public static native int openSafeConn();

    public static native boolean setAppid(String paramString);

    public static native void setServerEndPoint(String paramString, int paramInt);
}

/*
 * Location: /Users/rain/Desktop/baksmall/classes-dex2jar.jar Qualified Name:
 * com.yy.udbsdk.UdbSdkCalls JD-Core Version: 0.6.2
 */