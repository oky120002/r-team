package com.r.core.desktop.ctrl.impl.panle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.util.ImageUtil;

/**
 * 可设置背景图片的JPanel，提供了三种显示背景图片的方式：居中、平铺和拉伸。 未设置背景图片的情况下，同JPanel。
 * 
 * @author 何雨
 */
public class HImagePanel extends HBasePanel {
	private static final long serialVersionUID = -8251916094895167058L;

	/** 图片展示模式 */
	public enum ShowMode {
		居中, 平铺, 拉伸, ;
	}

	/** 背景图片 */
	private Image image;

	/**
	 * 后备图片<br />
	 * 当背景图片不能显示时(一般为null),就显示此图片
	 */
	private Image backImage;

	/** 背景图片显示模式 */
	private ShowMode showMode;

	/** 图片大小 */
	private Dimension dimension;

	/** 构造一个没有背景图片的JImagePane */
	public HImagePanel() {
		this(null, null, null, ShowMode.居中);
	}

	/** 构造一个没有背景图片,但具有高宽的JImagePane */
	public HImagePanel(Dimension dimension, ShowMode sm) {
		this(dimension, null, null, sm == null ? ShowMode.居中 : sm);
	}

	/** 构造一个没有背景图片,但具有高宽的JImagePane */
	public HImagePanel(Image image, ShowMode sm) {
		this(null, image, null, sm == null ? ShowMode.居中 : sm);
	}

	/**
	 * 构造一个具有指定背景图片和指定显示模式的JImagePane
	 * 
	 * @param backgroundImage
	 *            背景图片
	 * @param showMode
	 *            背景图片显示模式
	 */
	private HImagePanel(Dimension dimension, Image image, Image backImage, ShowMode showMode) {
		super();
		this.dimension = dimension;
		this.showMode = showMode;
		this.backImage = backImage;
		this.image = image;
		setOpaque(false); // 设置背景可以透明

		// 如果手动设置的长宽为空,则提取图片的长宽
		if (dimension == null) {
			this.dimension = new Dimension(image.getWidth(null), image.getHeight(null));
		}
		setPreferredSize(this.dimension); // 设置初始大小,如果为null则由UI提供大小
	}

	/**
	 * 绘制组件
	 * 
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 如果设置了背景图片则显示
		Image tempImage = this.image;
		if (tempImage == null) {
			tempImage = this.backImage;
		}
		if (tempImage != null) {
			int width = (int) dimension.getWidth();
			int height = (int) dimension.getHeight();
			int imageWidth = tempImage.getWidth(this);
			int imageHeight = tempImage.getHeight(this);

			switch (this.showMode) {
			case 居中: {
				int x = (width - imageWidth) / 2;
				int y = (height - imageHeight) / 2;
				g.drawImage(tempImage, x, y, this);
				break;
			}
			case 平铺: {
				for (int ix = 0; ix < width; ix += imageWidth) {
					for (int iy = 0; iy < height; iy += imageHeight) {
						g.drawImage(tempImage, ix, iy, this);
					}
				}
				break;
			}
			case 拉伸: {
				g.drawImage(tempImage, 0, 0, width, height, this);
				break;
			}
			}
		}
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the backgroundImage to set
	 */
	public void setImage(Image image) {
		this.image = image;
		this.repaint();
	}

	/**
	 * @return the showMode
	 */
	public ShowMode getShowMode() {
		return showMode;
	}

	/**
	 * @param showMode
	 *            the showMode to set
	 */
	public void setShowMode(ShowMode showMode) {
		if (showMode != null) {
			this.showMode = showMode;
			this.repaint();
		}
	}

	/**
	 * @return the backImage
	 */
	public Image getBackImage() {
		return backImage;
	}

	/**
	 * @param backImage
	 *            the backImage to set
	 */
	public void setBackImage(Image backImage) {
		if (backImage == null) {
			this.backImage = ImageUtil.readImageFromIO("com/hiyouyu/core/controls/ctrl/impl/panle/null.png");
		}
		this.backImage = backImage;
	}

}