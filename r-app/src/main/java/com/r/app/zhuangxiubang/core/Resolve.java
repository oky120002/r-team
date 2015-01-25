package com.r.app.zhuangxiubang.core;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.r.app.zhuangxiubang.model.Task;

public class Resolve {

    /** 解析投稿编号 */
    public String resolveAcceptTaskCaseNo(String acceptTaskListHtml) {
        Document doc = Jsoup.parse(acceptTaskListHtml);
        Elements lineheights = doc.getElementsByClass("lineheight1");
        return StringUtils.trim(lineheights.get(0).html());
    }

    /**
     * 从登陆页解析必要参数
     * 
     * @param loginHtml
     * @return
     */
    public String resolveLoginViewstate(String loginHtml) {
        Document doc = Jsoup.parse(loginHtml);
        Element viewstate = doc.getElementById("__VIEWSTATE");
        return viewstate.attr("value");
    }

    /**
     * 解析任务列表
     * 
     * @param tasks
     * @param html
     */
    public void resolveTaskList(Collection<Task> tasks, String html) {
        Document doc = Jsoup.parse(html);
        Elements zb_boxs = doc.getElementsByClass("zb_box");
        for (Element zb_box : zb_boxs) {
            Elements left_first = zb_box.getElementsByClass("left_first");
            if (CollectionUtils.isNotEmpty(left_first)) { // 招标中
                Task task = new Task();
                task.setIsBm(true);

                // 编号
                String no = left_first.get(0).child(0).html();
                task.setBianhao(no.substring(3));

                Element zb_box_center = zb_box.getElementsByClass("zb_box_center").get(0);

                // 楼盘
                String loupan = zb_box_center.select("span.h1").get(0).html();
                task.setLoupan(StringUtils.isBlank(loupan) ? "(无楼盘信息)" : loupan);

                // 户型
                String huxing = zb_box_center.select("li.text").get(2).html();
                if (StringUtils.isNotBlank(huxing)) {
                    huxing = huxing.replace("&nbsp;", "");
                }
                task.setHuxing(StringUtils.isBlank(huxing) ? "(无户型信息)" : huxing);

                // 要求
                Element h = zb_box_center.getElementById("H" + task.getBianhao());
                String yaoqiu = h.childNode(0).toString();
                if (StringUtils.isNotBlank(yaoqiu)) {
                    yaoqiu = yaoqiu.replace("&nbsp;", "");
                }
                task.setYaoqiu(StringUtils.isBlank(yaoqiu) ? "(无特殊要求)" : yaoqiu);

                tasks.add(task);
            }
        }
    }
}
