package com.r.app.zhuangxiubang.core;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.r.app.zhuangxiubang.model.Task;

public class Resolve {
    public void resolveTaskList(Collection<Task> tasks, String html) {
        int curPos = 0;
        int start = html.indexOf("designer_list");
        int end = html.indexOf("hlk_first", start);
        html = html.substring(start, end);

        while ((curPos = html.indexOf("zb_box_left")) != -1) {
            html = html.substring(curPos + "zb_box_left".length());
            Task task = new Task();
            // 编号
            String bianhao = StringUtils.substringBetween(html, "Bm(", ",");
            task.setBianhao(bianhao);

            // 楼盘
            String loupan = StringUtils.substringBetween(html, "class=\"h1\">", "</span>");
            task.setLoupan(StringUtils.isBlank(loupan) ? "(无楼盘信息)" : loupan);

            // 户型
            String huxing = StringUtils.substringBetween(html, "户型：", "</li>").replace("&nbsp;", "");
            task.setHuxing(StringUtils.isBlank(huxing) ? "(无户型信息)" : huxing);

            // 要求
            String yaoqiuContext = StringUtils.substringBetween(html, "onclick=\"Hide", "隐藏全文");
            String yaoqiu = StringUtils.substringBetween(yaoqiuContext, "\">", "<a").replace("&nbsp;", "");
            task.setYaoqiu(StringUtils.isBlank(yaoqiu) ? "(无特殊要求)" : yaoqiu);

            // 是否可以接标
            String bmContext = StringUtils.substringBetween(html, "Bm(", "js_botton.gif");
            if (0 < bmContext.indexOf("display:none")) {
                task.setIsBm(true);
            } else {
                task.setIsBm(false);
            }

            tasks.add(task);
        }
    }
}
