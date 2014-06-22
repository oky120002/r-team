/**
 * 
 */
package com.r.core.desktop.support;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author oky
 * 
 */
public class TitleCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -8737258310551129037L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null) {
			setToolTipText(value.toString());
		}

		return this;
	}

}
