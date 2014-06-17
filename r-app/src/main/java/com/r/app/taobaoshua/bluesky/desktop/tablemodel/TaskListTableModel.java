/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enums.TaskType;
import com.r.core.exceptions.SwitchPathException;

/**
 * 等待接手任务列表
 * 
 * @author oky
 * 
 */
public class TaskListTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -154657428132471147L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final BlueSky blueSky = BlueSky.getInstance();
	private static final List<Task> EMPTY = new ArrayList<Task>();
	private static final String[] COLS = new String[] { "No.", "任务类型", "发布价格", "发布点数", "发布人", "在线", "VIP", "账户等级", "发布时间", "好评时限", "任务状态" };
	private List<Task> tasks = EMPTY;

	public void setTasks(List<Task> tasks) {
		if (CollectionUtils.isEmpty(tasks)) {
			this.tasks = EMPTY;
		}
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
		switch (columnIndex) {
		case 0: // 序号
		case 2: // 发布价格
		case 3: // 发布点数
		case 4: // 发布人
		case 8: // 发布时间
		case 9: // 好评时限
		case 10: // 任务状态
			return String.class;
		case 1: // 任务类型
		case 5: // 是否在线
		case 6: // 是否VIP
		case 7: // 等级图标
			return Image.class;
		default:
			throw new SwitchPathException("列表列越界");
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Task task = tasks.get(rowIndex++);
		switch (columnIndex) {
		case 0: // 序号
			return String.valueOf(rowIndex);
		case 1: // 任务类型
			TaskType taskType = task.getTaskType();
			Image image = null;
			switch (taskType) {
			case 实物:
				image = blueSky.getImage("realobject.gif");
				break;
			case 实购:
				image = blueSky.getImage("realobject_shop.gif");
				break;
			case 虚拟:
				image = blueSky.getImage("virtual.gif");
				break;
			case 虚购:
				image = blueSky.getImage("virtual_shop.gif");
				break;
			case 搭配:
				image = blueSky.getImage("dapei.gif");
				break;
			case 其它:
			default:
				return "其它";
			}
			return image;
		case 2: // 发布价格
			return String.valueOf(task.getSecurityPrice());
		case 3: // 发布点数
			return String.valueOf(task.getPublishingPoint());
		case 4: // 发布人
			return task.getAccount();
		case 5: // 是否在线
			if (task.getIsAccountOnLine()) {
				return blueSky.getImage("online.gif");
			} else {
				return blueSky.getImage("notonline.gif");
			}
		case 6: // 是否VIP
			if (task.getIsAccountVip()) {
				return blueSky.getImage("vip.gif");
			} else if (task.getIsNew()) {
				return blueSky.getImage("new.gif");
			} else {
				return "";
			}
		case 7: // 等级图标
			String accountLevel = task.getAccountLevel();
			if (StringUtils.isNotBlank(accountLevel)) {
				return blueSky.getImage(accountLevel);
			} else if (task.getIsNew()) {
				return blueSky.getImage("new.gif");
			}
			return accountLevel;
		case 8: // 发布时间
			return sdf.format(task.getTaskPublishingTime());
		case 9: // 好评时限
			int time = task.getTimeLimit().intValue();
			if (time < 30) { // 立即
				return "立即确认";
			} else if (30 < time && time < 60) {
				return "30分钟确认";
			} else {
				return ((time + 1) / 60 / 24) + "天";
			}
		case 10: // 任务状态
			return task.getStatus().name();
		default:
			throw new SwitchPathException("列表列越界");
		}
	}
}
