/**
 * 
 */
package com.r.boda.uploadservice.upload.control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.r.boda.uploadservice.support.Support;
import com.r.boda.uploadservice.support.listener.FileUploadItem;
import com.r.boda.uploadservice.upload.model.Upload;
import com.r.boda.uploadservice.upload.service.UploadService;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadControl {
	private static final Logger logger = LoggerFactory.getLogger(UploadControl.class);

	@Resource(name = "upload.uploadService")
	private UploadService uploadService;

	public UploadControl() {
		logger.info("Instance UploadControl............................");
	}

	/**
	 * 主页
	 * 
	 * @param model
	 * @return 页面路径
	 */
	@RequestMapping(value = "index")
	public String index(ModelMap model, HttpServletRequest request) {
		return "index";
	}

	/**
	 * 上传页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploadpage")
	public String uploadPage(ModelMap model, HttpServletRequest request) {
		logger.debug("uploadpage");
		String group = request.getParameter("uploadgroup");
		List<Upload> uploads = uploadService.queryByGroup(group);
		if (CollectionUtils.isNotEmpty(uploads)) {
			model.put("uploads", uploads);
			model.put("isok", true);
		} else {
			model.put("isok", false);
		}
		return "upload/uploadpage";
	}

	/**
	 * 上传页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "test")
	@ResponseBody
	public Support<Object> test(ModelMap model, HttpServletRequest request) {
		Support<Object> support = new Support<Object>();
		support.putParam("qabc", "0000");
		return support;
	}

	/**
	 * 上传应答入口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploads")
	@ResponseBody
	public Support<Upload> uploads(ModelMap model, MultipartHttpServletRequest request) {
		logger.debug("开始上传");
		Support<Upload> support = new Support<Upload>();
		String uploadname = request.getParameter("uploadname");
		try {
			List<Upload> uploads = uploadService.save(request.getFiles(uploadname), request.getParameter("uploadgroup"));
			if (CollectionUtils.isNotEmpty(uploads)) {
				support.putParam("group", uploads.get(0).getGroup());
			}
			support.setSuccess(true);
			support.setEntities(uploads);
			support.setTips("上传成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件错误 : {}", e.getMessage());
			support.setSuccess(false);
			support.setTips(e.getMessage());
		}
		return support;
	}

	/**
	 * 上传状态(进度条)
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "fileUploadStatus")
	@ResponseBody
	public Support<FileUploadItem> fileUploadStatus(ModelMap model, HttpServletRequest request) {
		Support<FileUploadItem> support = new Support<FileUploadItem>();
		support.setModel(FileUploadItem.getFileUploadItemFromRequest(request.getSession()));
		logger.debug("上传文件进度 : " + support.getModel());
		return support;
	}

	/**
	 * 删除上传的文件
	 * 
	 * @param isDeleteFile
	 *            是否删除对应的硬盘上的物理文件
	 * @param fileId
	 *            文件id
	 * @return
	 */
	@RequestMapping(value = "deleteUpload/{isDeleteFile}/{fileId}")
	@ResponseBody
	public Support<Object> deleteUpload(@PathVariable Boolean isDeleteFile, @PathVariable String fileId, HttpServletRequest request) {
		Support<Object> support = new Support<Object>();
		try {
			uploadService.deleteFile(fileId, Boolean.valueOf(isDeleteFile).booleanValue());
			support.setSuccess(true);
			support.setTips("删除成功");
		} catch (Exception e) {
			support.setSuccess(false);
			support.setTips("删除失败!" + e.getMessage());
		}
		return support;
	}

	/**
	 * 查看上传的文件
	 * 
	 * @param fileId
	 *            文件id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "lookUploadFile/{fileId}")
	public void lookUploadFile(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Upload upload = uploadService.find(fileId);
//		download(request, response, upload);
		// Support<Object> support = new Support<Object>();
	}
	
	/**
	 * 下载文件
	 * 
	 * @param fileId
	 *            文件id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "downloadFile/{fileId}")
	public void downloadFile(@PathVariable String fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Upload upload = uploadService.find(fileId);
		download(request, response, upload);
		// Support<Object> support = new Support<Object>();
	}
	
	

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param upload
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, Upload upload) throws IOException {
		response.setContentType(upload.getFileType().getContentType());
		response.setHeader("Content-disposition", "attachment; filename=" + upload.getFileName());
		response.setHeader("Content-Length", upload.getFileLength().toString());

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(upload.getFilePath()));
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(bos);
	}
}
