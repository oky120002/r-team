package com.r.app.taobaoshua.bluesky.websource;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class BlueSkyResolveTest {

	@Test
	public void test() throws IOException {
//		String html = FileUtils.readFileToString(new File("./page/tasklist.txt"));
		
		
//		System.out.println(html);
	}
	
	@Test
	public void test2(){
		String   str = "abcd/>dcba";
		System.out.println(str.split("/>")[1]);
	}

}
