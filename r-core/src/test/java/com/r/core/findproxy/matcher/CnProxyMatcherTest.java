package com.r.core.findproxy.matcher;

import static org.junit.Assert.*;

import java.net.Proxy;
import java.util.List;

import org.junit.Test;

public class CnProxyMatcherTest {

	@Test
	public void test() {
		CnProxyMatcher matcher = new CnProxyMatcher();
		List<Proxy> proxys = matcher.getProxys();
	}

}
