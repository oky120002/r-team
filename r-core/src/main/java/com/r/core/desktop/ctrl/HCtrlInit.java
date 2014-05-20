/**
 * 描          述:  <描述>
 * 修  改   人:  bd02
 * 修改时间:  2013-3-11
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * <功能简述> <功能详细描述>
 * 
 * @author bd02
 * @version [版本号, 2013-3-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class HCtrlInit {

	/**
	 * 初始化控件初始属性
	 * 
	 * @see com.hiyouyu.core.controls.ctrl.HCtrlInit#initNoSpot()
	 * @see com.hiyouyu.core.controls.ctrl.HCtrlInit#initUIFont()
	 * @see com.hiyouyu.core.controls.ctrl.HCtrlInit#initWindowsStyleByWindows()
	 */
	public static void init() {
		HCtrlInit.initNoSpot();
		HCtrlInit.initUIFont(null);
		HCtrlInit.initWindowsStyleByWindows(null);
	}

	/** 禁用JTextField的浮动窗口出现 */
	public static void initNoSpot() {
		System.setProperty("javax.swing.text.Style", "no-spot"); // 禁用JTextField的浮动窗口出现
	}

	/** 统一设置所有控件的字体(请在窗口显示前设置),如果为null则默认为"宋体,普通样式,12大小" */
	public static void initUIFont(Font font) {
		if (font == null) {
			font = new Font("宋体", Font.PLAIN, 12);
		}
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	/** 设置窗体样式为winodws样式 */
	public static void initWindowsStyleByWindows(Component c) {
		// 设置窗体样式为winodws样式
		try {
			// 这里直接使用类路径字符串,而不是用com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getNamt().是因为,在此虚拟机里面可能不存在
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel类,那么就通不过编译
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			if (c != null) {
				SwingUtilities.updateComponentTreeUI(c); // 注意
			}
			// UIManager.getLookAndFeelDefaults().put("ClassLoader",
			// getClass().getClassLoader());
		} catch (Exception ex) {
		}
		// this line needs to be implemented in order to make JWS work properly
	}

	/** 设置窗口透明度 */
	@SuppressWarnings("restriction")
	public static void initWindowOpaque(Window w, float opaque) {
		// 设置窗口透明度
		try {
			com.sun.awt.AWTUtilities.setWindowOpaque(w, false);// 设置窗体透明
			com.sun.awt.AWTUtilities.setWindowOpacity(w, opaque);
		} catch (Exception e) {
		}
	}
}
