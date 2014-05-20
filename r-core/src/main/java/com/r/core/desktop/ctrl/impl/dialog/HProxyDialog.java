/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2012-12-28
 * <修改描述:>
 */
package com.r.core.desktop.ctrl.impl.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Proxy;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.r.core.desktop.ctrl.HBaseBox;
import com.r.core.desktop.ctrl.HBaseDialog;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.exceptions.io.NetworkIOReadErrorException;
import com.r.core.httpsocket.HttpSocket;
import com.r.core.httpsocket.context.HttpProxy;

/**
 * 代理设置窗口
 * 
 * @author rain
 * @version [1.0, 2012-12-28]
 */
public class HProxyDialog extends HBaseDialog {
	private static final long serialVersionUID = -6294574984222305510L;
	private static final Logger logger = LoggerFactory.getLogger(HProxyDialog.class);
	private static final int CTRL_STRUT = 5; // 控件之间的间隔
	private static final String TEST_SERVICE_ADDRESS = "www.baidu.com"; // 测试服务器地址
	private static final String TEST_SERVICE_NAME = "百度"; // 测试服务器名称
	private static final int TIMEOUT = 3 * 1000; // 默认3秒超时

	private JTextField proxyAddressTextField = new JTextField(); // 妖精账号
	private JTextField proxyPortTextField = new JTextField(); // 妖精账号密码
	private ButtonGroup proxyTypeButtonGroup = new ButtonGroup(); // 代理类型组
	private JCheckBox enableCheckBox = new JCheckBox(); // 启用
	private JButton okButton = new JButton("确定");
	private JButton testButton = new JButton("测试");
	private JButton exitButton = new JButton("取消");
	private HProxyHandler hProxyHandler; // 代理处理器
	private HProxy hProxy = null;

	public HProxyDialog(JFrame frame, HProxy hProxy, HProxyHandler hProxyHandler) {
		super(frame, "代理设置", true);
		logger.debug("实例化[代理设置窗口]");
		this.hProxyHandler = hProxyHandler;
		this.hProxy = hProxy;
		initSelf();
		initComponent();
		initListeners();
	}

	public HProxyDialog(JDialog dialog, HProxy hProxy, HProxyHandler hProxyHandler) {
		super(dialog, "代理设置", true);
		logger.debug("实例化[代理设置窗口]");
		this.hProxyHandler = hProxyHandler;
		this.hProxy = hProxy;
		initSelf();
		initComponent();
		initListeners();
	}

