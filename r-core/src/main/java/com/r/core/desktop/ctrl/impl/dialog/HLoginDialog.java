/**
 * 
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.AssertUtil;
import com.r.core.util.CalUtil;
import com.r.core.util.TaskUtil;

/**
 * @author rain
 *
 */
public class HLoginDialog extends HBaseDialog implements ActionListener, FocusListener, KeyListener, MouseListener, Runnable {
    private static final long serialVersionUID = -7004190292798750897L;

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(HLoginDialog.class, "登陆框");
    /** 对话框宽度最小值 */
    private static final int minWidth = 130;
    /** 对话框高度最小值 */
    private static final int minHeight = 45;

    /** 登陆执行处理器 */
    private HLoginHandler handler = null;

    /** 图片面板 */
    private HImagePanel imagePanel = null;
    /** 用户名文本框 */
    private JTextField usernameTextField = new JTextField(18);
    /** 密码文本框 */
    private JTextField passwordTextField = new JTextField(18);
    /** 校验码文本框 */
    private JTextField authCodeTextField = new JTextField(4);
    /** 账号标签 */
    private JLabel usernameLabel = new JLabel("账   号: ");
    /** 密码标签 */
    private JLabel passwordLabel = new JLabel("密   码: ");
    /** 验证码标签 */
    private JLabel authCodeLabel = new JLabel("验证码: ");
    /** 登陆按钮 */
    private JButton loginButton = new JButton("登陆");

    /** 验证码区域 */
    private HBaseBox authCodebox = HBaseBox.createHorizontalBaseBox();

    /** 是否成功登陆 */
    private boolean isLogin = false;

    /** 是否已经第一次调用了setVisible方法 */
    private boolean _firstVisible = false;

    /**
     * 构造一个登陆对话框<br/>
     * 如果此登陆不需要验证码,则obtain传入null
     * 
     * @param owner
     *            所属对话框
     * @param title
     *            标题
     * @param handler
     *            登陆执行处理器
     * @param obtain
     *            验证码获取器
     */
    public HLoginDialog(Dialog owner, String title, HLoginHandler handler) {
        super(owner, title, true);
        AssertUtil.isNotNull("登陆执行处理器不能为null", handler);
        this.handler = handler;

        this.setAuthCodeDialogSize(new Dimension(0, 0)); // 调整登陆对话框大小
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 关闭对话框,则直接销毁窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout());// 设置对话框布局为"方向"布局
        if (!AuthCodeTime.永不.equals(this.handler.getAuthCodeTime())) {
            this.imagePanel = new HImagePanel(handler.getHImageSize()); // 创建验证码图片面板
            this.imagePanel.addMouseListener(this);
            this.authCodebox.adds(this.authCodeLabel, this.imagePanel, this.authCodeTextField); // 初始化验证码区域控件
            this.authCodebox.setVisible(false); // 默认设置验证码区域是不显示的
        }
        this.add(getContextPanel(), BorderLayout.CENTER); // 添加子控件

