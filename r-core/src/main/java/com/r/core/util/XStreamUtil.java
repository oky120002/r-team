/**
 * 描          述:  <核心模块包>
 * 修  改   人:  rain
 * 修改时间:  2012-10-26
 * <修改描述:>
 */
package com.r.core.util;

import java.util.Map;
import java.util.WeakHashMap;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3DomDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * XML Object 转换实用工具<br/>
 * 封装Xstream功能,用以处理xml与bean的转换 这里解决了2个重大问题:<br \>
 * 1、xstream有bug，在转换过程中，会将 定义别名中的下划线"_"转换为xml后会变成“” <br />
 * 2、在xml生成对象时，xml有多出来的元素时，对象生成将会抛出异常。
 * 
 * @author rain
 * @version [1.0, 2012-10-26]
 */
public final class XStreamUtil {
    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(XStreamUtil.class, "XML解析构成");

    /** XStream集合 */
    private static Map<Class<?>, XStream> xstreamMap = new WeakHashMap<Class<?>, XStream>();

    /**
     * 转换过程中特殊字符转码
     */
    private static NameCoder nameCoder = new NameCoder() {

        @Override
        public String encodeNode(String name) {
            return name;
        }

        @Override
        public String encodeAttribute(String name) {
            return name;
        }

        @Override
        public String decodeNode(String nodeName) {
            return nodeName;
        }

        @Override
        public String decodeAttribute(String attributeName) {
            return attributeName;
        }

    };

    /**
     * 在xml中多余的节点生成bean时会抛出异常 通过该mapperWrapper跳过不存在的属性
     * 
     * @param mapper
     * @return [参数说明]
     * 
     * @return MapperWrapper [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static MapperWrapper createSkipOverElementMapperWrapper(Mapper mapper) {
        MapperWrapper resMapper = new MapperWrapper(mapper) {
            @SuppressWarnings("rawtypes")
            @Override
            public Class realClass(String elementName) {
                Class res = null;
                try {
                    res = super.realClass(elementName);
                } catch (CannotResolveClassException e) {
                    logger.warn("xstream change xml to object. filed {} not exsit. ", elementName);
                }
                return res;
            }

            /** 1.4.2版本不需要覆盖此方法 */
            @SuppressWarnings("rawtypes")
            @Override
            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                try {
                    if (definedIn.getDeclaredField(fieldName) == null) {
                        return false;
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    logger.warn("xstream change xml to object. filed {} not exsit. ", fieldName);
                }
                return false;
            }
        };

        return resMapper;
    }

    /**
     * 获取xstream转换对象
     * 
     * @param classType
     * @return [参数说明]
     * 
     * @return XStream [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType) {
        return getXstream(classType, true);
    }

    /**
     * 获取xstream转换对象
     * 
     * @param classType
     *            转换对象
     * @param isSkipOverElement
     *            是否跳过元素
     * @return [参数说明]
     * 
     * @return XStream [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static XStream getXstream(Class<?> classType, boolean isSkipOverElement) {
        if (xstreamMap.containsKey(classType)) {
            return xstreamMap.get(classType);
        }

        XStream res = null;
        if (isSkipOverElement) {
            res = new XStream(new Xpp3DomDriver(nameCoder)) {
                /**
                 * @param next
                 * @return
                 */
                protected MapperWrapper wrapMapper(MapperWrapper next) {
                    return createSkipOverElementMapperWrapper(next);
                }

            };
        } else {
            res = new XStream(new Xpp3DomDriver(nameCoder));
        }

        logger.warn("create xstream by [{}] , parameter [{}]", new Object[] { classType.getName(), isSkipOverElement });

        res.processAnnotations(classType);

        xstreamMap.put(classType, res);
        return res;
    }

    /**
     * 
     * 转换对象为xml字符串
     * 
     * @param obj
     *            转换的对象
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    public static String toXml(Object obj) {
        return getXstream(obj.getClass()).toXML(obj);
    }

    /**
     * 
     * 转换xml字符串为对象
     * 
     * @param classType
     *            转换的对象类型
     * @param xml
     *            被转换的字符串
     * @return [参数说明]
     * 
     * @return T [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @author rain
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(Class<T> classType, String xml) {
        if (classType == null) {
            throw new RuntimeException("转换的对象类型不能为空！");
        }

        T t = null;
        try {
            t = classType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("实例化" + classType.getName() + "失败！");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("实例化" + classType.getName() + "时，抵用构造函数失败！");
        }

        return (T) getXstream(classType).fromXML(xml, t);
    }
}
