/**
 * 
 */
package com.r.core.desktop.ctrl.alert;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * 弹出框
 * 
 * @author Administrator
 * 
 */
public class HAlert {
	/**
	 * 弹出提示框信息
	 * 
	 * @param message
	 *            提示信息
	 * @param parentComponent
	 *            父窗口
	 */
	public static void showTips(String message, String title, Component parentComponent) {
		title = StringUtils.isBlank(title) ? "提示信息" : title;
		JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/** 弹出警告信息 */
	public static void showWarnTips(String errorMessage, Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, errorMessage, "错误信息", JOptionPane.WARNING_MESSAGE);
	}

	/** 弹出错误信息,系统级错误,弹出错误信息后不会退出系统 */
	public static void showErrorTips(String errorMessage, Component parentComponent, Throwable e) {
		if (e == null) {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				System.err.println(stackTraceElement.toString());
			}
		} else {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(parentComponent, errorMessage, "错误信息", JOptionPane.ERROR_MESSAGE);
	}

	/** 弹出错误信息,系统级错误,弹出错误信息后不会退出系统 */
	public static void showErrorTips(String errorMessage, Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, errorMessage, "错误信息", JOptionPane.ERROR_MESSAGE);
	}

	/** 弹出错误信息,系统级错误,弹出错误信息后会自动退出系统 */
	public static void showError(String errorMessage, Component parentComponent, Throwable e) {
		showErrorTips(errorMessage, parentComponent, e);
		System.exit(1);
	}

	/**
	 * 弹出文件选择框
	 * 
	 * @param title
	 *            选择框标题
	 * @param currentDirectory
	 *            选择框默认定位的文件夹
	 * @param fileFilter
	 *            类型、文件和文件名过滤器
	 * @param parentComponent
	 *            父控件
	 * @return 选中的文件,如果发生io错误,或者没有选中文件.或者其它的一些未知错误,则返回null
	 */
	public static File showFileChooser(String title, File currentDirectory, FileFilter fileFilter, Component parentComponent) {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setDialogTitle(title);
		jFileChooser.setCurrentDirectory(currentDirectory);
		jFileChooser.setFileFilter(fileFilter);
		jFileChooser.setVisible(true);
		int result = jFileChooser.showOpenDialog(parentComponent);
		File selectedFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = jFileChooser.getSelectedFile();
			if (selectedFile.exists()) {
				return selectedFile;
			} else {
				return null;
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		} else if (result == JFileChooser.ERROR_OPTION) {
			return null;
		} else {
			return null;
		}
	}
}
