/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.r.app.taobaoshua.yuuboo.data.YuuBooDataContext;
import com.r.app.taobaoshua.yuuboo.model.PV;
import com.r.core.exceptions.SwitchPathException;

/**
 * PV列表数据模型
 * 
 * @author oky
 * 
 */
public class TaskListTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -154657428132471147L;
	private static final String[] COLS = new String[] { "编号", "发布者", "发布时间", "特殊条件", "数量" };
	private YuuBooDataContext dataContext;

	public TaskListTableModel(YuuBooDataContext dataContext) {
		super();
		this.dataContext = dataContext;
	}

	@Override
	public int getRowCount() {
		return dataContext.getPVSize();
	}

	@Override
	public int getColumnCount() {
		return COLS.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLS[col];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PV[] pvs = dataContext.getPVs();
		PV pv = null;
		try {
			pv = pvs[rowIndex];
		} catch (Exception e) {
			return "";
		}

		String value = null;

		switch (columnIndex) {
		case 0:
			value = pv.getId();
			break;
		case 1:
			value = pv.getPublisher();
			break;
		case 2:
			value = pv.getReleaseTime();
			break;
		case 3:
			value = pv.getSpecialConditions();
			break;
		case 4:
			value = pv.getCommodityNumber() + "个";
			break;
		default:
			throw new SwitchPathException("列表列越界");
		}
		return value;
	}
}
