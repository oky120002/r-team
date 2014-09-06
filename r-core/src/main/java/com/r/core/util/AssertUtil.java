/**
 * 描          述:  描述
 * 修  改   人:  oky
 * 修改时间:  2013-10-8
 * 修改描述:
 */
package com.r.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.core.exceptions.AssertException;

/**
 * 断言工具<br />
 * 此工具主要作用于方法参数的合法性判断<br />
 * 此工具就类是于JUnit的断言库。 如果传入一个错误的或者不合法的参数,就抛出一个{@link AssertException}异常。
 * 
 * @author Rani
 * @version 版本号, 2013-10-8
 */
public abstract class AssertUtil {

    /**
     * 断言对象为{@code false}。
     * 
     * <pre class="code">
     * AssertUtil.isFalse(&quot;参数对象必须为false&quot;, false);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code false}则抛出异常
     */
    public static void isFalse(CharSequence message, Boolean expression) {
        if (expression == null || expression) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code false}。
     * 
     * <pre class="code">
     * AssertUtil.isFalse(&quot;参数对象必须为false&quot;, false);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code false}则抛出异常
     */
    public static void isFalse(CharSequence message, boolean expression) {
        if (expression) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code false}。
     * 
     * <pre class="code">
     * AssertUtil.isFalse(false);
     * </pre>
     * 
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code false}则抛出异常
     */
    public static void isFalse(boolean expression) {
        isFalse("[断言失败] - 参数对象必须为true", expression);
    }

    /**
     * 断言对象为{@code false}。
     * 
     * <pre class="code">
     * AssertUtil.isFalse(false);
     * </pre>
     * 
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code false}则抛出异常
     */
    public static void isFalse(Boolean expression) {
        isFalse("[断言失败] - 参数对象必须为true", expression);
    }

    /**
     * 断言对象为{@code true}。
     * 
     * <pre class="code">
     * AssertUtil.isTrue(&quot;参数对象必须为true&quot;, true);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code true}则抛出异常
     */
    public static void isTrue(CharSequence message, Boolean expression) {
        if (expression == null || !expression) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code true}。
     * 
     * <pre class="code">
     * AssertUtil.isTrue(&quot;参数对象必须为true&quot;, true);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code true}则抛出异常
     */
    public static void isTrue(CharSequence message, boolean expression) {
        if (!expression) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code true}。
     * 
     * <pre class="code">
     * AssertUtil.isTrue(true);
     * </pre>
     * 
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code true}则抛出异常
     */
    public static void isTrue(boolean expression) {
        isTrue("[断言失败] - 参数对象必须为true", expression);
    }

    /**
     * 断言对象为{@code true}。
     * 
     * <pre class="code">
     * AssertUtil.isTrue(true);
     * </pre>
     * 
     * @param expression
     *            true或者false
     * @throws AssertException
     *             如果对象不是{@code true}则抛出异常
     */
    public static void isTrue(Boolean expression) {
        isTrue("[断言失败] - 参数对象必须为true", expression);
    }

    /**
     * 断言对象不为{@code null}。
     * 
     * <pre class="code">
     * AssertUtil.isNotNull(&quot;参数对象必须为非{@code null}&quot;, value);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象是{@code null}则抛出异常
     */
    public static void isNotNull(CharSequence message, Object object) {
        if (object == null) {
            fail(message);
        }
    }

    /**
     * 断言对象不为{@code null}。
     * 
     * <pre class="code">
     * AssertUtil.isNotNull(value);
     * </pre>
     * 
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象为{@code null}则抛出异常
     */
    public static void isNotNull(Object object) {
        isNotNull("[断言失败] - 参数对象必须为不为null", object);
    }

    /**
     * 断言对象为{@code null}。
     * 
     * <pre class="code">
     * AssertUtil.isNull(&quot;参数对象必须为null&quot;, value);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象不是{@code null}则抛出异常
     */
    public static void isNull(CharSequence message, Object object) {
        if (object != null) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code null}。
     * 
     * <pre class="code">
     * AssertUtil.isNull(value);
     * </pre>
     * 
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象不为{@code null}则抛出异常
     */
    public static void isNull(Object object) {
        isNull("[断言失败] - 参数对象必须为null", object);
    }

    /**
     * 
     * 断言两个对象不相等<br />
     * 都为{@code null}时相等,任一为{@code null}另外不为{@code null}时不相等,都不为{@code null} 时用
     * {@code equals()}方法判断
     * 
     * <pre class="code">
     * AssertUtil.isNotEq(srcObj, tagObj);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param srcObj
     *            原对象
     * @param tagObj
     *            比较对象
     * 
     * @throws AssertException
     *             如果对象相等则抛出异常
     * 
     */
    public static void isNotEq(CharSequence message, Object srcObj, Object tagObj) {
        if ((srcObj == null && tagObj == null) || (srcObj != null && tagObj != null && srcObj.equals(tagObj))) {
            fail(message);
        }
    }

    /**
     * 
     * 断言两个对象不相等<br />
     * 都为{@code null}时相等,任一为{@code null}另外不为{@code null}时不相等,都不为{@code null} 时用
     * {@code equals()}方法判断
     * 
     * <pre class="code">
     * AssertUtil.isNotEq(srcObj, tagObj);
     * </pre>
     * 
     * @param srcObj
     *            原对象
     * @param tagObj
     *            比较对象
     * 
     * @throws AssertException
     *             如果对象相等则抛出异常
     * 
     */
    public static void isNotEq(Object srcObj, Object tagObj) {
        isNotEq("[断言失败] - 传入的参数相等", srcObj, tagObj);
    }

    /**
     * 
     * 断言两个对象相等<br />
     * 都为{@code null}时相等,任一为{@code null}另外不为{@code null}时不相等,都不为{@code null} 时用
     * {@code equals()}方法判断
     * 
     * <pre class="code">
     * AssertUtil.isEq(srcObj, tagObj);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param srcObj
     *            原对象
     * @param tagObj
     *            比较对象
     * 
     * @throws AssertException
     *             如果对象不相等则抛出异常
     * 
     */
    public static void isEq(CharSequence message, Object srcObj, Object tagObj) {
        if (!((srcObj == null && tagObj == null) || (srcObj != null && tagObj != null && srcObj.equals(tagObj)))) {
            fail(message);
        }
    }

    /**
     * 
     * 断言两个对象相等<br />
     * 都为{@code null}时相等,任一为{@code null}另外不为{@code null}时不相等,都不为{@code null} 时用
     * {@code equals()}方法判断
     * 
     * <pre class="code">
     * AssertUtil.isEq(srcObj, tagObj);
     * </pre>
     * 
     * @param srcObj
     *            原对象
     * @param tagObj
     *            比较对象
     * 
     * @throws AssertException
     *             如果对象不相等则抛出异常
     * 
     */
    public static void isEq(Object srcObj, Object tagObj) {
        isEq("[断言失败] - 传入的参数不相等", srcObj, tagObj);
    }

    /**
     * 断言n1等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1不等于n2,则抛出异常
     */
    public static void isEq(CharSequence message, int n1, int n2) {
        if (n1 != n2) {
            fail(message);
        }
    }

    /**
     * 断言n1等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1不等于n2,则抛出异常
     */
    public static void isEq(int n1, int n2) {
        isEq("[断言失败] - n1等于n2", n1, n2);
    }

    /**
     * 断言n1不等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1等于n2,则抛出异常
     */
    public static void isNotEq(CharSequence message, int n1, int n2) {
        if (n1 == n2) {
            fail(message);
        }
    }

    /**
     * 断言n1不等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1等于n2,则抛出异常
     */
    public static void isNotEq(int n1, int n2) {
        isNotEq("[断言失败] - n1不等于n2", n1, n2);
    }

    /**
     * 断言对象为{@code Enum}。
     * 
     * <pre class="code">
     * AssertUtil.isEnum(value);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象不为{@code Enum}则抛出异常
     */
    public static void isEnum(CharSequence message, Object object) {
        if (object == null || !object.getClass().isEnum()) {
            fail(message);
        }
    }

    /**
     * 断言对象为{@code Enum}。
     * 
     * <pre class="code">
     * AssertUtil.isEnum(value);
     * </pre>
     * 
     * @param object
     *            断言检查的对象
     * @throws AssertException
     *             如果对象不为{@code Enum}则抛出异常
     */
    public static void isEnum(Object object) {
        isEnum("[断言失败] - 参数对象必须为枚举(Enum)", object);
    }

    /**
     * 断言字符串不为{@code null}且长度大于0。
     * 
     * <pre class="code">
     * AssertUtil.hasLength(&quot;名称不能为null&quot;, name);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param text
     *            待检查的字符串
     * @throws AssertException
     *             如果对字符串为{@code null}或者长度等于0则抛出异常
     * @see StringUtils#isEmpty(CharSequence)
     */
    public static void hasLength(CharSequence message, CharSequence text) {
        if (StringUtils.isEmpty(text)) {
            fail(message);
        }
    }

    /**
     * 断言字符串不为{@code null}且长度大于0。
     * 
     * <pre class="code">
     * AssertUtil.hasLength(name);
     * </pre>
     * 
     * @param text
     *            待检查的字符串
     * @throws AssertException
     *             如果字符串为{@code null}或者长度等于0则抛出异常
     * @see StringUtils#isEmpty(CharSequence)
     */
    public static void hasLength(CharSequence text) {
        hasLength("[断言失败] - 参数对象必须为字符串; 且字符串不能为null且长度大于0", text);
    }

    /**
     * 断言字符串在去掉首尾空格后不为{@code null}且长度大于0。
     * 
     * <pre class="code">
     * AssertUtil.hasText(&quot;名称不能为空&quot;, name);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param text
     *            待检查的字符串
     * @throws AssertException
     *             如果字符串去掉首尾空格后为{@code null}或者长度等于0则抛出异常
     * @see StringUtils#isBlank(String)
     */
    public static void hasText(CharSequence message, CharSequence text) {
        if (StringUtils.isBlank(text)) {
            fail(message);
        }
    }

    /**
     * 断言字符串在去掉首尾空格后不为{@code null}且长度大于0。
     * 
     * <pre class="code">
     * AssertUtil.hasText(name);
     * </pre>
     * 
     * @param text
     *            待检查的字符串
     * @throws AssertException
     *             如果字符串去掉首尾空格后为{@code null}或者长度等于0则抛出异常
     * @see StringUtils#isBlank(String)
     */
    public static void hasText(CharSequence text) {
        hasText("[断言失败] - 参数对象必须为字符串; 且字符串在去掉首尾空格后不为{@code null}且长度大于0。", text);
    }

    /**
     * 断言数组拥有元素; 数组不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.notEmpty(&quot;数组必须拥有元素&quot;, array);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param array
     *            待检查的数组
     * @throws AssertException
     *             如果数组为{@code null}或者长度为0则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNotEmpty(CharSequence message, Object[] array) {
        if (ArrayUtils.isEmpty(array)) {
            fail(message);
        }
    }

    /**
     * 断言数组拥有元素; 数组不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.notEmpty(array);
     * </pre>
     * 
     * @param array
     *            待检查的数组
     * @throws AssertException
     *             如果数组为{@code null}或者长度为0则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNotEmpty(Object[] array) {
        isNotEmpty("[断言失败] - 参数对象必须为数组; 且数组必须最少拥有一个元素", array);
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于size.
     * 
     * <pre class="code">
     * AssertUtil.hasSize(&quot;Collection必须拥有size个元素&quot;, collection);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param collection
     *            待检查的Collection
     * @param size
     *            拥有元素的最小长度
     * @throws AssertException
     *             如果collection为{@code null}或者长度小于size则抛出异常
     */
    public static void hasSize(CharSequence message, Collection<?> collection, int size) {
        if (collection == null || collection.size() < size) {
            fail(message);
        }
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于size.
     * 
     * <pre class="code">
     * AssertUtil.hasSize(&quot;Collection必须拥有size个元素&quot;, collection);
     * </pre>
     * 
     * @param collection
     *            待检查的Collection
     * @param size
     *            拥有元素的最小长度
     * @throws AssertException
     *             如果collection为{@code null}或者长度小于size则抛出异常
     */
    public static void hasSize(Collection<?> collection, int size) {
        hasSize("[断言失败] - 参数对象必须为Collection; 且collection必须最少拥有" + size + "个元素", collection, size);
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于size.
     * 
     * <pre class="code">
     * AssertUtil.hasSize(&quot;Collection必须拥有size个元素&quot;, collection);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param collection
     *            待检查的Collection
     * @param size
     *            拥有元素的最小长度
     * @throws AssertException
     *             如果collection为{@code null}或者长度小于size则抛出异常
     */
    public static void hasSize(CharSequence message, Object[] collection, int size) {
        if (collection == null || collection.length < size) {
            fail(message);
        }
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于size.
     * 
     * <pre class="code">
     * AssertUtil.hasSize(&quot;Collection必须拥有size个元素&quot;, collection);
     * </pre>
     * 
     * @param collection
     *            待检查的Collection
     * @param size
     *            拥有元素的最小长度
     * @throws AssertException
     *             如果collection为{@code null}或者长度小于size则抛出异常
     */
    public static void hasSize(Object[] collection, int size) {
        hasSize("[断言失败] - 参数对象必须为Collection; 且collection必须最少拥有" + size + "个元素", collection, size);
    }

    /**
     * 断言String不能为{@code null}且必须最少拥有一个可显示字符.
     * 
     * <pre class="code">
     * AssertUtil.isNotBlank(&quot;字符串不能为空&quot;, str);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param str
     *            待检查的String
     * @throws AssertException
     *             如果str为{@code null}或者没有一个可显示的字符则抛出异常
     * @see StringUtils#isNotBlank(String)
     */
    public static void isNotBlank(CharSequence message, CharSequence str) {
        if (StringUtils.isBlank(str)) {
            fail(message);
        }
    }

    /**
     * 断言String不能为{@code null}且必须最少拥有一个可显示字符.
     * 
     * <pre class="code">
     * AssertUtil.isNotBlank(&quot;字符串不能为空&quot;, str);
     * </pre>
     * 
     * @param str
     *            待检查的String
     * @throws AssertException
     *             如果str为{@code null}或者没有一个可显示的字符则抛出异常
     * @see StringUtils#isNotBlank(String)
     */
    public static void isNotBlank(CharSequence str) {
        isNotBlank("[断言失败] - 参数对象必须为String; 且String不能为null,且必须最少拥有一个可显示字符", str);
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty(&quot;Collection必须拥有元素&quot;, collection);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param collection
     *            待检查的Collection
     * @throws AssertException
     *             如果collection为{@code null}或者长度为0则抛出异常
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void isNotEmpty(CharSequence message, Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            fail(message);
        }
    }

    /**
     * 断言Collection拥有元素; Collection不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty(collection);
     * </pre>
     * 
     * @param collection
     *            待检查的Collection
     * @throws AssertException
     *             如果collection为{@code null}或者长度为0则抛出异常
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void isNotEmpty(Collection<?> collection) {
        isNotEmpty("[断言失败] - 参数对象必须为Collection; 且collection必须最少拥有一个元素", collection);
    }

    /**
     * 断言Map拥有元素; Map不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty(&quot;Map必须拥有元素&quot;, map);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param map
     *            待检查的Map
     * @throws AssertException
     *             如果map为{@code null}或者长度为0则抛出异常
     * @see MapUtils#isEmpty(Map)
     */
    public static void isNotEmpty(CharSequence message, Map<?, ?> map) {
        if (MapUtils.isEmpty(map)) {
            fail(message);
        }
    }

    /**
     * 断言Map拥有元素; Map不能为{@code null}且长度大于0.
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty(map);
     * </pre>
     * 
     * @param map
     *            待检查的Map
     * @throws AssertException
     *             如果map为{@code null}或者长度为0则抛出异常
     * @see MapUtils#isEmpty(Map)
     */
    public static void isNotEmpty(Map<?, ?> map) {
        isNotEmpty("[断言失败] - 参数对象必须为Map; 且map必须最少拥有一个元素", map);
    }

    /**
     * 断言不定参数数组拥有元素; 不定参数数组不能为{@code null}，长度大于0且其中每个元素都不能为{@code null}，长度都大于0.<br />
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty("不定参数数组必须拥有元素，且每个参数数组都必须最少拥有一个元素", array ... array);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param array
     *            待检查的不定参数数组
     * @throws AssertException
     *             如果不定参数数组为{@code null}，长度为0或者其中一个参数数组为{@code null}或者长度为0则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNotEmpty(CharSequence message, Object[]... array) {
        if (ArrayUtils.isEmpty(array)) {
            fail(message);
        } else {
            for (Object[] arrays : array) {
                isNotEmpty(message, arrays);
            }
        }
    }

    /**
     * 断言不定参数数组拥有元素; 不定参数数组不能为{@code null}，长度大于0且其中每个元素都不能为{@code null}，长度都大于0.
     * 
     * <pre class="code">
     * AssertUtil.isNotEmpty(array ... );
     * </pre>
     * 
     * @param array
     *            待检查的不定参数数组
     * @throws AssertException
     *             如果不定参数数组为{@code null}，长度为0或者其中一个参数数组为{@code null}或者长度为0则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNotEmpty(Object[]... array) {
        isNotEmpty("[断言失败] - 不定参数对象必须为数组; 且每个参数数组必须最少拥有一个元素", array);
    }

    /**
     * 断言数组没有null元素
     * <p>
     * 注: 如果数组没有元素，则不断言！
     * 
     * <pre class="code">
     * AssertUtil.isNoNullElements(&quot;数组必须没有一个null元素&quot;, array);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param array
     *            待检查的数组
     * @throws AssertException
     *             如果数组长度大于0且有一个null元素则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNoNullElements(CharSequence message, Object[] array) {
        if (ArrayUtils.isNotEmpty(array)) {
            for (Object arr : array) {
                if (arr == null) {
                    fail(message);
                }
            }
        }
    }

    /**
     * 断言数组没有null元素
     * <p>
     * 注: 如果数组没有元素，则不断言！
     * 
     * <pre class="code">
     * AssertUtil.isNoNullElements(array);
     * </pre>
     * 
     * @param array
     *            待检查的数组
     * @throws AssertException
     *             如果数组长度大于0且有一个null元素则抛出异常
     * @see ArrayUtils#isEmpty(Object[])
     */
    public static void isNoNullElements(Object[] array) {
        isNoNullElements("[断言失败] - 参数对象必须为数组; 且数组长度必须大于0且没有一个null元素", array);
    }

    /**
     * 断言Collection没有null元素
     * <p>
     * 注: 如果Collection没有元素，则不断言！
     * 
     * <pre class="code">
     * AssertUtil.isNoNullElements(&quot;数组必须没有一个null元素&quot;, collections);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息
     * @param array
     *            待检查的Collection
     * @throws AssertException
     *             如果Collection长度大于0且有一个null元素则抛出异常
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void isNoNullElements(CharSequence message, Collection<?> collections) {
        if (CollectionUtils.isNotEmpty(collections)) {
            for (Object collection : collections) {
                if (collection == null) {
                    fail(message);
                }
            }
        }
    }

    /**
     * 断言Collection没有null元素
     * <p>
     * 注: 如果Collection没有元素，则不断言！
     * 
     * <pre class="code">
     * AssertUtil.isNoNullElements(collections);
     * </pre>
     * 
     * @param array
     *            待检查的Collection
     * @throws AssertException
     *             如果Collection长度大于0且有一个null元素则抛出异常
     * @see CollectionUtils#isEmpty(Collection)
     */
    public static void isNoNullElements(Collection<?> collections) {
        isNoNullElements("[断言失败] - 参数对象必须为Collection; 且Collection长度必须大于0且没有一个null元素", collections);
    }

    /**
     * 断言提供对象obj是提供的对象类型clazz
     * 
     * <pre class="code">
     * AssertUtil.instanceOf(&quot;foo对象是Foo类型的实体&quot;, Foo.class, foo);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。通常来说会自动在消息后面加上
     *            "对象obj的类型[obj.getClass().getName()]必须是[type.getName()]"的提示信息
     * @param obj
     *            需要比较的对象
     * @param clazz
     *            被比较的对象类型
     * @throws AssertException
     *             如果对象obj不是对象类型clazz则抛出异常
     * @see Class#isInstance(Object)
     */
    public static void isInstanceOf(CharSequence message, Object obj, Class<?> clazz) {
        isNotNull("对象类型type不能为null", clazz);
        if (!clazz.isInstance(obj)) {
            fail((StringUtils.isNotBlank(message) ? message + " " : "") + "对象obj的类型 [" + (obj != null ? obj.getClass().getName() : "null") + "] 必须是 [" + clazz.getName() + "]");
        }
    }

    /**
     * 断言提供对象obj是提供的对象类型clazz
     * 
     * <pre class="code">
     * AssertUtil.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param obj
     *            需要比较的对象
     * @param clazz
     *            被比较的对象类型
     * @throws AssertException
     *             如果对象obj不是对象类型clazz则抛出异常
     * @see Class#isInstance(Object)
     */
    public static void isInstanceOf(Object obj, Class<?> clazz) {
        isInstanceOf(null, obj, clazz);
    }

    /**
     * 断言提供对象obj是提供的对象类型clazzs其中一个
     * 
     * <pre class="code">
     * AssertUtil.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param obj
     *            需要比较的对象
     * @param clazzs
     *            被比较的对象类型
     * @throws AssertException
     *             如果对象obj不是对象类型clazz则抛出异常
     * @see Class#isInstance(Object)
     */
    public static void isInstanceOf(CharSequence message, Object obj, Class<?>... clazzs) {
        if (ArrayUtils.isEmpty(clazzs)) {
            fail(message);
        } else {
            for (Class<?> clazz : clazzs) {
                if (clazz.isInstance(obj)) {
                    return;
                }
            }
            fail((StringUtils.isNotBlank(message) ? message + " " : "") + "对象obj的类型 [" + (obj != null ? obj.getClass().getName() : "null") + "] 必须是 [" + clazzs + "]中的一个类型");
        }
    }

    /**
     * 断言提供对象obj是提供的对象类型clazzs其中一个
     * 
     * <pre class="code">
     * AssertUtil.instanceOf(Foo.class, foo);
     * </pre>
     * 
     * @param objs
     *            需要比较的对象
     * @param clazzs
     *            被比较的对象类型
     * @throws AssertException
     *             如果对象obj不是对象类型clazz则抛出异常
     * @see Class#isInstance(Object)
     */
    public static void isInstanceOfs(CharSequence message, List<?> objs, Class<?>... clazzs) {
        for (Object obj : objs) {
            isInstanceOf(message, obj, clazzs);
        }
    }

    /**
     * 断言{@code superType.isAssignableFrom(subType)}返回{@code true}.
     * 
     * <pre class="code">
     * AssertUtil.isAssignable(Number.class, myClass);
     * </pre>
     * 
     * @param superType
     *            超类或者接口
     * @param subType
     *            子类或者实现
     * @throws AssertException
     *             如果类型subType不是类型superType的子类或者实现则抛出异常
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(null, superType, subType);
    }

    /**
     * 断言{@code superType.isAssignableFrom(subType)}返回{@code true}.
     * 
     * <pre class="code">
     * AssertUtil.isAssignable(&quot;Number必须是myClass的超类或者接口&quot;, Number.class, myClass);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。通常来说会自动在消息后面加上"类型subType不是类型superType的子类或者实现"的提示信息
     * @param superType
     *            超类或者接口
     * @param subType
     *            子类或者实现
     * @throws AssertException
     *             如果类型subType不是类型superType的子类或者实现则抛出异常
     */
    public static void isAssignable(CharSequence message, Class<?> superType, Class<?> subType) {
        isNotNull("对象类型superType不能为空", superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            fail((StringUtils.isNotBlank(message) ? message + " " : "") + "对象类型subType[" + subType + "]必须是对象类型superType[" + superType + "]的子类或者实现");
        }
    }

    /**
     * 断言n1大于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1小于或者等于n2,则抛出异常
     */
    public static void isGt(CharSequence message, int n1, int n2) {
        if (n1 <= n2) {
            fail(message);
        }
    }

    /**
     * 断言n1大于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGt(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1小于或者等于n2,则抛出异常
     */
    public static void isGt(int n1, int n2) {
        isGt("[断言失败] - n1 必须大于n2", n1, n2);
    }

    /**
     * 断言n1小于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isLt(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1大于或者等于n2,则抛出异常
     */
    public static void isLt(CharSequence message, int n1, int n2) {
        if (n1 >= n2) {
            fail(message);
        }
    }

    /**
     * 断言n1小于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isLt(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1大于或者等于n2,则抛出异常
     */
    public static void isLt(int n1, int n2) {
        isLt("[断言失败] - n1必须小于n2", n1, n2);
    }

    /**
     * 断言n1大于等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGte(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1小于n2,则抛出异常
     */
    public static void isGte(CharSequence message, int n1, int n2) {
        if (n1 < n2) {
            fail(message);
        }
    }

    /**
     * 断言n1大于等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isGte(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1小于n2,则抛出异常
     */
    public static void isGte(int n1, int n2) {
        isGte("[断言失败] - n1 必须大于等于n2", n1, n2);
    }

    /**
     * 断言n1小于等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isLte(integer, integer2);
     * </pre>
     * 
     * @param message
     *            断言失败的提示信息。
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1大于n2,则抛出异常
     */
    public static void isLte(CharSequence message, int n1, int n2) {
        if (n1 > n2) {
            fail(message);
        }
    }

    /**
     * 断言n1小于等于n2
     * <p>
     * 
     * <pre class="code">
     * AssertUtil.isLte(integer, integer2);
     * </pre>
     * 
     * @param n1
     * @param n2
     * @throws AssertException
     *             如果n1大于n2,则抛出异常
     */
    public static void isLte(int n1, int n2) {
        isLte("[断言失败] - n1必须小于等于n2", n1, n2);
    }

    /**
     * 断言状态为true
     * 
     * @param message
     *            断言失败的提示信息。
     * @param state
     *            状态
     * @throws AssertException
     *             如果state为false,则抛出异常
     */
    public static void state(CharSequence message, boolean state) {
        if (!state) {
            fail(message);
        }
    }

    /**
     * 断言状态为true
     * 
     * @param state
     *            状态
     * @throws AssertException
     *             如果state为false,则抛出异常
     */
    public static void state(boolean state) {
        if (!state) {
            fail("[断言失败] - expression必须为true");
        }
    }

    /**
     * 
     * 抛出一个拥有错误信息的{@link AssertException}异常
     * 
     * @param message
     *            如果断言失败的提示信息
     * @throws AssertException
     *             如果对象不是{@code null}则抛出异常
     */
    private static void fail(CharSequence message) {
        throw new AssertException(message == null ? "" : message.toString());
    }
}
