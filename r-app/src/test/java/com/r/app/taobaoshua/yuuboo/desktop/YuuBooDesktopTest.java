package com.r.app.taobaoshua.yuuboo.desktop;

import org.junit.Test;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.yuuboo.YuuBoo;
import com.r.core.desktop.ctrl.HCtrlUtil;

public class YuuBooDesktopTest {

	@Test
	public void test() {
		HCtrlUtil.setNoSpot();
		HCtrlUtil.setUIFont(null);
		HCtrlUtil.setWindowsStyleByWindows(null);
		TaobaoShuaApp.startup(); // 启动

		YuuBoo yuuBoo = TaobaoShuaApp.getInstance().getYuuBoo();
		System.out.println(yuuBoo.getCaptcha());
	}

}
