/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-3-13
 * <修改描述:>
 */
package com.r.core.desktop.ctrl.impl.panle;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;

import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.desktop.ctrl.impl.button.HIconButton;
import com.r.core.exceptions.io.IOReadErrorException;
import com.r.core.util.ImageUtil;
import com.r.core.util.ImageUtil.ReadImage;

/**
 * 抽屉
 * 
 * @author rain
 * @version [1.0, 2013-3-13]
 */
public class HWindowTitlePanle extends HBasePanel {
	private static final long serialVersionUID = -3916468248121993905L;

	/** 图片读取接口名称 */
	private static final String READIMAGE = HWindowTitlePanle.class.getName();

	// 初始化图片读取接口
	static {
		ImageUtil.init(HWindowTitlePanle.READIMAGE, new ReadImage() {
			@Override
			public Image readImage(String imageKey) throws IOReadErrorException {
				String imagepath = "com/hiyouyu/images/" + imageKey;
				InputStream ins = ClassLoader.getSystemResourceAsStream(imagepath);
				try {
					return ImageIO.read(ins);
				} catch (IOException e) {
					throw new IOReadErrorException("读取图片失败[" + imagepath + "]");
				}
			}
		});
	}

	/** 标题 */
	private JLabel titleLabel = new JLabel();

	/** 置顶按钮 */
	private HIconButton aWayTopButton = null;

	/** 最小化按钮 */
	private HIconButton minButton = null;

	/** 关闭按钮 */
	private HIconButton closeButton = null;

	/** 默认构造函数 */
	public HWindowTitlePanle() {
		super();
		init(null, null, null, null);
	}

	/** 默认构造函数 */
	public HWindowTitlePanle(String title) {
		super();
		init(title, null, null, null);
	}

	/** 默认构造函数 */
	public HWindowTitlePanle(String title, HIconButton topButton, HIconButton minButton, HIconButton closeButton) {
		super();
		init(title, topButton, minButton, closeButton);
	}

	/**
	 * @param 对title进行赋值
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	/** 添加关闭事件 */
	public void addActionListenerByCloseButton(ActionListener l) {
		if (closeButton != null) {
			closeButton.addActionListener(l);
		}
	}

	/** 添加最小化事件 */
	public void addActionListenerByMinButton(ActionListener l) {
		if (minButton != null) {
			minButton.addActionListener(l);
		}
	}

	/** 添加置顶事件 */
	public void addActionListenerByAWayTopButton(ActionListener l) {
		if (aWayTopButton != null) {
			aWayTopButton.addActionListener(l);
		}
	}

	/** 初始化 */
	private void init(String title, HIconButton topButton, HIconButton minButton, HIconButton closeButton) {
		titleLabel.setText(title);
		if (topButton != null) {
			this.aWayTopButton = topButton;
		}
		if (minButton != null) {
			this.minButton = minButton;
		}
		if (closeButton != null) {
			this.closeButton = closeButton;
		}

		initStyle();
		initImage();
		initComponent();
	}

	private void initStyle() {
		setOpaque(false);
	}

	/** 读取默认图片 */
	private void initImage() {
		Icon closeIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "close.png"));
		Icon closeRolloverIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "close_rollover.png"));
		Icon closePressedIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "close_pressed.png"));
		closeButton = new HIconButton("关闭", closeIcon, closeRolloverIcon, closePressedIcon);

		Icon minIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "min.png"));
		Icon minRolloverIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "min_rollover.png"));
		Icon minPressedIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "min_pressed.png"));
		minButton = new HIconButton("最小化", minIcon, minRolloverIcon, minPressedIcon);

		Icon aWayTopIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "aWayTop.png"));
		Icon aWayTopRolloverIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "aWayTop_rollover.png"));
		Icon aWayTopPressedIcon = ImageUtil.readIconFromImage(ImageUtil.getImage(HWindowTitlePanle.READIMAGE, "aWayTop_pressed.png"));
		aWayTopButton = new HIconButton("置顶", aWayTopIcon, aWayTopRolloverIcon, aWayTopPressedIcon);
	}

	private void initComponent() {
		setLayout(new BorderLayout(1, 1));

		add(titleLabel, BorderLayout.WEST);
		HBasePanel buttonPanle = new HBasePanel();
		buttonPanle.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanle.setOpaque(false);
		if (minButton != null) {
			buttonPanle.add(minButton, BorderLayout.EAST);
		}
		if (aWayTopButton != null) {
			buttonPanle.add(aWayTopButton, BorderLayout.EAST);
		}
		if (closeButton != null) {
			buttonPanle.add(closeButton, BorderLayout.EAST);
		}
		add(buttonPanle, BorderLayout.EAST);
	}
}
