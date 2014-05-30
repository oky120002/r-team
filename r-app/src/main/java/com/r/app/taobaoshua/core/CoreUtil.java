/**
 * 
 */
package com.r.app.taobaoshua.core;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author oky
 * 
 */
public class CoreUtil {

	/** 获取淘宝刷图标 */
	public static Image getDefaultIco() throws IOException {
		String imagepath = "com/r/app/taobaoshua/image/tray.png";
		InputStream ins = ClassLoader.getSystemResourceAsStream(imagepath);
		return ImageIO.read(ins);
	}
}
