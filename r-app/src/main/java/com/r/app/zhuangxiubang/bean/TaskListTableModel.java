/**
 * 
 */
package com.r.app.zhuangxiubang.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.CollectionUtils;

import com.r.app.zhuangxiubang.model.Task;
import com.r.core.exceptions.SwitchPathException;

/**
 * 招标任务列表
 * 
 * @author oky
 * 
 */
public class TaskListTableModel extends AbstractTableModel implements TableModel {
    private static final long serialVersionUID = -154657428132471147L;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
    private List<Task> tasks = new ArrayList<Task>();

    public TaskListTableModel() {
        super();
    }

    public void setTasks(List<Task> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            this.tasks = new ArrayList<Task>();
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
            return task.getBianhao();
        case 1: // 楼盘
            return task.getLoupan();
        case 2: // 要求
            return task.getYaoqiu();
        case 3: // 户型
            return task.getHuxing();
        case 4: // 是否能招标
            return task.getIsBm() == null ? "招标已结束" : (task.getIsBm() ? "等待招标" : "招标已结束");
        case 5:// 抓取时间
            return sdf.format(task.getCreateDate());
        case 6: // 是否已阅
            return task.getIsBm() == null ? "未读" : (task.getIsReaded() ? "已阅" : "未读");
        case 7: // 状态
            return task.getStatus() == null ? "" : task.getStatus();
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

    public List<Task> getTasks() {
        return tasks;
    }

    // 列表头按照枚举排列顺序来排列
    private enum Col {
        编号("编号", String.class, 15, 0), //
        楼盘("楼盘", String.class, -1, 1), //
        户型("户型", String.class, -1, 3), //
        要求("要求", String.class, -1, 2), //
        // 招标("招标状态", String.class, 30, 4), //
        时间("抓取时间", String.class, 25, 5), //
        已阅("已阅", String.class, 25, 6), // 是否已阅
        状态("状态", String.class, 25, 7), // 状态
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
