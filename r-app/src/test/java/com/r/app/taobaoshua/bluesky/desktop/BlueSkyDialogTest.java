package com.r.app.taobaoshua.bluesky.desktop;

import org.junit.Test;

import com.r.app.taobaoshua.TaobaoShuaApp;
import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.core.desktop.ctrl.HCtrlUtil;

public class BlueSkyDialogTest {

	@Test
	public void test() {
		HCtrlUtil.setNoSpot();
		HCtrlUtil.setUIFont(null);
		HCtrlUtil.setWindowsStyleByWindows(null);
		TaobaoShuaApp.startup(); // 启动

		BlueSky blueSky = TaobaoShuaApp.getInstance().getBlueSky();
		blueSky.startup();
		blueSky.getDialog().setVisible(true);
		
		while(true);
	}

}
