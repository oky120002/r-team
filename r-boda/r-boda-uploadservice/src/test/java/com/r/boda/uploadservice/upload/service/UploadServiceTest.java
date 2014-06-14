package com.r.boda.uploadservice.upload.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class UploadServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(UploadServiceTest.class); // 日志
	
	@Resource(name = "upload.uploadService")
	private UploadService uploadService;
	
	@Test
	public void test() {
		
	}

}