package com.r.core.util;

import org.junit.Test;

public class IDCardUtilTest {

	@Test
	public void test() {
		System.out.println("0 : " + IDCardUtil.getIDCard("500107198706192602").isValidity());
		System.out.println("1 : " + IDCardUtil.getIDCard("500107198706192612").isValidity());
		System.out.println("2 : " + IDCardUtil.getIDCard("500107198706192622").isValidity());
		System.out.println("3 : " + IDCardUtil.getIDCard("500107198706192632").isValidity());
		System.out.println("4 : " + IDCardUtil.getIDCard("500107198706192642").isValidity());
		System.out.println("5 : " + IDCardUtil.getIDCard("500107198706192652").isValidity());
		System.out.println("6 : " + IDCardUtil.getIDCard("500107198706192662").isValidity());
		System.out.println("7 : " + IDCardUtil.getIDCard("500107198706192672").isValidity());
		System.out.println("8 : " + IDCardUtil.getIDCard("500107198706192682").isValidity());
		System.out.println("9 : " + IDCardUtil.getIDCard("500107198706192692").isValidity());
	}

}
