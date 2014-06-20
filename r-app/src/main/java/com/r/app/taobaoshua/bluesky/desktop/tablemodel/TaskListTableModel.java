/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import java.awt.Image;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.model.enumtask.TaskType;
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
			} else if (0 < time && time < 60) {
				return "30分钟确认";
			} else {
				return ((time + 1) / 60 / 24) + "天";
			}
		case 10: // 任务状态
			return task.getStatus().name();
		case 11:
			double ppod = task.getPublishingPointOneDay().doubleValue();
			double money = ppod * 0.8 * 0.4;
			return numberFormat.format(ppod) + "点|" + numberFormat.format(money) + "元";
		case 12:
			return numberFormat.format(task.getSecurityPriceOneDayOnePPoint().doubleValue()) + "元";
		case 13: // 商保
			return task.isSincerity() ? blueSky.getImage("ShangBao.gif") : null;
		case 14: // 实名
			return task.isIDCard() ? blueSky.getImage("ShiMing.gif") : null;
		case 15: // 搜索
			return task.isSearch() ? blueSky.getImage("SouSuo.gif") : null;
		case 16: // 搜藏
			return task.isCollect() ? blueSky.getImage("ShouCang.gif") : null;
		case 17: // 旺聊
			return task.getIsWangWang() ? blueSky.getImage("WangLiao.gif") : null;
		case 18: // 审核
			return task.isReview() ? blueSky.getImage("ShenHe.gif") : null;
		case 19: // 条件
			return task.getAuxiliaryCondition();
		case 20: // 是否有QQ参与
			if (task.isUseQQ()) {
				return "QQ";
			} else {
				return null;
			}
		default:
			throw new SwitchPathException("列表列越界");
		}
	}

	/** 设置列宽 */
	public void setColWidth(JTable jTable) {
		TableColumnModel columns = jTable.getColumnModel();
		Col[] values = Col.values();
		for (int i = 0; i < values.length; i++) {
			TableColumn column = columns.getColumn(i);
			if (0 < values[i].getColWidth()) {
				column.setPreferredWidth(values[i].getColWidth());
			}
		}
	}

	// 列表头按照枚举排列顺序来排列
	private enum Col {
		任务编号("任务编号", String.class, 80, 0), //
		任务类型("任务类型", Image.class, 45, 1), //
		发布价格("发布价格", String.class, -1, 2), //
		发布点数("发布点数", String.class, -1, 3), //
		发布点天("发布点/天", String.class, 145, 11), //
		元发布点天("元/1发布点1天", String.class, 90, 12), //
		好评时限("好评时限", String.class, 45, 9), //
		商保("商保", Image.class, 25, 13), //
		实名("实名", Image.class, 45, 14), //
		搜索("搜索", Image.class, 40, 15), //
		收藏("收藏", Image.class, 40, 16), //
		旺聊("旺聊", Image.class, 40, 17), //
		审核("审核", Image.class, 40, 18), //
		QQ("QQ", String.class, 30, 20), //
		条件("条件", String.class, 250, 19), //
		发布人("发布人", String.class, 100, 4), //
		是否在线("在线", Image.class, -1, 5), //
		是否VIP("VIP", Image.class, -1, 6), //
		等级("等级图标", Image.class, -1, 7), //
		发布时间("发布时间", String.class, -1, 8), //
		任务状态("任务状态", String.class, 80, 10), //
		;

		private String colName;
		private Class<?> clazz;
		private int colWidth;
		private int index;

		public String getColName() {
			return colName;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public int getColWidth() {
			return colWidth;
		}

		public int getIndex() {
			return index;
		}

		Col(String colName, Class<?> clazz, int width, int index) {
			this.colName = colName;
			this.clazz = clazz;
			this.colWidth = width;
			this.index = index;
		}
	}
}
