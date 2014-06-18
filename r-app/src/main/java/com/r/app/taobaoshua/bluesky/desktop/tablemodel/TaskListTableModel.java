/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import java.awt.Image;
import java.text.NumberFormat;
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
	private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();
	private static final BlueSky blueSky = BlueSky.getInstance();
	private static final List<Task> EMPTY = new ArrayList<Task>();
	private List<Task> tasks = EMPTY;

	public TaskListTableModel() {
		super();
		numberFormat.setMaximumFractionDigits(2);
	}

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
		return Col.values().length;
	}

	@Override
	public String getColumnName(int col) {
		return Col.values()[col].getColName();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Col.values()[columnIndex].getClazz();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Task task = tasks.get(rowIndex++);
		// 列表头按照枚举排列顺序来排列
		int index = Col.values()[columnIndex].getIndex();
		switch (index) {
		case 0: // 任务编号
			return task.getNumber();
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
				return null;
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
			} else {
				return null;
			}
		case 7: // 等级图标
			String accountLevel = task.getAccountLevel();
			if (StringUtils.isNotBlank(accountLevel)) {
				return blueSky.getImage(accountLevel);
			} else if (task.getIsNew()) {
				return blueSky.getImage("new.gif");
			} else {
				return null;
			}
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
		case 11:
			return numberFormat.format(task.getPublishingPointOneDay().doubleValue());
		case 12:
			return numberFormat.format(task.getSecurityPriceOneDayOnePPoint().doubleValue());
		default:
			throw new SwitchPathException("列表列越界");
		}
	}

	// 列表头按照枚举排列顺序来排列
	private enum Col {
		任务编号("任务编号", String.class, 0), //
		任务类型("任务类型", Image.class, 1), //
		发布价格("发布价格", String.class, 2), //
		发布点数("发布点数", String.class, 3), //
		发布点天("发布点/天", String.class, 11), //
		元发布点天("元/1发布点1天", String.class, 12), //
		发布人("发布人", String.class, 4), //
		是否在线("在线", Image.class, 5), //
		是否VIP("VIP", Image.class, 6), //
		等级("等级图标", Image.class, 7), //
		发布时间("发布时间", String.class, 8), //
		好评时限("好评时限", String.class, 9), //
		任务状态("任务状态", String.class, 10), //
		;

		private String colName;
		private Class<?> clazz;
		private int index;

		public String getColName() {
			return colName;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public int getIndex() {
			return index;
		}

		Col(String colName, Class<?> clazz, int index) {
			this.colName = colName;
			this.clazz = clazz;
			this.index = index;
		}
	}
}
