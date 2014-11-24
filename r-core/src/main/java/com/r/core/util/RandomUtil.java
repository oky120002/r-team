/**
 * 工具
 */
package com.r.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 随机实用工具
 * 
 * @author rain
 */
public final class RandomUtil {
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(RandomUtil.class);

    /** 用于产生随机字符串,去掉不容易识别的字符 */
    private static final String RANDOMSTRING = "3456789abcdefghjkmpqrstwxyABCDEFGHIJKMPQRSTWXY";

    /** 中文转码的编码 */
    private static final String STRING_CHINESE_CODE = "gbk";

    /** 随机数产生库 */
    private static final Random random = new Random(System.currentTimeMillis());

    /**
     * 
     * 产生[0,maxNumber)之间的随机整数
     * 
     * @param maxNumber
     *            任意大于0的数字,否则返回0到Integer的最大值之间的任意数字, 如果传入0,则返回0
     * @return 返回[0,maxNumber)之间的随机数字
     * 
     * @return [0,maxNumber)之间的随机整数
     */
    public static int randomInteger(int maxNumber) {
        if (maxNumber == 0) {
            return 0;
        }
        if (0 < maxNumber) {
            return random.nextInt(maxNumber);
        }
        return random.nextInt();
    }

    /**
     * 
     * 产生[minNumber,maxNumber)之间的随机正整数<br />
     * minNumber
     * 
     * @param minNumber
     *            任意大于等于0的数字
     * @param maxNumber
     *            任意大于等于0数字
     * @return int [minNumber,maxNumber)之间的随机整数
     */
    public static int randomInteger(int minNumber, int maxNumber) {
        if (minNumber < 0 || maxNumber < 0) {
            throw new IllegalArgumentException("minNumber 和 maxNumber 必须是非负数");
        }

        if (minNumber == maxNumber) { // 如果最大值等于最小值,则返回0
            return 0;
        }
        if (minNumber > maxNumber) { // 如果minNumber大于maxNumber,则交换两个数字
            minNumber ^= maxNumber;
            maxNumber ^= minNumber;
            minNumber ^= maxNumber;
        }

        return random.nextInt(maxNumber - minNumber) + minNumber;
    }

    /** 通过提供默认字符串库,从重随机选择length个来组成随机字符串返回 */
    public static String randomString(String str, int length) {
        if (StringUtils.isBlank(str) || length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int size = str.length();
        for (int index = 0; index < length; index++) {
            sb.append(str.charAt(randomInteger(size)));
        }
        return sb.toString();
    }

    /** 通过默认字符串库,从重随机选择length个来组成随机字符串返回 */
    public static String randomString(int length) {
        return randomString(RANDOMSTRING, length);
    }

    /**
     * 
     * 计算命中率<br />
     * 以100为基数根据rate参数计算命中率
     * 
     * @param rate
     *            命中概率,如果小于等于0,则直接不命中
     * @param maxRate
     *            最大命中概率,如果小于等于0,则为最大命中率为100
     * @return 如果命中,则返回true,否则返回false
     */
    public static boolean hitRate100(int rate, int maxRate) {
        if (rate <= 0) {
            return false;
        }
        maxRate = maxRate <= 0 ? 100 : maxRate;
        rate = CalUtil.min(rate, maxRate);

        return rate > randomInteger(0, 99) ? true : false;
    }

    /**
     * 
     * 返回随机生成的汉字 <br />
     * 原理是从汉字区位码找到汉字。 在汉字区位码中分高位与底位，且其中简体又有繁体。 位数越前生成的汉字繁体的机率越大。
     * 所以在本方法中高位从176取，底位从161取，去掉大部分的繁体和生僻字。 但仍然会有.
     * 
     * @return String 随机汉字
     * @exception throws UnsupportedEncodingException 不支持GBK编码
     */
    public static String getChineseCharacter() {
        String str = null;

        // 定义高低位
        int hightPos = 176 + RandomUtil.randomInteger(39);
        int lowPos = 161 + RandomUtil.randomInteger(93);

        byte[] b = new byte[2];

        b[0] = Integer.valueOf(hightPos).byteValue();
        b[1] = Integer.valueOf(lowPos).byteValue();

        try {
            str = new String(b, STRING_CHINESE_CODE);
        } catch (UnsupportedEncodingException e) {
            str = new String(b);
            logger.warn("不支持gbk编码", e);
        }

        return str;
    }

    /**
     * 
     * 返回随机生成的UUID
     * 
     * @return String 32位随机UUID字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(randomInteger(0, 1));
    }
}
