/**
 * 
 */
package com.r.core.desktop.ctrl.alert;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.lang3.StringUtils;

import com.r.core.bean.JsFunction;
import com.r.core.desktop.ctrl.impl.dialog.HAuthCodeDialog;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.AuthCodeTime;
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.HLoginHandler;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.exceptions.SException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.tool.QQTool;

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

    /** 弹出错误信息,自定义级错误,弹出错误信息后不会退出系统 */
    public static void showErrorTips(SException e, Component parentComponent) {
        showErrorTips(e.getMessage(), parentComponent, e);
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
        showError(errorMessage, parentComponent);
    }

    /** 弹出错误信息,系统级错误,弹出错误信息后会自动退出系统 */
    public static void showErrorOnExit(String errorMessage, Component parentComponent, Throwable e) {
        showErrorTips(errorMessage, parentComponent, e);
        System.exit(1);
    }

    /** 弹出错误信息,系统级错误,弹出错误信息后不会退出系统 */
    public static void showError(String errorMessage, Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, "错误信息", JOptionPane.ERROR_MESSAGE);
    }

    /** 弹出输入框 */
    public static String showInputDialog(String title) {
        return JOptionPane.showInputDialog(title);
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

    /**
     * 弹出验证码输入框
     * 
     * @param obtain
     *            验证码图片获取器
     * @return 验证码
     */
    public static String showAuthCodeDialog(HImageObtain obtain) {
        HAuthCodeDialog hAuthCodeDialog = new HAuthCodeDialog(obtain);
        hAuthCodeDialog.setVisible(true);
        hAuthCodeDialog.dispose();
        try {
            return hAuthCodeDialog.getAuthCode();
        } finally {
            hAuthCodeDialog = null;

        }
    }

    /**
     * 弹出登陆框
     * 
     * @param title
     *            标题
     * @param handler
     *            登陆执行处理器
     * @return 如果成功登陆,则返回true,其它任何情况下都返回false
     */
    public static boolean showLoginDialog(String title, HLoginHandler handler) {
        HLoginDialog hLoginDialog = new HLoginDialog(null, title, handler);
        hLoginDialog.setVisible(true);
        hLoginDialog.dispose();
        try {
            return hLoginDialog.isLogin();
        } finally {
            hLoginDialog = null;
        }
    }

    /**
     * 弹出QQ登陆窗口
     * 
     * @param httpSocket
     *            套接字
     * @param appid
     *            腾讯网页应用ID
     * @param username
     *            默认用户名
     * @param password
     *            默认密码
     * @return 是否登陆成功
     */
    public static boolean showLoginDialogByQQ(final HttpSocket httpSocket, final String appid, final String username, final String password) {
        return HAlert.showLoginDialog("请登陆QQ账户", new HLoginHandler() {

            /** 默认的验证码 */
            private String authCode = null;
            /** 辅助校验码 */
            private String checkVC = null;
            /** 是否需要输入验证码 */
            private boolean isHaveAuthCode = false;

            @Override
            public Dimension getHImageSize() {
                return new Dimension(130, 55);
            }

            @Override
            public Image getHImage(String username, String password) {
                if (StringUtils.isNotBlank(username)) {
                    JsFunction checkVC = QQTool.getCheckVC(httpSocket, appid, username);
                    this.checkVC = checkVC.getPar(3);

                    if ("1".equals(checkVC.getPar(1))) { // 需要验证码
                        isHaveAuthCode = true;
                        try {
                            return QQTool.getLoginWebVerifycodeImage(httpSocket, appid, username);
                        } catch (NetworkIOReadErrorException e) {
                            HAlert.showErrorTips(e, null);
                        }

                    }
                    if ("0".equals(checkVC.getPar(1))) { // 不需要验证码 .默认验证码
                        isHaveAuthCode = false;
                        this.authCode = checkVC.getPar(2);
                    }
                }
                isHaveAuthCode = false;
                return null;
            }

            @Override
            public AuthCodeTime getAuthCodeTime() {
                return AuthCodeTime.填写用户名之后;
            }

            @Override
            public int doLogin(String username, String password, String authCode) {
                if (StringUtils.isBlank(username)) {// 用户名不能为空
                    return 6;
                }
                if (StringUtils.isBlank(password)) {// 密码不能为空
                    return 7;
                }
                if (StringUtils.isBlank(authCode) && this.isHaveAuthCode) { // 需要验证码时,就必须验证验证码是否为空
                    return 8;
                }
                if (StringUtils.isBlank(this.authCode) && !this.isHaveAuthCode) {// 当不需要手动填写验证码时,必须传入腾讯网页传过来的默认验证码
                    return 5;
                }

                JsFunction loginWeb = QQTool.loginWeb(httpSocket, appid, username, password, this.checkVC, this.isHaveAuthCode ? authCode : this.authCode);
                int flag = Integer.valueOf(loginWeb.getPar(1));
                switch (flag) {
                case 0:
                    return 0;
                case 4:
                    return 2;
                case 7:
                    return 5;
                }
                return -1;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
            }
        });
    }
}
