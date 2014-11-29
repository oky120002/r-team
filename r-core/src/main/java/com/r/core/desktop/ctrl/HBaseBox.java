/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-2-6
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.apache.commons.lang.ArrayUtils;

/**
 * 布局箱子
 * 
 * @author rain
 * @version [1.0, 2013-2-6]
 */
public class HBaseBox extends Box {
    private static final long serialVersionUID = 3526805066963571568L;

    /** 一个固定高度5的不可见组件 */
    public static final Component EmptyVertical = HBaseBox.createVerticalStrut(5);
    /** 一个固定宽度5的不可见组件 */
    public static final Component EmptyHorizontal = HBaseBox.createHorizontalStrut(5);

    public HBaseBox(int axis) {
        super(axis);
    }

    /** 创建一个从左到右显示其组件的 Box */
    public static HBaseBox createHorizontalBaseBox() {
        return new HBaseBox(BoxLayout.X_AXIS);
    }

    /** 批量添加组件 */
    public void adds(Component... components) {
        if (ArrayUtils.isNotEmpty(components)) {
            for (Component component : components) {
                if (component != null) {
                    this.add(component);
                }
            }
        }
    }

    /** 创建一个从左到右显示其组件的 Box */
    public static HBaseBox createHorizontalBaseBox(Component... components) {
        HBaseBox box = createHorizontalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        for (Component jComponent : components) {
            box.add(jComponent);
        }
        return box;
    }

    /** 创建一个固定宽度的不可见组件 */
    public static Component createHorizontalStrut(int width) {
        return Box.createHorizontalStrut(width);
    }

    /** 创建一个不固定宽度的不可见组件,此组件尽可能的把自己的宽度设置成最大值 */
    public static Component createHorizontalGlue() {
        return Box.createHorizontalGlue();
    }

    /** 创建一个从左到右显示其组件的 Box ,此组件始终把传入的组件左右居中显示 */
    public static HBaseBox createHorizontalCenter(Component... components) {
        HBaseBox box = HBaseBox.createHorizontalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        box.add(HBaseBox.createHorizontalGlue());
        for (Component component : components) {
            box.add(component);
        }
        box.add(HBaseBox.createHorizontalGlue());
        return box;
    }

    /** 创建一个从左到右显示其组件的 Box ,此组件始终把传入的组件居左显示 */
    public static HBaseBox createHorizontalLeft(Component... components) {
        HBaseBox box = HBaseBox.createHorizontalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        for (Component component : components) {
            box.add(component);
        }
        box.add(HBaseBox.createHorizontalGlue());
        return box;
    }

    /** 创建一个从左到右显示其组件的 Box ,此组件始终把传入的组件居右显示 */
    public static HBaseBox createHorizontalRight(Component... components) {
        HBaseBox box = HBaseBox.createHorizontalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        box.add(HBaseBox.createHorizontalGlue());
        for (Component component : components) {
            box.add(component);
        }
        return box;
    }

    /** 创建一个从上到下显示其组件的 Box */
    public static HBaseBox createVerticalBaseBox() {
        return new HBaseBox(BoxLayout.Y_AXIS);
    }

    /** 创建一个从上到下显示其组件的 Box */
    public static HBaseBox createVerticalBaseBox(Component... components) {
        HBaseBox box = createVerticalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        for (Component jComponent : components) {
            box.add(jComponent);
        }
        return box;
    }

    /** 创建一个固定高度的不可见组件 */
    public static Component createVerticalStrut(int height) {
        return Box.createVerticalStrut(height);
    }

    /** 创建一个不固定高度的不可见组件,此组件尽可能的把自己的高度设置成最大值 */
    public static Component createVerticalGlue() {
        return Box.createVerticalGlue();
    }

    /** 创建一个从上到下显示其组件的 Box ,此组件始终把传入的组件上下居中显示 */
    public static HBaseBox createVerticalCenter(Component... components) {
        HBaseBox box = HBaseBox.createVerticalBaseBox();
        if (ArrayUtils.isEmpty(components)) {
            return box;
        }
        box.add(HBaseBox.createVerticalGlue());
        for (Component component : components) {
            box.add(component);
        }
        box.add(HBaseBox.createVerticalGlue());
        return box;
    }

    /** 创建一个总是具有指定大小的不可见组件。 */
    public static Component createRigidArea(Dimension dim) {
        return Box.createRigidArea(dim);
    }

    /** 创建一个居中显示的 Box ,此组件始终把传入的组件上下左右居中显示 */
    public static Component createCenterBox(Component component) {
        return createHorizontalCenter(createVerticalCenter(component));
    }

    /** 显示/隐藏组件 */
    public void toggle() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}
