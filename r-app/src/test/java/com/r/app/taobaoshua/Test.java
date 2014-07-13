package com.r.app.taobaoshua;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Test {

	@org.junit.Test
	public void test() throws UnsupportedEncodingException {
		File file = new File("./datas/2.txt");
		try {

			List<String> readLines = FileUtils.readLines(file);
			List<String> ss = new ArrayList<String>();

			int index = 0;
			for (int i = 1160; i < 1226; i++) {
				if (index > 31) {
					index = 0;
				}
				StringBuffer sb = new StringBuffer();
				sb.append(index++).append('\t').append(i).append('\t').append(readLines.contains(String.valueOf(i)) ? "1" : "0");
				ss.add(sb.toString());
			}

			FileUtils.writeLines(new File("./datas/3.txt"), ss);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
