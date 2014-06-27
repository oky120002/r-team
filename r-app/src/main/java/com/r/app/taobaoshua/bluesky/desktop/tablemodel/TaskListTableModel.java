/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop.tablemodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.CollectionUtils;

import com.r.app.taobaoshua.bluesky.model.Task;
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
			return task.getTaskTypeIcon();
		case 2: // 发布价格
			return String.valueOf(task.getSecurityPrice());
		case 3: // 发布点数
			return String.valueOf(task.getPublishingPoint());
		case 4: // 发布人
			return task.getAccount();
		case 5: // 是否在线
			return task.getIsAccountOnLineIcon();
		case 6: // 是否VIP
			return task.getIsAccountVipIcon();
		case 7: // 等级图标
			return task.getAccountLevelIcon();
		case 8: // 发布时间
			return sdf.format(task.getTaskPublishingTime());
		case 9: // 好评时限
			return task.getTimeLimitString();
		case 10: // 任务状态
			return task.getStatus().name();
		case 11:
			double ppod = task.getPublishingPointOneDay().doubleValue();
			double money = ppod * 0.8 * 0.4;
			return numberFormat.format(ppod) + "点|" + numberFormat.format(money) + "元";
		case 12:
			return numberFormat.format(task.getSecurityPriceOneDayOnePPoint().doubleValue()) + "元";
		case 13: // 商保
			return task.getIsSincerityIcon();
		case 14: // 实名
			return task.getIsIDCardIcon();
		case 15: // 搜索
			return task.getIsSearchIcon();
		case 16: // 搜藏
			return task.getIsCollectIcon();
		case 17: // 旺聊
			return task.getIsWangWangIcon();
		case 18: // 审核
			return task.getIsReviewIcon();
		case 19: // 条件
			return task.getAuxiliaryCondition();
		case 20: // 是否有QQ参与
			return task.getIsUseQQIcon();
		case 21: // 是否改价
			return task.getIsUpdatePriceIcon();
		case 22: // 是否改地址
			return task.getIsUpdateAddrIcon();
		case 23: // 是否真实地址
			return task.getIsBaoGuoIcon();
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

	/** 根据行号返回当前行对应的Task数据 */
	public Collection<Task> getRowTask(int... rowIndexs) {
		Collection<Task> tasks = new ArrayList<Task>();
		for (int rowIndex : rowIndexs) {
			tasks.add(this.tasks.get(rowIndex));
		}
		return tasks;
	}

	// 列表头按照枚举排列顺序来排列
	private enum Col {
		任务编号("任务编号", String.class, 80, 0), //
		任务类型("任务类型", Icon.class, 45, 1), //
		发布价格("发布价格", String.class, -1, 2), //
		发布点数("发布点数", String.class, -1, 3), //
		发布点天("发布点/天", String.class, 145, 11), //
		元发布点天("元/1发布点1天", String.class, 90, 12), //
		好评时限("好评时限", String.class, 65, 9), //
		商保("商保", Icon.class, 25, 13), //
		实名("实名", Icon.class, 45, 14), //
		搜索("搜索", Icon.class, 40, 15), //
		收藏("收藏", Icon.class, 40, 16), //
		旺聊("旺聊", Icon.class, 40, 17), //
		审核("审核", Icon.class, 40, 18), //
		QQ("QQ", Icon.class, 30, 20), //
		改价("改价", Icon.class, 30, 21), //
		地址("地址", Icon.class, 40, 22), //
		包裹("空包", Icon.class, 30, 23), //
		条件("条件", String.class, 250, 19), //
		发布人("发布人", String.class, 100, 4), //
		是否在线("在线", Icon.class, -1, 5), //
		是否VIP("VIP", Icon.class, 30, 6), //
		等级("等级图标", Icon.class, -1, 7), //
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
