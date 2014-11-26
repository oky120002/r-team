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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.AssertUtil;
import com.r.core.util.CalUtil;
import com.r.core.util.TaskUtil;

/**
 * @author rain
 *
 */
public class HLoginDialog extends HBaseDialog implements ActionListener, Runnable {
    private static final long serialVersionUID = -7004190292798750897L;

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(HLoginDialog.class, "登陆框");
    /** 对话框宽度最小值 */
    private static final int minWidth = 130;
    /** 对话框高度最小值 */
    private static final int minHeight = 70;

    /** 登陆执行处理器 */
    private HLoginHandler handler = null;
    /** 验证码获取器 */
    private HImageObtain obtain = null;

    /** 图片面板 */
    private HImagePanel imagePanel = null;
    /** 用户名文本框 */
    private JTextField usernameTextField = new JTextField(18);
    /** 密码文本框 */
    private JTextField passwordTextField = new JTextField(18);
    /** 校验码文本框 */
    private JTextField authCodeTextField = new JTextField(4);
    /** 登陆按钮 */
    private JButton loginButton = new JButton("登陆");

    /** 是否成功登陆 */
    private boolean isLogin = false;

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
    public HLoginDialog(Dialog owner, String title, HLoginHandler handler, HImageObtain obtain) {
        super(owner, title, true);
        AssertUtil.isNotNull("登陆执行处理器不能为null", handler);
        Dimension dimension = new Dimension(0, 0);
        if (obtain != null) {
            AssertUtil.isNotNull("验证码图片尺寸不能为null", dimension);
            this.imagePanel = new HImagePanel(obtain.getHImageSize());
            this.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
        }

        this.obtain = obtain;
        this.setAuthCodeDialogSize(dimension);
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 关闭对话框,则直接销毁窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout());// 设置对话框布局为"方向"布局
        this.add(getContextPanel(), BorderLayout.CENTER); // 添加子控件
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /** 变换验证码图片 */
    public void updateAuthCodeImage() {
        TaskUtil.executeSequenceTask(this, HAuthCodeDialog.class.getName());
    }

    @Override
    public void run() {
        Image authCodeImage = this.obtain.getHImage();
        if (authCodeImage == null) {
            logger.debug("获取验证码失败!");
            return;
        }

        logger.debug("获取验证码成功!");
        this.setAuthCodeDialogSize(new Dimension(authCodeImage.getWidth(null), authCodeImage.getHeight(null)));
        this.imagePanel.setImage(authCodeImage);
        // this.testField.setText(null);
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
        int height = (int) authCodeImageSize.getHeight() + 47 + 47 + 47;

        width = width < minWidth ? minWidth : width;
        height = height < minHeight ? minHeight : height;

        this.setSize(width, height);
    }

    /** 返回子控件面板 */
    private Component getContextPanel() {
        HBaseBox box = HBaseBox.createVerticalBaseBox();
        box.add(HBaseBox.createHorizontalBaseBox(new JLabel("账  号: "), this.usernameTextField));
        box.add(HBaseBox.createHorizontalBaseBox(new JLabel("密  码: "), this.passwordTextField));
        if (this.imagePanel != null) {
            box.add(HBaseBox.createHorizontalBaseBox(new JLabel("校  验: "), this.imagePanel, this.authCodeTextField));
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
         * @return 登陆成功,则返回null,否则返回错误信息
         */
        String doLogin(String username, String password, String authCode);
    }

    /** 是否成功登陆 */
    public boolean isLogin() {
        return this.isLogin;
    }
}
