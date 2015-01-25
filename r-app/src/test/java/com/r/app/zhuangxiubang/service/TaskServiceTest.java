package com.r.app.zhuangxiubang.service;

import java.awt.Dimension;
import java.awt.Image;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.app.zhuangxiubang.model.Task;
import com.r.core.desktop.ctrl.alert.HAlert;
import com.r.core.desktop.ctrl.obtain.HImageObtain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:zhuangxiubang/spring.xml" })
public class TaskServiceTest {

    @Resource(name = "taskService")
    private TaskService taskService;

    // @Test
    public void test() {
        String errMsg = taskService.doLogin();

        while (true)
            ;
    }

    @Test
    public void testCheckAutCode() {
        taskService.doLogin();
        List<Task> webGetTaskList = taskService.getWebGetTaskList();

        String sss = HAlert.showAuthCodeDialog(new HImageObtain() {

            @Override
            public Dimension getHImageSize() {
                return new Dimension(200, 100);
            }

            @Override
            public Image getHImage() {
                return taskService.getImage();
            }
        });

        System.out.println(sss);
    }
}
