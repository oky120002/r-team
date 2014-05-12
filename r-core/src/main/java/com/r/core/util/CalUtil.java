/**
 * 
 */
package com.r.core.util;

/**
 * 计算工具<br />
 * 可以查看{@link http://blog.csdn.net/jiqingxia37/article/details/8293820}
 * 
 * @author oky
 * 
 */
public class CalUtil {

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
	public boolean isSameSign(int a, int b) {
		return (a ^ b) > 0; // true 表示 a和b有相同的符号， false表示a，b有相反的符号。
	}

	/** 判断数字奇偶性 */
	public boolean isOddNumber(int n) {
		return (n & 1) == 1;
	}

	/** 取平均数 */
	public int avg(int a, int b) {
		return (a + b) >> 1;
	}
}
