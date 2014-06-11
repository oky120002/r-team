/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.service;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.r.boda.uploadservice.upload.dao.UploadDao;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("upload.uploadService")
public class UploadService {
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class); // 日志

	public UploadService() {
		super();
		logger.info("Instance UploadService............................");
	}

	@Resource(name = "upload.uploadDao")
	private UploadDao uploadDao; // 用户Dao

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder; // 密码
	
	
}
