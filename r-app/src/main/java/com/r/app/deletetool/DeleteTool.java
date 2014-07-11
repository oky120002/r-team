/**
 * 
 */
package com.r.app.deletetool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.TaskUtil;

/**
 * 删除工具
 * 
 * @author oky
 * 
 */
public class DeleteTool {
	private static final Logger logger = LoggerFactory.getLogger(DeleteTool.class, "删除工具 - ");

	private static AtomicLong num = new AtomicLong(0);

	public static void main(String[] args) {
		DeleteTool.start();
	}

	private static void start() {
		File file = new File("./datas/1.txt");
		try {
			List<String> readLines = FileUtils.readLines(file);
			for (final String string : readLines) {
//				TaskUtil.executeTask(new Runnable() {
//					@Override
//					public void run() {
						new DeleteTool().deleteFiles(new File(string.trim()));
//					}
//				}); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteFiles(File file) {
		if (file != null && file.exists()) {
			if (num.get() % 100 == 0) {
				logger.debug("删除文件(文件夹)[{}]个", num.get());
			}

			if (file.isFile()) {
				logger.debug(file.getPath());
				file.delete();
				num.incrementAndGet();
				return;
			} else if (file.isDirectory()) {
				File[] listFiles = file.listFiles();
				if (ArrayUtils.isEmpty(listFiles)) {
					file.delete();
					num.incrementAndGet();
					return;
				}
				for (File f : listFiles) {
					deleteFiles(f);
				}
				num.incrementAndGet();
				logger.debug(file.getPath());
				file.delete();
				return;
			} else {
				logger.debug("未知文件 : {}", file.getPath());
			}
		}
	}

}
