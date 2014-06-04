/**
 * 
 */
package com.r.app.taobaoshua.yuuboo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.r.app.taobaoshua.yuuboo.model.PVQuest;

/**
 * @author oky
 *
 */
public class YuuBooResolveTest {
	
	private YuuBooResolve resolve = new YuuBooResolve();

	@Test
	public void test() throws IOException {
		String html = FileUtils.readFileToString(new File("./page/taobaolistpage.txt"));
		PVQuest quest = new PVQuest();
		quest.setLocation("郑州");
		quest.setPrice(50, 60);
		quest.setSearchKey("牛仔七分裤女 夏薄款百搭");
		quest.setShopKeeper("zh******g淘");
		List<String> resolveBaoBeiUrl = resolve.resolveBaoBeiUrl(quest, html);
		for (String string : resolveBaoBeiUrl) {
			System.out.println(string);
		}
		
		
	}

}
