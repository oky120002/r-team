/**
 * 
 */
package com.r.boda.uploadservice.support.listener;

import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

/**
 * @author oky
 * 
 */
@Component("fileUploasListener")
public class FileUploasListener implements ProgressListener {

	private double megaBytes = -1;
	private HttpSession session;

	public FileUploasListener() {
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void update(long pBytesRead, long pContentLength, int pItems) {
		double mBytes = pBytesRead / 1000000;
		double total = pContentLength / 1000000;
		if (megaBytes == mBytes) {
			return;
		}
		megaBytes = mBytes;
		if (pContentLength == -1) {
			System.out.println("So far, " + pBytesRead + " bytes have been read.");
		} else {
			double read = (mBytes / total);
			NumberFormat nf = NumberFormat.getPercentInstance();
//			session.setAttribute("read", nf.format(read));
			session.setAttribute("read", (int)(read * 1000));
		}
	}

}
