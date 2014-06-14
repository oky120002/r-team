/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import java.awt.Image;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskType;
import com.r.core.exceptions.SwitchPathException;
import com.r.core.util.ImageUtil;

/**
 * 等待接手任务列表
 * 
 * @author oky
 * 
 */
public class TaskListTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -154657428132471147L;
	private static final String[] COLS = new String[] { "任务类型" };
	private List<Task> tasks = null;

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public int getRowCount() {
		return tasks.size();
	}

	@Override
	public int getColumnCount() {
		return COLS.length;
	}

	@Override
	public String getColumnName(int col) {
		return COLS[col];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Task task = tasks.get(rowIndex);

		switch (columnIndex) {
		case 0:
			TaskType taskType = task.getTaskType();
			Image image = null;
			switch (taskType) {
			case 实物:
				image = ImageUtil.readImageFromIO("com/r/app/taobaoshua/bluesky/image/realobject.gif");
				break;
			case 实购:
				image = ImageUtil.readImageFromIO("com/r/app/taobaoshua/bluesky/image/realobject_shop.gif");
				break;
			case 虚拟:
				image = ImageUtil.readImageFromIO("com/r/app/taobaoshua/bluesky/image/virtual.gif");
				break;
			case 虚购:
				image = ImageUtil.readImageFromIO("com/r/app/taobaoshua/bluesky/image/virtual_shop.gif");
				break;
			}
			return image;
		default:
			throw new SwitchPathException("列表列越界");
		}
	}

}
