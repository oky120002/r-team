/**
 * 
 */
package com.r.boda.uploadservice.support.listener;

import javax.servlet.http.HttpSession;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author oky
 * 
 */
public class FileUploadItem {
	private int itemNumber;
	private long bytesRead;
	private long contentLength;
	private int percent;

	/**
	 * @return the itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}

	/**
	 * @return the bytesRead
	 */
	public long getBytesRead() {
		return bytesRead;
	}

	/**
	 * @return the contentLength
	 */
	public long getContentLength() {
		return contentLength;
	}

	/**
	 * @return the percent
	 */
	public int getPercent() {
		return percent;
	}

	public void setFileUploadItemData(long pBytesRead, long pContentLength, int pItems) {
		this.bytesRead = pBytesRead;
		this.contentLength = pContentLength;
		this.itemNumber = pItems;
		
		double b = pBytesRead;
		double c = pContentLength;
		
		this.percent = (int) ((b / c) * 100);
	}

	@Override
	public String toString() {
		return MessageFormatter.arrayFormat("正在上传第{}个文件,整体上传进度为 : {}/{}   {}%", new String[] { String.valueOf(this.itemNumber), String.valueOf(this.bytesRead), String.valueOf(this.contentLength), String.valueOf(this.percent) }).getMessage();
	}

	/**
	 * 从request中获取到FileUploadItem
	 * 
	 * @param session
	 * @return
	 */
	public static FileUploadItem getFileUploadItemFromRequest(HttpSession session) {
		if (session == null) {
			return null;
		}
		return (FileUploadItem) session.getAttribute(FileUploasListener.FILE_UPLOAD_KEY);
	}

}
