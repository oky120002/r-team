package com.r.core.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;

import javax.swing.JComponent;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * 
 * 字符串实用工具
 * 
 * @author Rain
 * 
 */
public abstract class StrUtil {

    /**
     * 将16进制字符串转换成字节数组<br />
     * 没有进行测试,不能知道能不能得到正确结果
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) ("0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | "0123456789ABCDEF".indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /** 将指定byte数组转换成16进制字符串 */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /** 将指定的颜色转换成16进制字符串 */
    public static String colorToHexString(Color color) {
        String red = StrUtil.replenishZero(Integer.toHexString(color.getRed()), 2, 0);
        String green = StrUtil.replenishZero(Integer.toHexString(color.getGreen()), 2, 0);
        String blue = StrUtil.replenishZero(Integer.toHexString(color.getBlue()), 2, 0);
        return "#" + red + green + blue;
    }

    /**
     * 将普通字符串转换成"get"方法名称<br>
     * 例如:hello -> getHello
     * 
     * @param str
     * @return
     */
    public static String strToFunctionName(String str) {
        return "get" + str.toUpperCase().charAt(0) + str.substring(1);
    }

    /**
     * 将"get"方法名称转换成普通字符串<br>
     * 例如:getHello -> hello
     * 
     * @param functionName
     * @return
     */
    public static String functionNameToStr(String functionName) {
        functionName = functionName.trim().substring(3);
        return functionName.toLowerCase().charAt(0) + functionName.substring(1);
    }

    /**
     * 数字补零 例子: <br>
     * 字符串:abc,补零位数:5,补零位置:0,结果:00abc <br>
     * 字符串:abc,补零位数:5,补零位置:1,结果:a00bc <br>
     * 字符串:abc,补零位数:5,补零位置:3,结果:abc00 <br>
     * 字符串:abc,补零位数:2,补零位置:0,结果:abc
     * 
     * @param str
     *            要补零的字符串
     * @param digit
     *            补零位数
     * @param site
     *            补零位置(大于字符串长度||小于0:右边补零,等于0:左边补零,中间数字:截断字符串补零)
     * @return
     * @throws Exception
     */
    public static String replenishZero(String str, Integer digit, Integer site) {
        if (str.length() >= digit)
            return str;
        if (site < 0 || site >= str.length())
            site = str.length();
        digit = digit - str.length();
        StringBuffer zero = new StringBuffer();
        for (int i = 0; i < digit; i++) {
            zero.append('0');
        }
        String preStr = str.substring(0, site);
        String lastStr = str.substring(site, str.length());
        StringBuffer sb = new StringBuffer();
        sb.append(preStr).append(zero).append(lastStr);
        return sb.toString();
    }

    /** 返回随机的32位UUID */
    public static String uuid() {
        return RandomUtil.uuid();
    }

    /** 根据前字符串和后字符串截取中间的一段字符串,如果截取不到,则返回null */
    public static String mid(String html, String pre, String last) {
        int index = html.indexOf(pre);
        int lastIndex = html.lastIndexOf(last);
        if (index == -1 || lastIndex == -1) {
            return null;
        }
        return html.substring(index + pre.length(), lastIndex);
    }

    /**
     * 格式化字符串<br />
     * 把"{}"字符按照传入顺序替换成传入的字符串
     * 
     * @param message
     *            格式化的信息
     * @param objects
     *            格式化参数
     * @return 格式化完成后的字符串
     */
    public static String formart(String message, Object... objects) {
        if (ArrayUtils.isEmpty(objects)) {
            return message;
        }
        return MessageFormatter.arrayFormat(message, objects).getMessage();
    }

    /**
     * 计算字符串长宽
     * 
     * @param component
     *            字符串所在的控件,如果传入null,则获取默认值 字符串所在的控件
     * @param str
     *            字符串
     * @param font
     *            字体,如果传入null,则自动提取component上的字体
     * @return 返回字符串的长宽
     * @see javax.swing.JComponent#getFont()
     */
    @SuppressWarnings("deprecation")
    public static Dimension getStringWidth(JComponent component, String str, Font font) {
        if (component == null) {
            FontMetrics fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
            int fontWidth = fontMetrics.stringWidth(str);
            int fontHeight = fontMetrics.getHeight();
            return new Dimension(fontWidth, fontHeight);
        } else {
            if (font == null) {
                font = component.getFont();
            }
            FontMetrics fontMetrics = component.getFontMetrics(font);
            int fontWidth = fontMetrics.stringWidth(str);
            int fontHeight = fontMetrics.getHeight();
            return new Dimension(fontWidth, fontHeight);
        }
    }
}
