/**
 * 
 */
package com.r.app.taobaoshua.desktop;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.r.app.taobaoshua.data.DataContext;
import com.r.app.taobaoshua.model.PVQuest;
import com.r.core.exceptions.SwitchPathException;

/**
 * PV列表数据模型
 * 
 * @author oky
 * 
 */
public class PVQuestListTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -154657428132471147L;
	private static final String[] COLS = new String[] { "编号", "发布者", "接手时间", "店主", "价格", "所在地", "搜索关键字", };
	private DataContext dataContext;

	public PVQuestListTableModel(DataContext dataContext) {
		super();
		this.dataContext = dataContext;
	}

	@Override
	public int getRowCount() {
		return dataContext.getPVQuestSize();
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
		PVQuest[] pvQuests = dataContext.getPVQuests();
		PVQuest pvQuest = null;
		try {
			pvQuest = pvQuests[rowIndex];
		} catch (Exception e) {
			return "";
		}

		String value = null;

		switch (columnIndex) {
		case 0:
			value = pvQuest.getId();
			break;
		case 1:
			value = pvQuest.getPublisher();
			break;
		case 2:
			value = pvQuest.getBoardTime();
			break;
		case 3:
			value = pvQuest.getShopKeeper();
			break;
		case 4:
			value = pvQuest.getPriceMin() + "-" + pvQuest.getPriceMax() + "元";
			break;
		case 5:
			value = pvQuest.getLocation();
			break;
		case 6:
			value = pvQuest.getSearchKey();
			break;
		default:
			throw new SwitchPathException("列表越界");
		}
		return value;
	}
}
