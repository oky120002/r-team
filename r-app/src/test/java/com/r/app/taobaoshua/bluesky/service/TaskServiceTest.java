package com.r.app.taobaoshua.bluesky.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.app.taobaoshua.bluesky.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TaskServiceTest {

	@Resource(name = "taskService")
	private TaskService taskService;

	@Test
	public void test() {
		List<Task> queryAll = taskService.queryAll();
	
		for (Task t : queryAll) {
			System.out.println(t.getAccount());
			System.out.println(t.getAddr());
		}
		
	}

}
