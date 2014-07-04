/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.r.boda.uploadservice.core.EnviromentalParameter;
import com.r.boda.uploadservice.core.UpLoadErrorException;
import com.r.boda.uploadservice.upload.dao.UploadDao;
import com.r.boda.uploadservice.upload.model.FileType;
import com.r.boda.uploadservice.upload.model.Upload;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;
import com.r.core.util.RandomUtil;

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

	@Resource(name = "epar")
	private EnviromentalParameter epar;

	@Resource(name = "passwordEncoder")
	private PasswordEncoder passwordEncoder; // 密码

	/**
	 * 保存上传的文件
	 * 
	 * @param files
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public List<Upload> save(List<MultipartFile> files, String group) throws UpLoadErrorException {
		if (CollectionUtils.isEmpty(files)) {
			return new ArrayList<Upload>();
		}

		List<Upload> failUploads = new ArrayList<Upload>();
		UpLoadErrorException error = null;
		for (MultipartFile multipartFile : files) {
			logger.debug("保存上传的文件 : {}", multipartFile.getOriginalFilename());
			try {
				File uploadFile = getUploadRandomFile();
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);
				Upload up = new Upload();
				up.setFileName(multipartFile.getOriginalFilename());
				up.setFilepath(uploadFile.getAbsolutePath());
				up.setGroup(group);
				up.setIsEnabled(true);
				up.setFileType(FileType.re(multipartFile.getOriginalFilename()));
				uploadDao.create(up);
			} catch (Exception e) {
				if (error == null) {
					error = new UpLoadErrorException("");
				}
				error.addError(multipartFile.getOriginalFilename(), e.getMessage());
			}
		}
		if (error != null) {
			throw error;
		}
		return failUploads;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public void create(Upload up) {
		uploadDao.create(up);
	}

	/**
	 * 根据分组查询上传的文件
	 * 
	 * @param group
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
	public List<Upload> queryByGroup(String group) {
		Upload up = new Upload();
		up.setGroup(group);
		up.setIsEnabled(true);
		return uploadDao.queryByExample(up);
	}

	/**
	 * 根据上传的文件名生成对应的File实体<br />
	 * 
	 * @param fileName
	 * @return
	 */
	private File getUploadRandomFile() {
		String randomFile = RandomUtil.uuid();
		char c1 = randomFile.charAt(0);
		char c2 = randomFile.charAt(1);
		char c3 = randomFile.charAt(2);
		char c4 = randomFile.charAt(3);
		StringBuilder sb = new StringBuilder();
		sb.append(epar.getFileSavePath());
		sb.append(c1).append(File.separatorChar);
		sb.append(c2).append(File.separatorChar);
		sb.append(c3).append(File.separatorChar);
		sb.append(c4).append(File.separatorChar);
		sb.append(randomFile).append(".up");
		return new File(sb.toString());
	}
}
