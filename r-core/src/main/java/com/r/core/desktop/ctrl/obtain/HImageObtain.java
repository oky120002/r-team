/**
 * 
 */
package com.r.core.desktop.ctrl.obtain;

import java.awt.Dimension;
import java.awt.Image;

/**
 * 图片获取器
 * 
 * @author rain
 *
 */
public interface HImageObtain {
    /** 获取图片 */
    Image getHImage();

    /** 获取图片尺寸 */
    Dimension getHImageSize();
}
