/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-3
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.dao;

import org.springframework.stereotype.Repository;

import com.r.boda.uploadservice.core.AbstractDaoImpl;
import com.r.boda.uploadservice.upload.model.Upload;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author rain
 */
@Repository("upload.uploadDao")
public class UploadDaoImpl extends AbstractDaoImpl<Upload> implements UploadDao {

	private static final Logger logger = LoggerFactory.getLogger(UploadDaoImpl.class); // 日志

	public UploadDaoImpl() {
		super(Upload.class);
		logger.info("Instance UploadDaoImpl............................");
	}
}
