/**
 * 
 */
package com.r.boda.uploadservice.support.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * @author oky
 * 
 */
public class FileUploasListener implements ProgressListener {

	public static final String FILE_UPLOAD_KEY = "FILE_UPLOAD_KEY";

	private FileUploadItem fileUploadItem;
	private HttpSession session;

	public FileUploasListener(HttpSession session) {
		this.session = session;
		this.fileUploadItem = new FileUploadItem();
		this.session.setAttribute(FILE_UPLOAD_KEY, this.fileUploadItem);
	}

	public void update(long pBytesRead, long pContentLength, int pItems) {
		if (pContentLength == -1) {
			this.fileUploadItem.setFileUploadItemData(-1, -1, -1);
		} else {
			this.fileUploadItem.setFileUploadItemData(pBytesRead, pContentLength, pItems);
		}
	}
}
