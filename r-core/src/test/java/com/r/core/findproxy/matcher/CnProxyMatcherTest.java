package com.r.core.findproxy.matcher;

import org.junit.Test;
import org.quartz.SchedulerException;

import com.r.core.findproxy.context.FindProxyContext;

public class CnProxyMatcherTest {

	@Test
	public void test() throws SchedulerException {
		FindProxyContext findProxy = new FindProxyContext();
		findProxy.init();
		findProxy.execOne();
		while(true);
	}

}
