package com.r.app.taobaoshua.bluesky.websource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.app.taobaoshua.bluesky.core.BlueSkyResolve;
import com.r.app.taobaoshua.bluesky.model.Task;
import com.r.app.taobaoshua.bluesky.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class BlueSkyResolveTest {

	@Resource(name = "taskService")
	private TaskService taskService;

	@Test
	public void test() throws IOException {
		String html = FileUtils.readFileToString(new File("./page/tasklist.txt"));
		BlueSkyResolve r = new BlueSkyResolve();
		List<Task> tasks = new ArrayList<Task>();
		r.resolveTaskList(tasks, html);
		for (Task task : tasks) {
			System.out.println(task.getNumber());
		}
		taskService.updateTaskList(tasks);
	}

}
