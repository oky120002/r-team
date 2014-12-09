/**
 * 
 */
package com.yy.udbsdk;

import com.mchange.lang.ByteUtils;

/**
 * @author rain
 *
 */
public class YYTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        UdbSdkCalls call = new UdbSdkCalls();
        Ret_Token token = call.getToken("abcd");
        String hexAscii = ByteUtils.toHexAscii(token.token);
        System.out.println(hexAscii);
    }

}
