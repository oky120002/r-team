package com.r.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 特定字符串生成类
 * 
 * @author rain
 * 
 */
public abstract class CodeUtil {
	/**
	 * 返回UUID随机字符串
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * md5加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 *             没有找到MD5编码器时,抛出此错误
	 * @throws UnsupportedEncodingException
	 *             编码失败时,抛出此错误
	 */
	public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("UTF-8"));
		return StrUtil.byteToHexString(md.digest());
	}

	/**
	 * Base64加密
	 * 
	 * @param str
	 *            原始字符串
	 * @return 加密后的字符串
	 */
	@SuppressWarnings("restriction")
	public static String base64Code(String str) {
		return new sun.misc.BASE64Encoder().encode(str.getBytes());
	}

	/**
	 * Base64解密
	 * 
	 * @param str
	 *            加密后的字符串
	 * @return 解密后的字符串
	 * @throws IOException
	 *             加密格式字符串错误
	 */
	@SuppressWarnings("restriction")
	public static String base64EnCode(String str) throws IOException {
		return new String(new sun.misc.BASE64Decoder().decodeBuffer(str));
	}

	/**
	 * 
	 * 字符串编码转换
	 * 
	 * @param str
	 *            字符串
	 * @param internal
	 *            内部编码
	 * @param read
	 *            转换后的编码
	 * @return 转后的字符串
	 */
	public static String code(String str, Charset internal, Charset read) {
		if (str == null) {
			return null;
		}
		if (internal == null && read == null) { // 两个编码都没有设置
			return str;
		} else if (read == null) {
			str = new String(str.getBytes(internal));
		} else if (internal == null) {
			str = new String(str.getBytes(), read);
		} else { // 设置了内部编码和读取编码
			str = new String(str.getBytes(internal), read);
		}
		return str;
	}
}
