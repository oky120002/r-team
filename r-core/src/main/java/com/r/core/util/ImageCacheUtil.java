/**
 * 
 */
package com.r.core.util;

import java.awt.Image;
import java.awt.geom.IllegalPathStateException;
import java.util.HashMap;
import java.util.Map;

import com.r.core.exceptions.initialization.InitializationErrorException;
import com.r.core.exceptions.io.IOReadErrorException;

/**
 * 图片缓存实用工具 <br />
 * 自带图片缓存,可是满足绝大多数使用情况<br/>
 * ImageCacheUtil工具类的"get"方法,必须在实现"ReadImage"接口的情况下才能使用,给ImageUtil初始化
 * 
 * @author oky
 * 
 */
public class ImageCacheUtil {

	/** 图片缓存 */
	private static Map<String, Image> imageMap = new HashMap<String, Image>();

	/** 图片读取接口 */
	private static Map<String, ReadImage> readImages = new HashMap<String, ReadImage>();

	/** 初始化ImageUtil的图片读取功能 */
	public static void init(String key, ReadImage readImage) {
		ImageCacheUtil.readImages.put(key, readImage);
	}

	/**
	 * 根据接口ReadImage提供的方法获得图片<br/>
	 * 以ImageReadMode.Read模式读取图片<br/>
	 * 如果找不到则报错
	 * 
	 * @param key
	 *            图片读取接口
	 * @param imageKey
	 *            根据传入的imageKey来读取图片,imageKey可以是图片全路径,也可单纯的是图片名称,也可以是自定义的字符串
	 *            如果为空,则报错
	 * @return 图片
	 */
	public static Image getImage(String key, String imageKey) {
		return ImageCacheUtil.getImage(key, imageKey, ImageReadMode.Read);
	}

	/**
	 * 根据接口ReadImage提供的方法获得图片<br>
	 * 如果找不到则报错
	 * 
	 * @param key
	 *            图片读取接口
	 * @param imageKey
	 *            根据传入的imageKey来读取图片,imageKey可以是图片全路径,也可单纯的是图片名称,也可以是自定义的字符串
	 *            如果为空,则报错
	 * @param imageReadModel
	 *            图片的读取模式,如果为空,则取默认读取模式ImageReadMode.Read
	 * @return
	 */
	public static Image getImage(String key, String imageKey, ImageReadMode imageReadModel) {
		String imageMapKey = key + "." + imageKey;
		if (imageKey == null) {
			throw new NullPointerException("传入的imageKey不能为空");
		}
		if (!ImageCacheUtil.readImages.containsKey(key)) {
			throw new InitializationErrorException("请调用ImageCacheUtil.init(String, ReadImage)方法初始化ImageUtil的图片读取功能");
		}
		if (imageReadModel == null) {
			imageReadModel = ImageReadMode.Read;
		}

		ReadImage readImage = ImageCacheUtil.readImages.get(key);
		Image image = null;
		switch (imageReadModel) {
		case Read:
			image = imageMap.get(imageMapKey);
			if (image == null) {
				try {
					image = readImage.readImage(imageKey);
				} catch (Exception e) {
					throw new IOReadErrorException("读取图片[" + imageKey + "]失败");
				}
				imageMap.put(imageMapKey, image);
			}
			return image;
		case ReadFromFile:
			try {
				return readImage.readImage(imageKey);
			} catch (Exception e) {
				throw new IOReadErrorException("读取图片[" + imageKey + "]失败");
			}
		case ReadFromFileAndSaveToCache:
			try {
				image = readImage.readImage(imageKey);
			} catch (Exception e) {
				throw new IOReadErrorException("读取图片[" + imageKey + "]失败");
			}
			imageMap.put(imageMapKey, image);
			return image;
		}
		throw new IllegalPathStateException("找不到对应的图片读取方式");
	}

	/** 读取图片模式 */
	public enum ImageReadMode {
		/** 先从缓存读取,如果缓存没有则从文件系统读取,然后把文件放入缓存等待下次读取 */
		Read,
		/** 跳过缓存,直接从文件系统读取,读出的图片不放入缓存 */
		ReadFromFile,
		/** 跳过缓存,直接从文件系统读取,读出的图片放入图片缓存等待下次读取 */
		ReadFromFileAndSaveToCache,
	}

	public static interface ReadImage {
		/** 根据传入的imageKey来读取图片,imageKey可以是图片全路径,也可单纯的是图片名称,也可以是自定义的字符串 */
		public Image readImage(String imageKey) throws IOReadErrorException;
	}

}
