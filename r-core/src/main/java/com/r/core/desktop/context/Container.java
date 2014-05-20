/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-5
 * <修改描述:>
 */
package com.r.core.desktop.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.r.core.exceptions.SException;
import com.r.core.util.ClassScanUtil;

/**
 * 容器
 * 
 * @author rain
 * @version [1.0, 2013-3-5]
 */
public class Container {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(Container.class);

	/** 保存的Bean */
	private Map<String, Bean> strBeans = new HashMap<String, Bean>();

	/** 保存Bean.class.name()和BeanId的映射关系 */
	private Map<String, String> classToBeanId = new HashMap<String, String>();

	/** 扫描的基础包 */
	private String packageName = null;

	/** 默认构造函数 */
	public Container(String packageName) {
		super();
		this.packageName = packageName;
		traversalBeans();
		beansMemberVarAssignment();
		initBeans();
	}

	/** 获取Bean */
	public Bean getBean(Class<?> cls) {
		if (cls == null) {
			throw new NullPointerException("不能传入空的Bean类型");
		}
		Beans beans = cls.getAnnotation(Beans.class);
		if (StringUtils.isBlank(beans.value())) {
			return getBean(cls.getName());
		} else {
			return getBean(beans.value());
		}

	}

	/** 获取Bean */
	public Bean getBean(String id) {
		return strBeans.get(id);
	}

	/** 遍历且实例化Beans */
	private void traversalBeans() {
		logger.info("----------遍历且实例化Beans------------");
		Set<Class<?>> allClassesByAnnotation = ClassScanUtil.getAllClassesByAnnotation(packageName, Beans.class);
		for (Class<?> cls : allClassesByAnnotation) {
			Beans annotation = cls.getAnnotation(Beans.class);
			String key = "";
			if (StringUtils.isBlank(annotation.value())) {
				key = cls.getName();
			} else {
				key = annotation.value();
			}
			Bean bean = null;
			try {
				bean = (Bean) cls.newInstance();
				logger.info("----找到Bean: " + key + "[" + bean.getClass().getName() + "]");
				classToBeanId.put(cls.getName(), key);
				strBeans.put(key, bean);
			} catch (Exception e) {
				logger.debug("找到类" + cls.getName() + ",但是没有实现" + Bean.class.getName() + "接口,舍去!");
			}
		}
	}

	/** 扫描拥有Beans成员的类对应的Beans赋值 */
	private void beansMemberVarAssignment() {
		logger.info("----------扫描拥有Beans成员的类对应的Beans赋值------------");
		for (Map.Entry<String, Bean> entry : strBeans.entrySet()) {
			Bean value = entry.getValue();
			Field[] declaredFields = value.getClass().getDeclaredFields(); // 获得所有类属性(包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段)
			for (Field field : declaredFields) {
				Beans annotation = field.getAnnotation(Beans.class); // 获得此字段上的Beans注解
				if (annotation != null) { // 此字段是Beans
					logger.info("------给类[" + value.getClass().getName() + "]的Beans变量[" + field.getName() + "]赋值----------");
					String beanId = annotation.value();
					if (StringUtils.isEmpty(beanId)) {
						beanId = classToBeanId.get(field.getType().getName());
					}
					Bean bean = strBeans.get(beanId);
					field.setAccessible(true);
					try {
						if (field.get(value) == null) {
							field.set(value, bean);
						}
					} catch (IllegalArgumentException e) {
						throw new SException("参数错误", e);
					} catch (IllegalAccessException e) {
						throw new SException("不能为Bean[" + bean.getClass().getName() + "]的[" + field.getName() + "]属性赋值<error>" + e.toString() + "</error>", e);
					}
					field.setAccessible(false);
				}
			}
		}
	}

	/** 初始化Beans */
	private void initBeans() {
		logger.info("----------初始化Beans------------");
		for (Map.Entry<String, Bean> entry : strBeans.entrySet()) {
			entry.getValue().init();
		}
	}
}
