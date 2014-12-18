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
import javax.swing.JPasswordField;
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
public class HLoginDialog extends HBaseDialog implements ActionListener, FocusListener, KeyListener, MouseListener {
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
    private JTextField passwordTextField = new JPasswordField(18);
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

    /** 登陆状态 */
    private LoginStatus loginstatus = LoginStatus.未登陆;
    /** 是否已经第一次调用了setVisible方法(用户验证码变换) */
    private boolean _firstAuthCode = false;
    /** 保存上一次的用户名(用户验证码变换) */
    private String _lastUsername = null;

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
        if (this.handler.isHaveAuthCode()) {
            this.imagePanel = new HImagePanel(handler.getHImageSize()); // 创建验证码图片面板
            this.imagePanel.addMouseListener(this);
            this.authCodebox.adds(this.authCodeLabel, this.imagePanel, this.authCodeTextField); // 初始化验证码区域控件
            this.authCodebox.setVisible(false); // 默认设置验证码区域是不显示的
        }
        this.add(getContextPanel(), BorderLayout.CENTER); // 添加子控件

        this.loginButton.addActionListener(this); // 添加"登陆"按钮响应事件
        this.usernameTextField.addKeyListener(this);// 添加"密码文本框"键盘响应事件
        this.passwordTextField.addKeyListener(this); // 添加"密码文本框"键盘响应事件
        this.authCodeTextField.addKeyListener(this);// 添加"验证码文本框"键盘响应事件
        this.usernameTextField.addFocusListener(this);// 添加"账号文本框"失去焦点事件
        this.passwordTextField.addFocusListener(this);// 添加"密码文本框"失去焦点事件

        this._lastUsername = this.handler.getUsername();
        this.usernameTextField.setText(this._lastUsername);
        this.passwordTextField.setText(this.handler.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.loginstatus = this.handler.doLogin(this.usernameTextField.getText(), this.passwordTextField.getText(), this.authCodeTextField.getText());
        switch (loginstatus) {
        case 成功登陆:
            logger.debug("登陆系统成功!");
            this.setVisible(false);
            break;
        case 验证码错误:
        case 不存在此账号:
        case 密码错误:
        case 提交的参数错误或者缺失:
        case 账号与密码不匹配:
        case 账号为空:
        case 验证码为空:
        case 密码为空:
        case 未知错误:
            logger.debug("登陆系统失败 - {}", loginstatus.name());
            HAlert.showError(loginstatus.name(), this);
            this.updateAuthCodeImage(AuthCodeTime.登陆后失败后);
            break;
        default:
            logger.debug("登陆系统失败 - {}", loginstatus.name());
            HAlert.showError(loginstatus.name(), this);
            break;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.usernameTextField.equals(e.getSource()) && !this.usernameTextField.getText().equals(this._lastUsername)) {
            this._lastUsername = this.usernameTextField.getText();
            updateAuthCodeImage(AuthCodeTime.填写用户名之后);
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

    /**
     * 变换验证码图片
     * 
     * @param authCodeTime
     *            验证码获取时机
     */
    public void updateAuthCodeImage(final AuthCodeTime authCodeTime) {
        if (this.handler.isHaveAuthCode()) {
            TaskUtil.executeSequenceTask(new Runnable() {
                @Override
                public void run() {
                    if (handler.isHaveAuthCode()) {
                        loginButton.setEnabled(false);
                        Image authCodeImage = handler.getHImage(authCodeTime, imagePanel.getImage(), usernameTextField.getText(), passwordTextField.getText()); // 如果获取验证码失败
                        authCodeTextField.setText(null);
                        if (authCodeImage == null) {
                            logger.debug("获取验证码图片完成! - 没有获取到验证码图片");
                            authCodebox.setVisible(false);
                            setAuthCodeDialogSize(new Dimension(0, 0));
                        } else {
                            logger.debug("获取验证码图片完成! - 获取到验证码图片");
                            authCodebox.setVisible(true);
                            setAuthCodeDialogSize(new Dimension(authCodeImage.getWidth(null), authCodeImage.getHeight(null)));
                            imagePanel.setImage(authCodeImage);
                        }
                        loginButton.setEnabled(true);
                    }

                }
            }, HAuthCodeDialog.class.getName());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updateAuthCodeImage(AuthCodeTime.点击验证码图片后);
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
    public void setVisible(boolean b) {
        if (!this._firstAuthCode) {
            this._firstAuthCode = true;
            this.updateAuthCodeImage(AuthCodeTime.第一次显示登陆框);
        }
        super.setVisible(b);
    }

    /** 返回登陆状态 */
    public LoginStatus getLoginStatus() {
        return this.loginstatus;
    }

    /** 获取登陆名 */
    public String getUsername() {
        return this.usernameTextField.getText();
    }

    /** 获取登陆密码 */
    public String getPassword() {
        return this.passwordTextField.getText();
    }

    /** 获取验证码图片 */
    public Image getAuthCodeImage() {
        return this.imagePanel == null ? null : this.imagePanel.getImage();
    }

    /** 是否保存用户名和密码 */
    public boolean isKeepUsernameAndPassword() {
        // TODO r-core 实现"登陆框"保存用户名和密码功能
        return true;
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

        HBaseBox box = HBaseBox.createVerticalBaseBox();
        box.add(HBaseBox.createHorizontalBaseBox(this.usernameLabel, this.usernameTextField));
        box.add(HBaseBox.createHorizontalBaseBox(this.passwordLabel, this.passwordTextField));
        if (this.handler.isHaveAuthCode()) {
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
         */
        LoginStatus doLogin(String username, String password, String authCode);

        /**
         * 获取验证码图片
         * 
         * @param autchCodeTime
         *            验证获取时机
         * @param authCodeImage
         *            当前的验证码图片
         * @param username
         *            用户名
         * @param password
         *            密码
         * @return 如果返回null,隐藏验证码区域
         */
        Image getHImage(AuthCodeTime autchCodeTime, Image authCodeImage, String username, String password);

        /** 获取图片尺寸 */
        Dimension getHImageSize();

        /** 获取默认用户名 */
        String getUsername();

        /** 获取默认密码 */
        String getPassword();

        /** 是否保存用户名和密码 */
        // TODO r-core 请实现此功能
        boolean isLoginKeepUsernameAndPassword();

        /** 是否需要验证码 */
        boolean isHaveAuthCode();
    }

    /** 获取验证码的时机(除开"永不"之外的情况,只生效一次) */
    public enum AuthCodeTime {
        第一次显示登陆框, 填写用户名之后, 点击验证码图片后, 登陆后失败后, ;
    }

    public enum LoginStatus {
        未登陆, 未知错误, // 难以发现的问题
        取消登陆, // TODO r-core 完成通用登陆框的取消登登陆状态
        成功登陆, // 成功登陆
        密码错误, 验证码错误, 不存在此账号, 账号与密码不匹配, 提交的参数错误或者缺失, 账号为空, 密码为空, 验证码为空, // 基本上是登陆后状态
        ;
    }
}
