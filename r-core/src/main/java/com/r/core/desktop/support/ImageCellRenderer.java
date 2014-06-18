/**
 * 
 */
package com.r.core.desktop.support;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.r.core.util.ImageUtil;

/**
 * @author oky
 * 
 */
public class ImageCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -8737258310551129037L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (value != null && value instanceof Image) {
			setIcon(ImageUtil.readIconFromImage((Image) value));
		} else {
			setIcon(null);
		}
		setValue(null);
		return this;
	}

}
