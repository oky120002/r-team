/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.dao;

import org.springframework.stereotype.Repository;

import com.r.boda.uploadservice.upload.model.Upload;
import com.r.web.support.abs.AbstractDaoImpl;

/**
 * @author rain
 */
@Repository("upload.uploadDao")
public class UploadDaoImpl extends AbstractDaoImpl<Upload> implements UploadDao {
	public UploadDaoImpl() {
		super(Upload.class);
	}
}
