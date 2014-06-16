package com.r.app.taobaoshua.bluesky.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.app.taobaoshua.bluesky.dao.TaskDao;
import com.r.app.taobaoshua.bluesky.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TaskServiceTest {

	@Resource(name = "taskService")
	private TaskService taskService;

	@Resource(name = "taskDao")
	private TaskDao taskDao;

	@Test
	public void test() {
		List<Task> queryAll = taskService.queryAll();

		for (Task t : queryAll) {
			System.out.println(t.getAccount());
			System.out.println(t.getAddr());
			System.out.println(t.getStatus());
			t.setAccount("账号 : " + t.hashCode());
			t.setPublishingPoint(362.7);
			taskService.update(t);
		}
		
		taskService.update(" update Task set status = '已接手等待付款' where status = '未接手' ");
		
		queryAll = taskService.queryAll();
		for (Task t : queryAll) {
			System.out.println(t.getAccount());
			System.out.println(t.getAddr());
			System.out.println(t.getStatus());
			System.out.println(t.getPublishingPoint().getClass());
			System.out.println(t.getPublishingPoint());
			t.setAccount("账号 : " + t.hashCode());
			taskService.update(t);
		}
	}

}
