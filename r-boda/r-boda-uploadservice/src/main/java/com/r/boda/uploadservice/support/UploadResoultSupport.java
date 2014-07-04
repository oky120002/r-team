/**
 * 
 */
package com.r.boda.uploadservice.support;

import java.util.ArrayList;
import java.util.List;

import com.r.boda.uploadservice.upload.model.Upload;

/**
 * @author oky
 * 
 */
public class UploadResoultSupport {
	private String group;
	private List<Upload> uploads = new ArrayList<Upload>();
	private String error;
	private boolean isError = false;

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the uploads
	 */
	public List<Upload> getUploads() {
		return uploads;
	}

	/**
	 * @param uploads
	 *            the uploads to set
	 */
	public void setUploads(List<Upload> uploads) {
		this.uploads = uploads;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * @param isError
	 *            the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
}