        this.loginButton.addActionListener(this); // 添加"登陆"按钮响应事件
        this.passwordTextField.addKeyListener(this); // 添加"密码文本框"键盘响应事件
        this.authCodeTextField.addKeyListener(this);// 添加"验证码文本框"键盘响应事件
        this.usernameTextField.addFocusListener(this);// 添加"账号文本框"失去焦点事件
        this.passwordTextField.addFocusListener(this);// 添加"密码文本框"失去焦点事件

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.isLogin = false;
        int flag = this.handler.doLogin(this.usernameTextField.getText(), this.passwordTextField.getText(), this.authCodeTextField.getText());
        switch (flag) {
        case 0:// 成功登陆
            logger.debug("登陆系统成功!");
            this.isLogin = true;
            this.setVisible(false);
            break;
        case 1:// 密码错误
            logger.debug("登陆系统失败 - 密码错误");
            HAlert.showError("密码错误!", this);
            break;
        case 2:// 验证码错误
            logger.debug("登陆系统失败 - 验证码错误");
            HAlert.showError("验证码错误!", this);
            this.updateAuthCodeImage();
            break;
        case 3:// 不存在此账号
            logger.debug("登陆系统失败 - 不存在此账号");
            HAlert.showError("不存在此账号!", this);
            break;
        case 4:// 账号与密码不匹配
            logger.debug("登陆系统失败 - 账号与密码不匹配");
            HAlert.showError("账号与密码不匹配!", this);
            break;
        case 5:// 提交的参数错误或者缺失
            logger.debug("登陆系统失败 - 提交的参数错误或者缺失");
            HAlert.showError("提交的参数错误或者缺失!", this);
            break;
        case 6:// 账号为空
            logger.debug("登陆系统失败 - 账号为空");
            HAlert.showError("账号为空!", this);
            break;
        case 7:// 密码为空
            logger.debug("登陆系统失败 - 密码为空");
            HAlert.showError("密码为空!", this);
            break;
        case 8:// 验证码为空
            logger.debug("登陆系统失败 - 验证码为空");
            HAlert.showError("验证码为空!", this);
            break;
        case -1:
        default:
            logger.debug("登陆系统失败 - 未知错误");
            HAlert.showError("未知错误!", this);
            break;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        switch (this.handler.getAuthCodeTime()) {
        case 填写用户名之后:
            if (this.usernameTextField.equals(e.getSource())) {
                updateAuthCodeImage();
            }
            break;
        case 填写密码之后:
            if (this.passwordTextField.equals(e.getSource())) {
                updateAuthCodeImage();
            }
            break;
        case 填写用户名和密码之后:
            updateAuthCodeImage();
            break;
        default:
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') { // 回车
            loginButton.doClick();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /** 变换验证码图片 */
    public void updateAuthCodeImage() {
        if (!AuthCodeTime.永不.equals(this.handler.getAuthCodeTime())) {
            TaskUtil.executeSequenceTask(this, HAuthCodeDialog.class.getName());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updateAuthCodeImage();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        if (!AuthCodeTime.永不.equals(this.handler.getAuthCodeTime())) {
            Image authCodeImage = this.handler.getHImage(this.usernameTextField.getText(), this.passwordTextField.getText()); // 如果获取验证码失败
            this.authCodeTextField.setText(null);
            if (authCodeImage == null) {
                logger.debug("获取验证码图片完成! - 没有获取到验证码图片");
                this.authCodebox.setVisible(false);
                this.setAuthCodeDialogSize(new Dimension(0, 0));
            } else {
                logger.debug("获取验证码图片完成! - 获取到验证码图片");
                this.authCodebox.setVisible(true);
                this.setAuthCodeDialogSize(new Dimension(authCodeImage.getWidth(null), authCodeImage.getHeight(null)));
                this.imagePanel.setImage(authCodeImage);
            }
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (!this._firstVisible && AuthCodeTime.立即.equals(this.handler.getAuthCodeTime())) {
            this._firstVisible = true;
            this.updateAuthCodeImage();
        }
        super.setVisible(b);
    }

    /** 是否成功登陆 */
    public boolean isLogin() {
        return this.isLogin;
    }

    /**
     * 根据验证码图片大小和其他控件大小,来设置验证码对话框大小<br/>
     * 宽=max(标签+验证码+验证码输入框,标签+文本框)的高度<br/>
     * 高=用户名行+密码行+验证码行+确定按钮行的高度<br/>
     * 高宽都必须大于最小值
     * 
     * @param authCodeImageSize
     *            验证码图片尺寸
     */
    private void setAuthCodeDialogSize(Dimension authCodeImageSize) {
        int width = CalUtil.max(70 + (int) authCodeImageSize.getWidth() + 70, 70 + 140);
        int height = (int) authCodeImageSize.getHeight() + 35 + 35 + 35;

        width = width < minWidth ? minWidth : width;
        height = height < minHeight ? minHeight : height;

        this.setSize(width, height);
    }

    /** 返回子控件面板 */
    private Component getContextPanel() {
        this.usernameTextField.setText(this.handler.getUsername());
        this.passwordTextField.setText(this.handler.getPassword());

        HBaseBox box = HBaseBox.createVerticalBaseBox();
        box.add(HBaseBox.createHorizontalBaseBox(this.usernameLabel, this.usernameTextField));
        box.add(HBaseBox.createHorizontalBaseBox(this.passwordLabel, this.passwordTextField));
        if (!AuthCodeTime.永不.equals(this.handler.getAuthCodeTime())) {
            box.add(this.authCodebox);
        }
        box.add(HBaseBox.createHorizontalRight(this.loginButton));
        return box;
    }

    /** 登陆处理器 */
    public interface HLoginHandler {
        /**
         * 执行登陆操作<br/>
         * 如果登陆成功,则返回null,否则返回错误信息
         * 
         * @param username
         *            用户名
         * @param password
         *            密码
         * @param authCode
         *            验证码,如果此登陆不需要验证码,则传入null
         * @return 返回登陆情况<br/>
         *         -1:未知错误<br/>
         *         0:登陆成功<br/>
         *         1:密码错误<br/>
         *         2:验证码错误<br/>
         *         3:不存在此账号<br/>
         *         4:账号与密码不匹配<br/>
         *         5:提交的参数错误或者缺失<br/>
         *         6:账号为空<br/>
         *         7:密码为空<br/>
         *         8:验证码为空<br/>
         */
        int doLogin(String username, String password, String authCode);

        /**
         * 获取验证码图片
         * 
         * @param username
         *            用户名
         * @param password
         *            密码
         */
        Image getHImage(String username, String password);

        /** 获取图片尺寸 */
        Dimension getHImageSize();

        /** 获取获得验证码的时机 */
        AuthCodeTime getAuthCodeTime();

        /** 获取默认用户名 */
        String getUsername();

        /** 获取默认密码 */
        String getPassword();
    }

    /** 获取验证码的时机 */
    public enum AuthCodeTime {
        永不, 立即, 填写用户名之后, 填写密码之后, 填写用户名和密码之后, ;
    }

}
