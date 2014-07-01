/**
 * 
 */
package com.r.boda.uploadservice.upload.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.r.boda.uploadservice.support.listener.FileUploadItem;
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
		System.out.println("uploadpage");
		return "upload/uploadpage";
	}

	/**
	 * 上传应答入口
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploads")
	public String uploads(ModelMap model, MultipartHttpServletRequest request) {
		String uploadname = request.getParameter("uploadname");
		for (MultipartFile file : request.getFiles(uploadname)) {
			System.out.println(file.getOriginalFilename());
		}
		return "upload/uploadpage";
	}

	/**
	 * 上传状态(进度条)
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "fileUploadStatus")
	public @ResponseBody
	FileUploadItem fileUploadStatus(ModelMap model, HttpServletRequest request) {
		FileUploadItem fileUploadItemFromRequest = FileUploadItem.getFileUploadItemFromRequest(request.getSession());
		System.out.println(fileUploadItemFromRequest);
		return fileUploadItemFromRequest;
	}
}
