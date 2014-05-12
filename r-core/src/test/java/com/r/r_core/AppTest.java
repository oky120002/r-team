package com.r.r_core;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:*.xml" })
public class AppTest {
	
	private PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

//	private String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

	@Test
	public void test() throws IOException {
		System.out.println(0);
//		Resource[] resources = resolver.getResources("classpath:/menu/*.xml");
		Resource resource = resolver.getResource("classpath:/menu/*.xml");
		System.out.println(resource.getURI());
		
//		for (Resource res : resources) {
//			System.out.println(res.getFile().getName());
//		}
	}
}
