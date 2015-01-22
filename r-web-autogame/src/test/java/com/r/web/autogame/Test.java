/**
 * 
 */
package com.r.web.autogame;

import java.io.UnsupportedEncodingException;

/**
 * @author rain
 *
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String t = "8080800510929131535215620100401700801020620604000000000001éå¿å01510228196509284355000100023518^1;00023519^1;2c9092e43765ab5b013765dc5a8c056710012013-05-20 11:07:17";

        System.out.println(new String(t.getBytes("UTF-8")));
        System.out.println(new String(t.getBytes("GBK")));
        System.out.println(new String(t.getBytes("ISO-8859-1")));
    }
}
