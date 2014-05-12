package com.r.core.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.r.core.exceptions.initialization.InitializationErrorException;

public class TrayUtil {

	private static TrayIcon tray = null;

	/**
	 * 
	 * <初始化托盘图标>
	 * 
	 * @param image
	 *            托盘图标
	 * @param title
	 *            托盘标题
	 * @param actionListener
	 *            托盘双击响应事件
	 * @param trayItems
	 *            托盘项
	 */
	public static void initTray(Image image, String title, ActionListener actionListener, Collection<TrayItem> trayItems) {
		if (tray == null) {
			if (SystemTray.isSupported()) {
				TrayUtil.tray = new TrayIcon(image, title, creatTrayMenuItems(trayItems));

				TrayUtil.tray.setImageAutoSize(true); // 设置图片自动缩放
				if (actionListener != null) {
					// 添加双击响应事件
					TrayUtil.tray.addActionListener(actionListener);
				}

				try {
					SystemTray.getSystemTray().add(TrayUtil.tray); // 添加托盘图标
				} catch (AWTException e) {
					throw new InitializationErrorException("添加托盘图标失败");
				}
			} else {
				throw new InitializationErrorException("此系统不支持托盘图标");
			}
		} else {
			throw new InitializationErrorException("已经初始化过托盘图标，不能再次初始化");
		}
	}

	/**
	 * 
	 * <添加托盘项>
	 * 
	 * @param trayItem
	 *            托盘项
	 * 
	 */
	public static void addTrayMenuItem(TrayItem trayItem) {
		if (trayItem.isSeparator()) {
			TrayUtil.tray.getPopupMenu().addSeparator();
		} else {
			TrayUtil.tray.getPopupMenu().add(creatTrayMenuItem(trayItem));
		}
	}

	/**
	 * 
	 * <添加托盘项>
	 * 
	 * @param actionCommand
	 *            响应的命令
	 * @param title
	 *            标题
	 * @param actionListener
	 *            响应的回调方法
	 * 
	 * @author rain
	 */
	public static void addTrayMenuItem(final String actionCommand, final String title, final ActionListener actionListener) {
		TrayUtil.addTrayMenuItem(new TrayItem() {

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public String getActionCommand() {
				return actionCommand;
			}

			@Override
			public ActionListener getActionListener() {
				return actionListener;
			}

			@Override
			public boolean isSeparator() {
				return false;
			}
		});
	}

	/** 添加分隔符 */
	public static void addSeparator() {
		TrayUtil.addTrayMenuItem(new TrayItem() {

			@Override
			public String getTitle() {
				return null;
			}

			@Override
			public String getActionCommand() {
				return null;
			}

			@Override
			public ActionListener getActionListener() {
				return null;
			}

			@Override
			public boolean isSeparator() {
				return true;
			}
		});
	}

	/**
	 * 
	 * <创建托盘菜单>
	 * 
	 * @param trayItem
	 *            托盘项
	 * 
	 * @return MenuItem [返回类型说明]
	 */
	private static MenuItem creatTrayMenuItem(TrayItem trayItem) {
		MenuItem menuItem = new MenuItem(trayItem.getTitle());
		menuItem.setActionCommand(trayItem.getActionCommand());
		menuItem.addActionListener(trayItem.getActionListener());
		return menuItem;
	}

	/**
	 * 
	 * <创建托盘图标菜单项>
	 * 
	 * @param actionListener
	 *            点击菜单响应方法
	 * 
	 * @return PopupMenu [返回类型说明]
	 */
	private static PopupMenu creatTrayMenuItems(Collection<TrayItem> trayItems) {
		PopupMenu menu = new PopupMenu();
		if (CollectionUtils.isNotEmpty(trayItems)) {
			for (TrayItem trayItem : trayItems) {
				if (trayItem.isSeparator()) {
					menu.addSeparator();
				} else {
					menu.add(creatTrayMenuItem(trayItem));
				}
			}
		}
		return menu;
	}

	/**
	 * <托盘项>
	 * 
	 * @author rain
	 * @version [版本号, 2013-2-25]
	 * @see [相关类/方法]
	 * @since [产品/模块版本]
	 */
	public static interface TrayItem {
		/** 获得响应命令 */
		public String getActionCommand();

		/** 获得标题 */
		public String getTitle();

		/** 获得响应方法 */
		public ActionListener getActionListener();

		/** 是否分隔符 */
		public boolean isSeparator();
	}

}
