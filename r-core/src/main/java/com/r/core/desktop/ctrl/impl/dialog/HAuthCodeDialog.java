/**
 * 
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
import com.r.core.desktop.ctrl.obtain.HImageObtain;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.AssertUtil;
import com.r.core.util.TaskUtil;

/**
 * 验证码对话框<br/>
 * 对话框最多只能缩小到 150x35
 * 
 * @author rain
 *
 */
public class HAuthCodeDialog extends HBaseDialog implements ActionListener, Runnable {
    private static final long serialVersionUID = -5020615116483536863L;

    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(HAuthCodeDialog.class, "验证码对话框");

    /** 对话框宽度最小值 */
    private static final int minWidth = 130;
    /** 对话框高度最小值 */
    private static final int minHeight = 70;

    /** 验证码输入框 */
    private JTextField testField = new JTextField();
    /** 确定对话框 */
    private JButton okButton = new JButton("确定");
    /** 图片面板 */
    private HImagePanel imagePanel = null;
    /** 验证码图片获取器 */
    private HImageObtain obtain = null;
    /** 验证码 */
    private String authCode = null;

    /**
     * 构造验证码对话框
     * 
     * @param obtain
     *            验证码图片获取器
     */
    public HAuthCodeDialog(HImageObtain obtain) {
        this((Frame) null, obtain);
    }

    /**
     * 构造验证码对话框
     * 
     * @param owner
     *            所属对话框
     * @param obtain
     *            验证码图片获取器
     */
    public HAuthCodeDialog(Dialog owner, HImageObtain obtain) {
        super(owner, "请输入验证码", true);
        init(obtain);
    }

    /**
     * 构造验证码对话框
     * 
     * @param owner
     *            所属窗口
     * @param obtain
     *            验证码图片获取器
     */
    public HAuthCodeDialog(Frame owner, HImageObtain obtain) {
        super(owner, "请输入验证码", true);
        init(obtain);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.authCode = this.testField.getText();
        this.setVisible(false);
    }

    /** 返回验证码 */
    public String getAuthCode() {
        return this.authCode;
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
        this.testField.setText(null);
    }

    /**
     * 根据验证码图片大小和其他控件大小,来设置验证码对话框大小<br/>
     * 宽=验证码的宽度<br/>
     * 高=验证码的高度+一行控件(文本框+按钮)的高度<br/>
     * 高宽都必须大于最小值
     * 
     * @param authCodeImageSize
     *            验证码图片尺寸
     */
    private void setAuthCodeDialogSize(Dimension authCodeImageSize) {
        int width = (int) authCodeImageSize.getWidth();
        int height = (int) authCodeImageSize.getHeight() + 45;

        width = width < minWidth ? minWidth : width;
        height = height < minHeight ? minHeight : height;

        this.setSize(width, height);
    }

    /**
     * 初始化验证码窗口参数
     * 
     * @param obtain
     *            验证码图片获取器
     */
    private void init(HImageObtain obtain) {
        AssertUtil.isNotNull("验证码图片获取器不能为null", obtain);
        Dimension dimension = obtain.getHImageSize();
        AssertUtil.isNotNull("验证码图片尺寸不能为null", dimension);

        this.obtain = obtain;
        this.imagePanel = new HImagePanel(dimension);

        this.setAuthCodeDialogSize(dimension);
        this.setLocationRelativeTo(null); // 全屏居中
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // 关闭对话框,则直接销毁窗口
        this.setResizable(false);// 不能调整对话框大小
        this.setLayout(new BorderLayout());// 设置对话框布局为"方向"布局
        this.add(getContextPanel(), BorderLayout.CENTER); // 添加子控件
        this.okButton.addActionListener(this);// 给"确定"按钮添加点击事件
        this.imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateAuthCodeImage();
            }
        });
        this.testField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == '\n') { // 回车
                    okButton.doClick();
                }
            }
        });
    }

    /** 返回子控件面板 */
    private Component getContextPanel() {
        HBaseBox box = HBaseBox.createVerticalBaseBox();
        box.add(this.imagePanel);
        box.add(HBaseBox.createHorizontalBaseBox(testField, okButton));
        return box;
    }
}
