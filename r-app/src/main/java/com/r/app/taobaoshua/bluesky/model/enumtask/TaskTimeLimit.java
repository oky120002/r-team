/**
 * 
 */
package com.r.app.taobaoshua.bluesky.model.enumtask;

/**
 * @author Administrator
 *
 */
public enum TaskTimeLimit {
	立即("立即确认", 0), //
	分钟30("30分钟好评", 30), //
	天1("1天好评", 1 * 24 * 60), //
	天2("2天好评", 2 * 24 * 60), //
	天3("3天好评", 3 * 24 * 60), //
	天4("4天好评", 4 * 24 * 60), //
	天5("5天好评", 5 * 24 * 60), //
	天6("6天好评", 6 * 24 * 60), //
	天7("7天好评", 7 * 24 * 60), //
	;

	private String name;
	private int timeLimit;

	/**
	 * 返回任务时限
	 * 
	 * @param timeLimit
	 *            -1:立即确认<br />
	 *            0:30分钟<br />
	 *            1:1天<br />
	 *            2:2天<br />
	 *            3:3天<br />
	 *            4:4天<br />
	 *            5:5天<br />
	 *            6:6天<br />
	 *            7:7天<br />
	 *            其它:null
	 * @return
	 */
	public static TaskTimeLimit getTaskTimeLimitByName(String name) {

		switch (name) {
		case "立即确认":
			return TaskTimeLimit.立即;
		case "30分钟好评":
			return TaskTimeLimit.分钟30;
		case "1天好评":
			return TaskTimeLimit.天1;
		case "2天好评":
			return TaskTimeLimit.天2;
		case "3天好评":
			return TaskTimeLimit.天3;
		case "4天好评":
			return TaskTimeLimit.天4;
		case "5天好评":
			return TaskTimeLimit.天5;
		case "6天好评":
			return TaskTimeLimit.天6;
		case "7天好评":
			return TaskTimeLimit.天7;
		default:
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	TaskTimeLimit(String name, int timeLimit) {
		this.name = name;
		this.timeLimit = timeLimit;
	}
}