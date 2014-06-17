package com.r.core.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 图片的相关操作<br/>
 * 
 * @author rain
 * 
 */
public abstract class ImageUtil {

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
		} catch (Exception e) {
			e.printStackTrace();
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
	public static Icon readIconFromImage(Image image) {
		return new ImageIcon(image);
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
