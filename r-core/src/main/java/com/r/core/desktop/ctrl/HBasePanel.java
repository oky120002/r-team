/**
 * 描          述:  <展示模块>
 * 修  改   人:  rain
 * 修改时间:  2012-11-12
 * <修改描述:>
 */
package com.r.core.desktop.ctrl;

import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.lang.ArrayUtils;

/**
 * <顶层面板>
 * 
 * @author rain
 * @version [版本号, 2012-11-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class HBasePanel extends JPanel {
    private static final long serialVersionUID = 3763512195783698252L;

    public HBasePanel() {
        super();
    }

    public HBasePanel(LayoutManager layout) {
        super(layout);
    }

    /** 批量添加控件 */
    public HBasePanel(JComponent... components) {
        if (ArrayUtils.isNotEmpty(components)) {
            for (JComponent jComponent : components) {
                add(jComponent);
            }
        }
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