	/** 初始化控件监听 */
	private void initListeners() {
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initHProxy();
				HProxyDialog.this.hProxyHandler.proxyOk(HProxyDialog.this.hProxy);
			}
		});

		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isProxyOk = false;
				initHProxy();
				if (isCheckHProxyOK()) {
					// 测试代理是否可以正常连接服务器
					try {
						HttpSocket httpSocket = HttpSocket.newHttpSocket(false, null);
						httpSocket.setTimeout(TIMEOUT); // 设置超时时间
						httpSocket.setProxy(HttpProxy.newInstance(HProxyDialog.this.hProxy.isEnable(), HProxyDialog.this.hProxy.getProxyType(), HProxyDialog.this.hProxy.getProxyHostName(), HProxyDialog.this.hProxy.getProxyPort()));
						httpSocket.send(TEST_SERVICE_ADDRESS);
						isProxyOk = true;
					} catch (NetworkIOReadErrorException ie) {
						isProxyOk = false;
						HAlert.showErrorTips("代理测试失败 - 连接" + TEST_SERVICE_NAME + "服务器[失败]", HProxyDialog.this, ie);
					} catch (Exception e1) {
						isProxyOk = false;
						HAlert.showErrorTips("代理测试失败 - " + e1.getMessage(), HProxyDialog.this, e1);
					}
					if (isProxyOk) {
						HAlert.showTips("代理测试成功 - 连接" + TEST_SERVICE_NAME + "服务器[成功]", "代理测试成功", HProxyDialog.this);
						isProxyOk = true;
					}
				}
				HProxyDialog.this.hProxyHandler.proxyTest(HProxyDialog.this.hProxy, isProxyOk);
			}
		});

		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HProxyDialog.this.setVisible(false);
				HProxyDialog.this.dispose();
			}
		});
	}

	/**
	 * 初始化自身
	 * 
	 * @param hProxy
	 */
	private void initSelf() {
		setModal(true); // 设置成模态对话框
		setSize(new Dimension(300, 230));// 设置窗口大小
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// 点击关闭按钮,注销窗口
	}

	/** 初始化控件 */
	private void initComponent() {
		add(getComponentByAccount(), BorderLayout.NORTH);
		add(getComponentByButton(), BorderLayout.SOUTH);
	}

	/** 初始化组建_账户信息部分 */
	private HBaseBox getComponentByAccount() {
		// ///----设置布局
		HBaseBox northBox = HBaseBox.createVerticalBaseBox();
		northBox.setBorder(BorderFactory.createTitledBorder("代理信息")); // 设置箱子组件内边距

		// 端口
		HBaseBox proxyEnableBox = HBaseBox.createHorizontalBaseBox();
		proxyEnableBox.add(new JLabel("启  用："));
		proxyEnableBox.add(enableCheckBox);
		proxyEnableBox.add(HBaseBox.createHorizontalGlue());
		northBox.add(proxyEnableBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));
		enableCheckBox.setSelected(hProxy.isEnable());

		// 地址
		HBaseBox proxyAddressBox = HBaseBox.createHorizontalBaseBox();
		proxyAddressBox.add(new JLabel("地  址："));
		proxyAddressBox.add(proxyAddressTextField);
		northBox.add(proxyAddressBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));
		String hostName = hProxy.getProxyHostName();
		if (hostName != null) {
			this.proxyAddressTextField.setText(hostName);
		}

		// 端口
		HBaseBox proxyPortBox = HBaseBox.createHorizontalBaseBox();
		proxyPortBox.add(new JLabel("端口号："));
		proxyPortBox.add(proxyPortTextField);
		northBox.add(proxyPortBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));
		int port = hProxy.getProxyPort();
		if (0 < port && port < 65536) {
			this.proxyPortTextField.setText(String.valueOf(port));
		}

		// 类型
		Proxy.Type type = Proxy.Type.HTTP;
		try {
			type = Proxy.Type.values()[hProxy.getProxyPort()];
		} catch (Exception e) {
		}
		HBaseBox proxyTypeBox = HBaseBox.createHorizontalBaseBox();
		for (Proxy.Type proxyType : Proxy.Type.values()) {
			proxyTypeBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
			if (Proxy.Type.HTTP.name().equals(proxyType.name())) { // 现在只支持http代理模式
				JRadioButton proxyTypeRadioButton = new JRadioButton(proxyType.name());
				proxyTypeRadioButton.setActionCommand(String.valueOf(proxyType.ordinal()));
				if (type.name().equals(proxyType.name())) { // 默认选中
					proxyTypeRadioButton.setSelected(true);
				}
				proxyTypeButtonGroup.add(proxyTypeRadioButton);
				proxyTypeBox.add(proxyTypeRadioButton);
			}
			proxyTypeBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
		}
		northBox.add(proxyTypeBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		// 提示信息
		JLabel tipsLabel = new JLabel("超过" + TIMEOUT + "秒后，没有响应，算作超时");
		tipsLabel.setFont(new Font(tipsLabel.getFont().getName(), tipsLabel.getFont().getStyle(), 14));
		tipsLabel.setForeground(Color.RED);
		HBaseBox tipsBox = HBaseBox.createHorizontalBaseBox();
		tipsBox.add(tipsLabel);
		tipsBox.add(HBaseBox.createHorizontalGlue());
		northBox.add(tipsBox);
		northBox.add(HBaseBox.createVerticalStrut(CTRL_STRUT));

		return northBox;
	}

	/** 初始化组建_按钮部分 */
	private HBaseBox getComponentByButton() {
		// 按钮
		HBaseBox southBox = HBaseBox.createHorizontalBaseBox();
		southBox.setBorder(BorderFactory.createEmptyBorder(CTRL_STRUT, CTRL_STRUT, CTRL_STRUT, CTRL_STRUT)); // 设置箱子组件内边距
		southBox.add(okButton).requestFocus();
		southBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
		southBox.add(testButton).requestFocus();
		southBox.add(HBaseBox.createHorizontalStrut(CTRL_STRUT));
		southBox.add(exitButton);
		return southBox;
	}

	/**
	 * 校验当前代理值是否格式上正确
	 * 
	 * @return boolean 是否通过数据校验
	 */
	public boolean isCheckHProxyOK() {

		// 代理端口
		boolean isHaveErrorByProxyPort = false;
		int proxyPort = -1;
		try {
			proxyPort = Integer.valueOf(this.proxyPortTextField.getText()).intValue();
		} catch (NumberFormatException e) {
			isHaveErrorByProxyPort = true;
		} finally {
			if (isHaveErrorByProxyPort || (proxyPort < 1 || 65535 < proxyPort)) {
				/** 弹出错误提示 */
				HAlert.showWarnTips("端口必须是大于1且小于65536的整数", this);
				HProxyDialog.this.proxyPortTextField.setText("");
				return false;
			}
		}

		// 代理类型
		Proxy.Type proxyType = null;
		try {
			proxyType = Proxy.Type.values()[Integer.valueOf(proxyTypeButtonGroup.getSelection().getActionCommand()).intValue()];
		} catch (Exception e) {
			/** 弹出错误提示 */
			HAlert.showWarnTips("请选择一个正确的代理类型", this);
			HProxyDialog.this.proxyPortTextField.setText("");
			return false;
		}
		if (proxyType == null) {
			return false;
		}

		return true;
	}

	/** 初始化当前代理值 */
	private void initHProxy() {
		// 代理地址
		String proxyAddress = this.proxyAddressTextField.getText();
		if (StringUtils.isNotBlank(proxyAddress)) {
			this.hProxy.setProxyHostName(this.proxyAddressTextField.getText());
		}

		// 是否启用
		try {
			this.hProxy.setEnable(this.enableCheckBox.isSelected());
		} catch (NumberFormatException e) {
		}

		// 代理端口
		try {
			this.hProxy.setProxyPort(Integer.valueOf(this.proxyPortTextField.getText()).intValue());
		} catch (NumberFormatException e) {
		}

		// 代理类型
		try {
			this.hProxy.setProxyType(Proxy.Type.values()[Integer.valueOf(proxyTypeButtonGroup.getSelection().getActionCommand()).intValue()]);
		} catch (Exception e) {
		}
	}

	/**
	 * @return the hProxy
	 */
	public HProxy getProxy() {
		return hProxy;
	}

	public static interface HProxyHandler {
		/** 确定 */
		void proxyOk(HProxy hProxy);

		/**
		 * 代理测试
		 * 
		 * @param isOk
		 *            是否测试成功
		 */
		void proxyTest(HProxy hProxy, boolean isOk);
	}

	public static interface HProxy {
		public Proxy.Type getProxyType();

		/** 设置是否启用代理 */
		public void setEnable(boolean isEnable);

		/** 是否启用代理 */
		public boolean isEnable();

		/** 设置代理类型 */
		public void setProxyType(Proxy.Type proxyType);

		/** 获取代理地址 */
		public String getProxyHostName();

		/** 设置代理地址 */
		public void setProxyHostName(String proxyHostName);

		/** 获取代理端口 */
		public int getProxyPort();

		/** 设置代理端口 */
		public void setProxyPort(int proxyPort);

	}
}
