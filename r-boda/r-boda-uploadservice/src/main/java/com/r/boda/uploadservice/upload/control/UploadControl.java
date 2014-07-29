/**
 * 
 */
package com.r.boda.uploadservice.upload.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.r.boda.uploadservice.core.UpLoadErrorException;
import com.r.boda.uploadservice.support.Support;
import com.r.boda.uploadservice.support.listener.FileUploadItem;
import com.r.boda.uploadservice.upload.model.FileType;
import com.r.boda.uploadservice.upload.model.FileType.ResponseDataType;
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

        // 上传文件分组
        String uploadgroup = request.getParameter("uploadgroup");
        model.put("uploadgroup", uploadgroup);

        List<Upload> uploads = uploadService.queryByGroup(uploadgroup);

        // 列表
        model.put("isok", false);
        model.put("havetags", "[]");
        if (CollectionUtils.isNotEmpty(uploads)) {
            model.put("uploads", uploads);
            model.put("isok", true);

            // 保存已经存在的标签
            StringBuilder tags = new StringBuilder();
            tags.append('[');
            for (Upload upload : uploads) {
                if (StringUtils.isNotBlank(upload.getTag())) {
                    tags.append('\'').append(upload.getTag()).append('\'').append(", ");
                }
            }
            tags.append(']');
            model.put("havetags", tags.toString());
        }
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
    @ResponseBody
    public Support<Upload> uploads(ModelMap model, MultipartHttpServletRequest request) {
        logger.debug("开始上传");
        Support<Upload> support = new Support<Upload>();
        try {
            Map<String, MultipartFile> fileMap = request.getFileMap();
            List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
            List<String> tags = new ArrayList<String>();
            for (Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                multipartFiles.add(entry.getValue());
                tags.add(request.getParameter(entry.getKey() + "_tag"));
            }

            List<Upload> uploads = uploadService.save(multipartFiles, tags, request.getParameter("uploadgroup"));
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
        Upload upload = uploadService.find(fileId);
        response.setContentType(upload.getFileType().getContentType());
        InputStream bis = null;
        OutputStream bos = null;
        try {
            bis = new FileInputStream(upload.getFilePath());
            bos = response.getOutputStream();
            IOUtils.copy(bis, bos);
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }
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
        response.setContentType(upload.getFileType().getContentType());
        response.setHeader("Content-Disposition", "attachment;fileName=" + upload.getFileName());
        InputStream bis = null;
        OutputStream bos = null;
        try {
            bis = new FileInputStream(upload.getFilePath());
            bos = response.getOutputStream();
            IOUtils.copy(bis, bos);
        } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }
    }

    /**
     * pdf删除页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deletePageByPdf/{fileId}")
    public String deletePageByPdf(ModelMap model, @PathVariable String fileId, HttpServletRequest request) {
        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
        } catch (UpLoadErrorException e) {
            model.put("error", e.getMessage());
            return "upload/errorPdfPage";
        }
        try {
            int pdfPageNumber = uploadService.pdfPageNumber(upload.getFile());
            model.put("fileId", fileId);
            model.put("pdfPageNumber", pdfPageNumber);
            return "upload/deletePdfPage";
        } catch (IOException e) {
            logger.error("pdf删除页面 : {}", e.getMessage());
            model.put("error", " 附件平台内部错误 : " + e.toString());
            return "upload/errorPdfPage";
        }
    }

    /**
     * 删除pdf页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deletePageByPdf/{fileId}/{start}/{end}")
    public Support<Object> deletePageByPdf(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, @PathVariable Integer end, HttpServletRequest request) {
        Support<Object> support = new Support<Object>();
        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
        } catch (UpLoadErrorException e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
            return support;
        }

        uploadService.pdfDeletePage(upload.getFile(), start, end);
        
        support.setTips("删除成功");
        return new Support<Object>();
    }

    /**
     * pdf插入页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "insertPdfPage/{fileId}")
    public String insertPdfPage(ModelMap model, @PathVariable String fileId, HttpServletRequest request) {
        Upload upload = uploadService.find(fileId);
        try {
            if (!ResponseDataType.pdf.equals(upload.getFileType().getResponseDataType())) {
                model.put("errorMsg", "此文件不是PDF文件");
                return "upload/errorPage";
            }
        } catch (NullPointerException npe) {
            model.put("errorMsg", "系统错误,请联系管理员");
            return "upload/errorPage";
        }

        return "upload/insertPdfPage";
    }

    /**
     * 插入pdf页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "insertPdfPage/{fileId}/{start}/{end}")
    public Support<Object> insertPdfPage(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, @PathVariable Integer end, MultipartHttpServletRequest request) {
        Upload upload = uploadService.find(fileId);
        // try {
        // if
        // (!ResponseDataType.pdf.equals(upload.getFileType().getResponseDataType()))
        // {
        // model.put("errorMsg", "此文件不是PDF文件");
        // return "upload/errorPage";
        // }
        // } catch (NullPointerException npe) {
        // model.put("errorMsg", "系统错误,请联系管理员");
        // return "upload/errorPage";
        // }

        return new Support<Object>();
    }
}
