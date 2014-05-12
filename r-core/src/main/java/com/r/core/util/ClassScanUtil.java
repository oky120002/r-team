/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-20
 * <修改描述:>
 */
package com.r.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 扫描指定包（包括jar）下的class文件 <br>
 * 
 * <a href="http://sjsky.iteye.com">http://sjsky.iteye.com</a>
 * 
 * @author michael
 */

public abstract class ClassScanUtil {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ClassScanUtil.class);

	/** 是否排除内部类 true->是 false->否 */
	private static boolean excludeInnerClass = true;

	/** 过滤规则适用情况 true—>搜索符合规则的 false->排除符合规则的 */
	private static boolean checkInOrEx = true;

	/** 过滤规则列表 如果是null或者空，即全部符合不过滤 */
	private static List<String> classFilters = null;

	/**
	 * 
	 * <通过扫描包和文件夹来获得类>
	 * 
	 * @param basePackage
	 *            基础包
	 * @param recursive
	 *            是否递归搜索子包
	 * @param excludeInnerClass
	 *            是否排除内部类: true->是 false->否
	 * @param checkInOrEx
	 *            过滤规则使用情况:true—>搜索符合规则的 false->排除符合规则的
	 * @param classFilters过滤规则列表
	 *            如果是null或者空，即全部符合不过滤
	 * 
	 * @return Set<Class<?>> [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @author rain
	 * @see [类、类#方法、类#成员]
	 */
	public static Set<Class<?>> getAllClasses(String basePackage, boolean recursive, boolean excludeInnerClass, boolean checkInOrEx, List<String> classFilters) {
		ClassScanUtil.excludeInnerClass = excludeInnerClass;
		ClassScanUtil.checkInOrEx = checkInOrEx;
		ClassScanUtil.classFilters = classFilters;

		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageName = basePackage;
		if (packageName.endsWith(".")) {
			packageName = packageName.substring(0, packageName.lastIndexOf('.'));
		}
		String package2Path = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(package2Path);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					logger.info("扫描路径:" + basePackage + "下file类型的class文件....");
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					doScanClassesByFile(classes, packageName, recursive, filePath);
				} else if ("jar".equals(protocol)) {
					logger.info("扫描路径:" + basePackage + "下jar文件中的类....");
					doScanClassesByJar(classes, packageName, recursive, url);
				}
			}
		} catch (IOException e) {
			logger.error("IOException error:", e);
		}
		return classes;
	}

	/**
	 * 
	 * <通过扫描包和文件夹来获得"标注于某个注解"的类> <br/>
	 * 
	 * @param basePackage
	 *            基础包
	 * @param clazz
	 *            注解类型的类,不能为空
	 * 
	 * @return Set<Class<T>> [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @author rain
	 * @see ClassScanUtil#getAllClassesByClass(String, Class, boolean, boolean,
	 *      boolean, List)
	 */
	public static Set<Class<?>> getAllClassesByAnnotation(String basePackage, Class<?> clazz) {
		return getAllClassesByAnnotation(basePackage, clazz, true, false, true, null);
	}

	/**
	 * 
	 * <通过扫描包和文件夹来获得"标注于某个注解"的类>
	 * 
	 * @param basePackage
	 *            基础包
	 * @param clazz
	 *            注解类型的类,不能为空
	 * @param recursive
	 *            是否递归搜索子包
	 * @param excludeInnerClass
	 *            是否排除内部类: true->是 false->否
	 * @param checkInOrEx
	 *            过滤规则使用情况:true—>搜索符合规则的 false->排除符合规则的
	 * @param classFilters过滤规则列表
	 *            如果是null或者空，即全部符合不过滤
	 * @return [参数说明]
	 * 
	 * @return Set<Class<?>> [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @author rain
	 * @see [类、类#方法、类#成员]
	 */
	public static Set<Class<?>> getAllClassesByAnnotation(String basePackage, Class<?> clazz, boolean recursive, boolean excludeInnerClass, boolean checkInOrEx, List<String> classFilters) {
		if (StringUtils.isBlank(basePackage)) {
			logger.error("不能传入空字符串的[basePackage]");
			throw new NullPointerException("不能传入空字符串[basePackage]");
		}

		if (clazz == null) {
			throw new NullPointerException("不能传入空类[clazz]");
		}

		if (!clazz.isAnnotation()) {
			throw new IllegalArgumentException("传入的[clazz]必须是注解类型");
		}

		Set<Class<?>> allClasses = getAllClasses(basePackage, recursive, excludeInnerClass, checkInOrEx, classFilters);
		if (CollectionUtils.isEmpty(allClasses)) {
			return new LinkedHashSet<Class<?>>();
		}

		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		for (Class<?> allClass : allClasses) {
			// 判断 注解
			Annotation[] annotations = allClass.getAnnotations();
			if (annotations == null || annotations.length == 0) {
				continue;
			}
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(clazz)) {
					classes.add(allClass);
					continue;
				}
			}
		}
		return classes;
	}

	/**
	 * 
	 * <通过扫描包和文件夹来获得"继承某个类"或者"实现某个接口"的类> <br/>
	 * 
	 * @param basePackage
	 *            基础包
	 * @param clazz
	 *            查找的类.如果为null则搜索所有的类
	 * 
	 * @return Set<Class<T>> [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @author rain
	 * @see ClassScanUtil#getAllClassesByClass(String, Class, boolean, boolean,
	 *      boolean, List)
	 */
	public static <T> Set<Class<T>> getAllClassesByClass(String basePackage, Class<T> clazz) {
		return getAllClassesByClass(basePackage, clazz, true, false, true, null);
	}
	
	/**
	 * 
	 * <通过扫描包和文件夹来获得"继承某个类"或者"实现某个接口"或者"标注于某个注解"的类>
	 * 
	 * @param basePackage
	 *            基础包
	 * @param clazz
	 *            查找的类.如果为null则搜索所有的类
	 * @param recursive
	 *            是否递归搜索子包
	 * @param excludeInnerClass
	 *            是否排除内部类: true->是 false->否
	 * @param checkInOrEx
	 *            过滤规则使用情况:true—>搜索符合规则的 false->排除符合规则的
	 * @param classFilters过滤规则列表
	 *            如果是null或者空，即全部符合不过滤
	 * @return [参数说明]
	 * 
	 * @return Set<Class<?>> [返回类型说明]
	 * @exception throws [异常类型] [异常说明]
	 * @author rain
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> Set<Class<T>> getAllClassesByClass(String basePackage, Class<T> clazz, boolean recursive, boolean excludeInnerClass, boolean checkInOrEx, List<String> classFilters) {
		if (StringUtils.isBlank(basePackage)) {
			logger.error("不能传入空字符串的[basePackage]");
			throw new NullPointerException("不能传入空字符串[basePackage]");
		}

		Set<Class<?>> allClasses = getAllClasses(basePackage, recursive, excludeInnerClass, checkInOrEx, classFilters);
		if (CollectionUtils.isEmpty(allClasses)) {
			return new LinkedHashSet<Class<T>>();
		}

		Set<Class<T>> classes = new LinkedHashSet<Class<T>>();
		for (Class<?> allClass : allClasses) {
			if (clazz == null) {
				// 扫描所有类
				classes.add((Class<T>) allClass);
			} else if (clazz.isInterface()) {
				// 判断接口
				List<Class<?>> allClazzes = ClassUtils.getAllInterfaces(allClass);
				if (CollectionUtils.isEmpty(allClazzes)) {
					continue;
				}

				for (Class<?> allClazz : allClazzes) {
					if (allClazz.equals(clazz)) {
						classes.add((Class<T>) allClass);
						continue;
					}
				}
				// } else if (clazz.isAnnotation()) {
				// // 判断 注解
				// Annotation[] annotations = allClass.getAnnotations();
				// if (annotations == null || annotations.length == 0) {
				// continue;
				// }
				// for (Annotation annotation : annotations) {
				// if (annotation.equals(clazz)) {
				// classes.add((Class<T>) allClass);
				// continue;
				// }
				// }
			} else {
				// 判断 超类
				List<Class<?>> allClazzes = ClassUtils.getAllSuperclasses(allClass);
				if (CollectionUtils.isEmpty(allClazzes)) {
					continue;
				}

				for (Class<?> allClazz : allClazzes) {
					if (allClazz.equals(clazz)) {
						classes.add((Class<T>) allClass);
						continue;
					}
				}
			}
		}
		return classes;
	}

	/**
	 * 
	 * 以jar的方式扫描包下的所有Class文件<br>
	 * 
	 * @param basePackage
	 *            eg：michael.utils.
	 * 
	 * @param url
	 * 
	 * @param recursive
	 * 
	 * @param classes
	 */

	private static void doScanClassesByJar(Set<Class<?>> classes, String basePackage, boolean recursive, URL url) {
		String package2Path = basePackage.replace('.', '/');
		JarFile jar;
		try {
			jar = ((JarURLConnection) url.openConnection()).getJarFile();
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				if (!name.startsWith(package2Path) || entry.isDirectory()) {
					continue;
				}

				// 判断是否递归搜索子包
				if (!recursive && name.lastIndexOf('/') != package2Path.length()) {
					continue;
				}

				// 判断是否过滤 inner class
				if (ClassScanUtil.excludeInnerClass && name.indexOf('$') != -1) {
					logger.info("exclude inner class with name:" + name);
					continue;
				}

				String classSimpleName = name.substring(name.lastIndexOf('/') + 1);
				// 判定是否符合过滤条件
				if (ClassScanUtil.filterClassName(classSimpleName)) {
					String className = name.replace('/', '.');
					className = className.substring(0, className.length() - 6);
					try {
						classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
					} catch (ClassNotFoundException e) {
						logger.error("Class.forName error:", e);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException error:", e);
		}
	}

	/**
	 * 
	 * 以文件的方式扫描包下的所有Class文件
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void doScanClassesByFile(Set<Class<?>> classes, String packageName, boolean recursive, String packagePath) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		final boolean fileRecursive = recursive;
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义文件过滤规则
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return fileRecursive;
				}
				String filename = file.getName();
				if (excludeInnerClass && filename.indexOf('$') != -1) {
					logger.info("exclude inner class with name:" + filename);
					return false;
				}
				return filterClassName(filename);
			}
		});

		for (File file : dirfiles) {
			if (file.isDirectory()) {
				doScanClassesByFile(classes, packageName + "." + file.getName(), recursive, file.getAbsolutePath());
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					logger.error("IOException error:", e);
				}
			}
		}
	}

	/**
	 * 
	 * 根据过滤规则判断类名
	 * 
	 * @param className
	 * @return
	 */
	private static boolean filterClassName(String className) {
		if (!className.endsWith(".class")) {
			return false;
		}
		if (null == ClassScanUtil.classFilters || ClassScanUtil.classFilters.isEmpty()) {
			return true;
		}

		String tmpName = className.substring(0, className.length() - 6);
		boolean flag = false;
		for (String str : classFilters) {
			String tmpreg = "^" + str.replace("*", ".*") + "$";
			Pattern p = Pattern.compile(tmpreg);
			if (p.matcher(tmpName).find()) {
				flag = true;
				break;
			}
		}
		return (checkInOrEx && flag) || (!checkInOrEx && !flag);
	}
}