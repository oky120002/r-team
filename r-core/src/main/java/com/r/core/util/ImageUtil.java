package com.r.core.util;

import java.awt.Image;
import java.awt.geom.IllegalPathStateException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.r.core.exceptions.initialization.InitializationErrorException;
import com.r.core.exceptions.io.IOReadErrorException;

/**
 * 图片的相关操作<br/>
 * 此类自带图片缓存,可是满足绝大多数使用情况<br/>
 * 要使用ImageUtil的图片功能,则必须先实现此接口,给ImageUtil初始化
 * 
 * @author rain
 * 
 */
public abstract class ImageUtil {
	/** 图片缓存 */
	private static Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();

	/** 图片读取接口 */
	private static Map<String, ReadImage> readImages = new HashMap<String, ReadImage>();

	/** 初始化ImageUtil的图片读取功能 */
	public static void init(String key, ReadImage readImage) {
		ImageUtil.readImages.put(key, readImage);
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
	public static BufferedImage getImage(String key, String imageKey) {
		return ImageUtil.getImage(key, imageKey, ImageReadMode.Read);
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
	public static BufferedImage getImage(String key, String imageKey, ImageReadMode imageReadModel) {
		String imageMapKey = key + "." + imageKey;
		if (imageKey == null) {
			throw new NullPointerException("传入的imageKey不能为空");
		}
		if (!ImageUtil.readImages.containsKey(key)) {
			throw new InitializationErrorException("请调用ImageUtil.init(String, ReadImage)方法初始化ImageUtil的图片读取功能");
		}
		if (imageReadModel == null) {
			imageReadModel = ImageReadMode.Read;
		}

		ReadImage readImage = ImageUtil.readImages.get(key);
		BufferedImage image = null;
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

	/**
	 * 保存图片到文件
	 * 
	 * @param img
	 *            图片Buffer
	 * @param file
	 *            文件,如果文件不存在则创建,如果存在则删除后在创建
	 * @throws IOException
	 */
	public static void saveImageToFile(BufferedImage img, File file) throws IOException {
		AssertUtil.isNotNull("不能传入空的图片Buffer", img);
		AssertUtil.isNotNull("不能传入空的目标文件", file);

		String name = file.getName();
		String[] split = name.split("\\.");
		if (split != null && split.length > 1) {
			ImageIO.write(img, split[1], file);
		} else {
			ImageIO.write(img, "", file);
		}
	}

	/**
	 * 读取图片
	 * 
	 * @param file
	 *            读取的文件
	 * @return 返回读取的图片
	 * 
	 * @throws IOException
	 *             读取图片失败时抛出此异常
	 * @see javax.imageio.ImageIO#read()
	 */
	public static BufferedImage readImageFromFile(File file) throws IOException {
		// 如果读取动态gif图片不动,可以尝试使用Toolkit.getDefaultToolkit().getImage(String);方法来读取
		if (file == null) {
			throw new NullPointerException("不能传入空的目标文件");
		}
		return ImageIO.read(file);
	}

	/**
	 * 读取图片<br />
	 * 从架包中读取图片
	 * 
	 * @param path
	 *            架包路径
	 * @return 返回读取的图片
	 * @throws IOException
	 *             读取图片失败时抛出此异常
	 * @see javax.imageio.ImageIO#read()
	 */
	public static Image readImageFromIO(String path) {
		try {
			return ImageIO.read(ClassLoader.getSystemResourceAsStream(path));
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 从图片获取图标
	 * 
	 * @param image
	 *            图片
	 * @return 返回图标
	 * @see javax.swing.ImageIcon.ImageIcon
	 */
	public static Icon readIconFromImage(BufferedImage image) {
		return new ImageIcon(image);
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
		public BufferedImage readImage(String imageKey) throws IOReadErrorException;
	}

	/**
	 * 合并两张图片
	 * 
	 * @param src
	 *            原始图片
	 * @param dst
	 *            要合并的图片
	 * @param x
	 *            以左上角开始算 横坐标
	 * @param y
	 *            以左上角开始算 纵坐标
	 */
	public static void merge(Image src, Image dst, int x, int y) {
		src.getGraphics().drawImage(dst, x, y, null);
	}
}
