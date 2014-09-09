package com.r.app.zhuangxiubang.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.app.zhuangxiubang.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:zhuangxiubang/spring.xml" })
public class TaskServiceTest {

    @Resource(name = "taskService")
    private TaskService taskService;

    @Test
    public void test() {
        List<Task> tasks = new ArrayList<Task>();
        taskService.webGetTaskList(tasks);

        for (Task task : tasks) {
            System.out.println(task.getLoupan());
        }
    }

}
