package com.r.core.desktop.ctrl.impl.label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import org.quartz.SchedulerException;

import com.r.core.exceptions.SwitchPathException;
import com.r.core.util.TaskUtil;

/**
 * 时间标签<br />
 * new出时间标签后,需要调用start()才能启动
 * 
 * @author Administrator
 * 
 */
public class HTimeLabel extends JLabel {
    private static final long serialVersionUID = 495049751073694276L;

    /** key:时间标签,value:当前时间(单位毫秒) */
    private static Set<HTimeLabel> times = new HashSet<HTimeLabel>();
    private static boolean isInit = false; // 是否已经初始化
    private boolean isRunning = false; // 是否运行中
    private Type type = Type.Nowtime; // 显示类型
    private String preTimeStr = ""; // 显示在时间前的标签字符串

    // 当前时间
    private String format = "yyyy-MM-dd HH:mm:ss"; // 时间格式

    // 倒计时属性
    private long countdownTime = -1; // 倒计时剩余
    private String startText = ""; // 倒计时开始钱显示的字符串
    private String endText = ""; // 结束后显示的字符串
    private Runnable countDownRunnable; // 倒计时完成后执行

    public HTimeLabel() {
        super();
        HTimeLabel.times.add(this);
        init();
    }

    /**
     * 创建普通时间标签
     * 
     * @param format
     *            显示的格式化时间字符串(单位毫秒)
     * @return 时间标签
     */
    public static HTimeLabel newTimeLabel(String format) {
        HTimeLabel timeLabel = new HTimeLabel();
        timeLabel.setType(Type.Nowtime); // 设置成倒计时模式
        timeLabel.setFormat(format); // 设置格式化时间的字符串
        return null;
    }

    /**
     * 创建一个倒计时标签
     * 
     * @param countdownTime
     *            倒计时剩余时间
     * @param startText
     *            倒计时开始前显示的字符串(单位毫秒)
     * @param endText
     *            结束后显示的字符串(单位毫秒)
     * @return 时间标签
     */
    public static HTimeLabel newCountdownTimeLabel(long countdownTime, String startText, String endText) {
        HTimeLabel timeLabel = new HTimeLabel();
        timeLabel.setType(Type.Countdown); // 设置成倒计时模式
        timeLabel.setStartText(startText); // 设置倒计时开始前的字符串
        timeLabel.setEndText(endText); // 结束后显示的字符串
        timeLabel.setCountdownTime(countdownTime);
        return timeLabel;
    }

    /** 初始化 */
    public synchronized void init() {
        if (HTimeLabel.isInit) {
            return;
        }
        HTimeLabel.isInit = true;
        try {
            TaskUtil.executeScheduleTask(new Runnable() {
                @Override
                public void run() {
                    for (HTimeLabel timeLabel : HTimeLabel.times) {
                        if (timeLabel.isRunning) {
                            Long value = timeLabel.getCountdownTime();
                            switch (timeLabel.getType()) {
                            case Nowtime:
                                timeLabel.setText(timeLabel.getPreTimeStr() + new SimpleDateFormat(timeLabel.getFormat()).format(new Date()));
                                break;
                            case Countdown:
                                if (value != null) {
                                    if (value.longValue() > 0) {
                                        timeLabel.setText(timeLabel.getPreTimeStr() + HTimeLabel.getCountDownTimeString(HTimeLabel.this.countdownTime));
                                        timeLabel.setCountdownTime(value.longValue() - 1000);
                                    } else {
                                        timeLabel.pause();
                                        timeLabel.setText(timeLabel.getEndText());
                                        if (countDownRunnable != null) {
                                            countDownRunnable.run();
                                        }
                                    }
                                }
                                break;
                            default:
                                throw new SwitchPathException("HTimelabel标签中,没有对应的时间流动类型.");
                            }

                        }
                    }
                }
            }, "0/1 * * * * ?");
        } catch (SchedulerException e) {
        }
    }

    /** 启动 */
    public HTimeLabel start() {
        this.isRunning = true;
        return this;
    }

    /** 暂停 */
    public HTimeLabel pause() {
        this.isRunning = false;
        return this;
    }

    /** 获取当前时间标签是否正在运行中 */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 获得当前时间标签类型
     * 
     * @return 时间标签类型
     * @see com.hiyouyu.core.controls.ctrl.impl.label.HTimeLabel.Type
     */
    public Type getType() {
        return type;
    }

    /**
     * 设置当前时间标签类型
     * 
     * @param type
     *            时间标签的类型
     * @return 返回时间标签自身的引用
     * @see com.hiyouyu.core.controls.ctrl.impl.label.HTimeLabel.Type
     */
    public HTimeLabel setType(Type type) {
        this.type = type;
        return this;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public HTimeLabel setFormat(String format) {
        this.format = format;
        return this;
    }

    /** 获取倒计时剩余时间(单位毫秒) */
    public long getCountdownTime() {
        return countdownTime;
    }

    /** 设置倒计时剩余时间(单位毫秒) */
    public HTimeLabel setCountdownTime(long countdownTime) {
        this.countdownTime = countdownTime;
        return this;
    }

    /** 获得倒计时剩余时间字符串表示法 */
    public static String getCountDownTimeString(long countdownTime) {
        long hour = countdownTime / 1000 / 60 / 60; // 时
        long minute = countdownTime / 1000 / 60 % 60; // 分
        long second = countdownTime / 1000 % 60; // 秒

        StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour);
        } else {
            sb.append(0);
        }
        sb.append(":");

        if (minute > 0) {
            sb.append(minute);
        } else {
            sb.append(0);
        }
        sb.append(":");

        if (second > 0) {
            sb.append(second);
        } else {
            sb.append(0);
        }
        return sb.toString();
    }

    /**
     * @return the countDownRunnable
     */
    public Runnable getCountDownRunnable() {
        return countDownRunnable;
    }

    /**
     * 设置倒计时完成后执行的方法
     * 
     * @param countDownRunnable
     *            the countDownRunnable to set
     */
    public void setCountDownRunnable(Runnable countDownRunnable) {
        this.countDownRunnable = countDownRunnable;
    }

    /**
     * @return the startText
     */
    public String getStartText() {
        return startText;
    }

    /**
     * @param startText
     *            the startText to set
     */
    public HTimeLabel setStartText(String startText) {
        this.startText = startText;
        if (!this.isRunning) {
            this.setText(startText);
        }
        return this;
    }

    /**
     * @return the endText
     */
    public String getEndText() {
        return endText;
    }

    /**
     * @return the preTimeStr
     */
    public String getPreTimeStr() {
        return preTimeStr;
    }

    /**
     * @param preTimeStr
     *            the preTimeStr to set
     */
    public void setPreTimeStr(String preTimeStr) {
        this.preTimeStr = preTimeStr;
    }

    /**
     * @param endText
     *            the endText to set
     */
    public HTimeLabel setEndText(String endText) {
        this.endText = endText;
        return this;
    }

    public static enum Type {
        /** 每隔1秒,显示当前时间(当前时间) */
        Nowtime,
        /** 每隔1秒,显示剩余时间(倒计时) */
        Countdown, ;
    }
}
