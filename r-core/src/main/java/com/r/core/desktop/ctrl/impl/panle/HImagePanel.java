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

    /** 构造一个没有背景图片的JImagePanel */
    public HImagePanel() {
        this(null, null, null, ShowMode.居中);
    }

    /** 构造一个没有背景图片且居中的JImagePanel */
    public HImagePanel(Image image) {
        this(null, image, null, ShowMode.居中);
    }

    /** 构造一个没有背景图片但设置了尺寸且居中的JImagePanel */
    public HImagePanel(Dimension dimension) {
        this(dimension, null, null, ShowMode.居中);
    }

    /** 构造一个没有背景图片,但具有高宽的JImagePanel */
    public HImagePanel(Dimension dimension, ShowMode showMode) {
        this(dimension, null, null, showMode == null ? ShowMode.居中 : showMode);
    }

    /** 构造一个没有背景图片,但具有高宽的JImagePanel */
    public HImagePanel(Image image, ShowMode showMode) {
        this(null, image, null, showMode == null ? ShowMode.居中 : showMode);
    }

    /**
     * 构造一个具有指定背景图片和指定显示模式的JImagePanel
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
        if (dimension == null && image != null) {
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

    /** 获取图片 */
    public Image getImage() {
        return image;
    }

    /** 设置图片 */
    public void setImage(Image image) {
        this.image = image;
        this.dimension = new Dimension(image.getWidth(null), image.getHeight(null));
        this.repaint();
    }

    /** 获取图片拉伸方式 */
    public ShowMode getShowMode() {
        return showMode;
    }

    /** 设置图片拉伸方式 */
    public void setShowMode(ShowMode showMode) {
        this.showMode = showMode;
        if (image != null) {
            this.repaint();
        }
    }

    /** 获取背景图片 */
    public Image getBackImage() {
        return backImage;
    }

    /** 设置背景图片,默认null.png */
    public void setBackImage(Image backImage) {
        if (backImage == null) {
            this.backImage = ImageUtil.readImageFromIO("com/hiyouyu/core/controls/ctrl/impl/panle/null.png");
        }
        this.backImage = backImage;
    }

}