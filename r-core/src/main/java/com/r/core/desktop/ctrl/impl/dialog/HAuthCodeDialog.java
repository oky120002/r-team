/**
 * 
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.alert.HAlert.AuthCodeObtain;
import com.r.core.desktop.ctrl.impl.panle.HImagePanel;
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

    /** 对话框宽度最小值(没有考虑边框宽度) */
    private static final int minWidth = 130;
    /** 对话框高度最小值(没有考虑边框和菜单栏等宽度,一般40) */
    private static final int minHeight = 30;

    /** 验证码输入框 */
    private JTextField testField = new JTextField();
    /** 确定对话框 */
    private JButton okButton = new JButton("确定");
    /** 图片面板 */
    private HImagePanel imagePanel = null;
    /** 验证码图片获取器 */
    private AuthCodeObtain obtain = null;
    /** 验证码 */
    private String authCode = null;

    /**
     * 构造验证码对话框
     * 
     * @param image
     *            验证码
     * @param obtain
     */
    public HAuthCodeDialog(AuthCodeObtain obtain) {
        super((JFrame) null, "请输入验证码", true);
        AssertUtil.isNotNull("验证码图片获取器不能为null", obtain);
        Dimension dimension = obtain.getAuthCodeImageSize();
        AssertUtil.isNotNull("验证码图片尺寸不能为null", dimension);

        this.obtain = obtain;
        this.imagePanel = new HImagePanel(dimension);

        this.setSize((int) dimension.getWidth(), (int) dimension.getHeight() + 40);
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
        Image authCodeImage = this.obtain.getAuthCodeImage();
        int width = authCodeImage.getWidth(null) < minWidth ? minWidth : authCodeImage.getWidth(null);
        int height = authCodeImage.getHeight(null) < minHeight ? minHeight : authCodeImage.getHeight(null);
        this.setSize(width, height + 40); // 根据验证码设置窗口大小
        this.imagePanel.setImage(authCodeImage);
        this.testField.setText(null);
    }

    /** 返回子控件面板 */
    private Component getContextPanel() {
        HBaseBox box = HBaseBox.createVerticalBaseBox();
        box.add(this.imagePanel);
        box.add(HBaseBox.createHorizontalBaseBox(testField, okButton));
        return box;
    }
}
