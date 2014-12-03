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
import com.r.core.desktop.ctrl.impl.dialog.HLoginDialog.LoginStatus;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.exceptions.SException;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.tool.QQTool;
import com.r.core.util.AssertUtil;

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
        AssertUtil.isNotNull("验证码图片获取器不能为空", obtain);
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
     * @param obtain
     *            登陆后数据获取器(可为null)
     * @return 返回登陆后的状态
     */
    public static LoginStatus showLoginDialog(String title, HLoginHandler handler, LoginReturnValueObtain obtain) {
        AssertUtil.isNotNull("登陆执行处理器不能为空", handler);
        HLoginDialog hLoginDialog = new HLoginDialog(null, StringUtils.isBlank(title) ? "登陆框" : title, handler);
        hLoginDialog.setVisible(true);
        hLoginDialog.dispose();
        LoginStatus loginStatus = hLoginDialog.getLoginStatus();
        if (obtain != null) {
            obtain.returnValue(loginStatus, hLoginDialog.getUsername(), hLoginDialog.getPassword(), hLoginDialog.getAuthCodeImage(), hLoginDialog.isKeepUsernameAndPassword());
        }
        try {
            return loginStatus;
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
     *            默认用户名,可为null
     * @param password
     *            默认密码,可为null,为了安全最好为null
     * @param obtain
     *            登陆后数据获取器
     * @return 返回登陆后的状态
     */
    public static LoginStatus showLoginDialogByQQ(final HttpSocket httpSocket, final String appid, final String username, final String password, LoginReturnValueObtain obtain) {
        AssertUtil.isNotNull("网络请求套接字不能为空", httpSocket);
        AssertUtil.isNotBlank("QQ网络应用ID不能为空", appid);
        return HAlert.showLoginDialog("请登陆QQ账户", new HLoginHandler() {

            /** 默认的验证码 */
            private String authCode = null;
            /** 辅助校验码 */
            private String checkVC = null;
            /** 是否需要手动输入验证码 */
            private boolean isHaveAuthCode = false;

            @Override
            public LoginStatus doLogin(String username, String password, String authCode) {
                if (StringUtils.isBlank(username)) {// 用户名不能为空
                    return LoginStatus.账号为空;
                }
                if (StringUtils.isBlank(password)) {// 密码不能为空
                    return LoginStatus.密码为空;
                }
                if (StringUtils.isBlank(authCode) && this.isHaveAuthCode) { // 需要验证码时,就必须验证验证码是否为空
                    return LoginStatus.验证码为空;
                }
                if (StringUtils.isBlank(this.authCode) && !this.isHaveAuthCode) {// 当不需要手动填写验证码时,必须传入腾讯网页传过来的默认验证码
                    return LoginStatus.提交的参数错误或者缺失;
                }

                JsFunction loginWeb = QQTool.loginWeb(httpSocket, appid, username, password, this.checkVC, this.isHaveAuthCode ? authCode : this.authCode);
                int flag = Integer.valueOf(loginWeb.getPar(1));
                switch (flag) {
                case 0:
                    return LoginStatus.成功登陆;
                case 3:
                    return LoginStatus.账号与密码不匹配;
                case 4:
                    return LoginStatus.密码错误;
                case 7:
                    return LoginStatus.提交的参数错误或者缺失;
                }
                return LoginStatus.未知错误;
            }

            @Override
            public boolean isHaveAuthCode() {
                return true;
            }

            @Override
            public Image getHImage(AuthCodeTime autchCodeTime, Image authCodeImage, String username, String password) {
                if (StringUtils.isNotBlank(username)) {
                    JsFunction checkVC = QQTool.getCheckVC(httpSocket, appid, username);
                    this.checkVC = checkVC.getPar(3);
                    if ("1".equals(checkVC.getPar(1))) { // 需要验证码
                        isHaveAuthCode = true;
                        try {
                            switch (autchCodeTime) {
                            case 第一次显示登陆框:
                            case 登陆后失败后:
                            case 点击验证码图片后:
                            case 填写用户名之后:
                                return QQTool.getLoginWebVerifycodeImage(httpSocket, appid, username);
                            default:
                                return authCodeImage;
                            }
                        } catch (NetworkIOReadErrorException e) {
                            HAlert.showErrorTips(e, null);
                        }
                        return null;
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
            public Dimension getHImageSize() {
                return new Dimension(130, 55);
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public String getPassword() {
                return password;
            }

        }, obtain);
    }

    /** 登陆后数据获取器 */
    public interface LoginReturnValueObtain {
        /**
         * 返回值
         * 
         * @param loginStatus
         *            登陆后状态
         * @param username
         *            用户名
         * @param password
         *            密码
         * @param image
         *            验证码图片
         */
        void returnValue(LoginStatus loginStatus, String username, String password, Image image, boolean isKeepUsernameAndPassword);

    }
}
