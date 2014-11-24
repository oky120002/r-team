/**
 * 
 */
package com.r.core.util;

/**
 * 计算实用工具<br />
 * 位计算查看{@link http://blog.csdn.net/jiqingxia37/article/details/8293820}
 * 
 * @author oky
 * 
 */
public abstract class CalUtil {
    /** 取负数 */
    public static int dam(int n) {
        return n < 0 ? n : -n;
    }

    /** 取绝对值 */
    public static int abs(int n) {
        return (n ^ (n >> 31)) - (n >> 31);
    }

    /** 取最大值 */
    public static int max(int a, int b) {
        return b & ((a - b) >> 31) | a & (~(a - b) >> 31);
        /* 如果a>=b,(a-b)>>31为0，否则为-1 */
    }

    /** 取最小值 */
    public static int min(int a, int b) {
        return a & ((a - b) >> 31) | b & (~(a - b) >> 31);
        /* 如果a>=b,(a-b)>>31为0，否则为-1 */
    }

    /** 判断符号是否相同 */
    public static boolean isSameSign(int a, int b) {
        return (a ^ b) > 0; // true 表示 a和b有相同的符号， false表示a，b有相反的符号。
    }

    /** 判断数字奇偶性 */
    public static boolean isOddNumber(int n) {
        return (n & 1) == 1;
    }

    /** 取平均数 */
    public static int avg(int a, int b) {
        return (a + b) >> 1;
    }

    public static void main(String[] args) {
        System.out.println(12 & 4);
        System.out.println(12 & 8);
        System.out.println(13 & 1);
        System.out.println(14 & 2);
    }

    /**
     * 位判断<br/>
     * 判断num是不是sum加数中的一个
     * 
     * @param sum
     *            1,2,4,8,16,32...中某些数字之和
     * @param num
     *            1,2,4,8,16,32...中的一个数字
     * @return
     */
    public static boolean inBit(int sum, int num) {
        return (sum & num) == num;
    }
}
