/**
 * 
 */
package com.r.app.taobaoshua.yuuboo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.collections.SetUtils;

import com.r.app.taobaoshua.yuuboo.model.PV;
import com.r.app.taobaoshua.yuuboo.model.PVQuest;

/**
 * @author oky
 * 
 */
public class YuuBooDataContext {
	private static final String FIRE_NAME_CHANGER_LISTENER = "DataChangerListener";
	private static final String FIRE_METHOD_NAME_PV = "PV";
	private static final String FIRE_METHOD_NAME_PV_QUEST = "PVQUEST";
	private List<DataChangerListener> changerListener = new ArrayList<DataChangerListener>();

	// XXX r-app:taobaoshua 这个如果不设置成"获取"方法不同步的话..在JTableModel里面则有线程安全问题,所以,经常会抛出null异常
	private Queue<PV> pvs = new ConcurrentLinkedQueue<PV>(); // PV
	private Queue<PVQuest> pvQuests = new ConcurrentLinkedQueue<PVQuest>(); // PV任务
	@SuppressWarnings("unchecked")
	private Set<String> pvFailTaskIds = SetUtils.synchronizedSet(new HashSet<String>()); // 校验失败的PV

	// ------------
	/** 添加PV集 */
	public void addPVs(List<PV> pvlist) {
		for (PV pv : pvlist) {
			if (!pvs.contains(pv) && !pvFailTaskIds.contains(pv.getId())) {
				// FIXME r-app:taobaoshua 这里需要支持三种特殊情况的查询
				if (pv.isIntoStoreAndSearch()) {
					continue;
				}
				pvs.add(pv);
			}
		}
		fireListener(FIRE_NAME_CHANGER_LISTENER, FIRE_METHOD_NAME_PV);
	}

	/** 弹出一个PV */
	public PV pollPV() {
		if (pvs.size() == 0) {
			return null;
		}
		PV pv = pvs.poll();
		fireListener(FIRE_NAME_CHANGER_LISTENER, FIRE_METHOD_NAME_PV);
		return pv;
	}

	/** 获得所有的pv集 */
	public PV[] getPVs() {
		return pvs.toArray(new PV[] {});
	}

	/** 返回来路流量任务数量 */
	public int getPVSize() {
		return pvs.size();
	}

	// ------------
	/** 添加失败的PV任务ID */
	public void addPVFailTaskId(String id) {
		pvFailTaskIds.add(id);
	}

	/** 返回任务失败ID */
	public Collection<String> getPvFailTaskIds() {
		return this.pvFailTaskIds;
	}

	/** 批量添加失败的PV任务ID */
	public void addPVFailTaskIds(Collection<String> lines) {
		pvFailTaskIds.addAll(lines);
	}

	// --------------

	/** 返回来路PV列表刷新间隔(单位 秒) */
	public int getPVListRefreshInterval() {
		return 30;
	}

	/** 返回来路PV任务列表刷新间隔(单位 秒) */
	public int getPVQuestListRefreshInterval() {
		return 15;
	}

	/** 返回做每个任务的间隔时间 */
	public int getPVQuestTakeTaskIntervalTime() {
		return 30;
	}

	/** 返回接手任务间隔时间 */
	public int getPVTakeTaskIntervalTime() {
		return 40;
	}

	/** 返回在淘宝搜索宝贝时,最大检索的页数 */
	public int getExecSearchTaobaoPageNumberCommand() {
		return 50;
	}

	// ---------------

	/** 添加数据变动监听器 */
	public void addChangerListener(DataChangerListener changerListener) {
		this.changerListener.add(changerListener);
	}

	/** 执行监听器 */
	private void fireListener(String listenerName, String methodName) {
		switch (listenerName) {
		case FIRE_NAME_CHANGER_LISTENER:
			switch (methodName) {
			case FIRE_METHOD_NAME_PV:
				for (DataChangerListener listener : changerListener) {
					listener.changePVList();
				}
				break;
			case FIRE_METHOD_NAME_PV_QUEST:
				for (DataChangerListener listener : changerListener) {
					listener.changePVQuestList();
				}
				break;
			}

			break;
		}
	}

	// ---------------
	/** 添加PV任务集 */
	public void addPVQuests(PVQuest... pvQuestLists) {
		for (PVQuest pvQuest : pvQuestLists) {
			if (!pvQuests.contains(pvQuest) && !pvFailTaskIds.contains(pvQuest.getId())) {
				pvQuests.add(pvQuest);
			}
		}
		fireListener(FIRE_NAME_CHANGER_LISTENER, FIRE_METHOD_NAME_PV_QUEST);
	}

	/**
	 * 检查pvQuest任务是否已存在
	 * 
	 * @return
	 */
	public boolean containsPVQuest(PVQuest pvQuest) {
		return pvQuests.contains(pvQuest);
	}

	/** 获得所有的pv任务集 */
	public PVQuest[] getPVQuests() {
		return pvQuests.toArray(new PVQuest[] {});
	}

	public int getPVQuestSize() {
		return pvQuests.size();
	}

	/** 弹出一个PV */
	public PVQuest pollPVQuest() {
		if (pvQuests.size() == 0) {
			return null;
		}
		PVQuest pvQuest = pvQuests.poll();
		fireListener(FIRE_NAME_CHANGER_LISTENER, FIRE_METHOD_NAME_PV_QUEST);
		return pvQuest;
	}
}
