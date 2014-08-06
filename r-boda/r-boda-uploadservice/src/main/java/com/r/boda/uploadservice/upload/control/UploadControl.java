/**
 * 
 */
package com.r.boda.uploadservice.upload.control;

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
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.r.boda.uploadservice.core.AbstractControl;
import com.r.boda.uploadservice.core.EnviromentalParameter;
import com.r.boda.uploadservice.core.UpLoadErrorException;
import com.r.boda.uploadservice.support.Support;
import com.r.boda.uploadservice.support.listener.FileUploadItem;
import com.r.boda.uploadservice.upload.model.FileType;
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
public class UploadControl extends AbstractControl {
    private static final Logger logger = LoggerFactory.getLogger(UploadControl.class);

    private static final String PARAMS_UPLOAD_GROUP = "uploadgroup"; // 附件分组
    private static final String PARAMS_TAGS = "tags"; // 附件标签
    private static final String PARAMS_SYSNAME = "sysname"; // 附件-系统

    @Resource(name = "upload.uploadService")
    private UploadService uploadService;

    @Resource(name = "epar")
    private EnviromentalParameter epar;
    
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
     * 此方法主要解决showUploadDialog调用跨域获取不到值的问题.通过全局session来解决
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "sessionparams")
    @ResponseBody
    public Support<Object> sessionparams(HttpServletRequest request) {
        Support<Object> support = new Support<Object>();
        HttpSession session = request.getSession();
        session.setAttribute(PARAMS_TAGS, request.getParameter(PARAMS_TAGS));
        session.setAttribute(PARAMS_UPLOAD_GROUP, request.getParameter(PARAMS_UPLOAD_GROUP));
        session.setAttribute(PARAMS_SYSNAME, request.getParameter("PARAMS_SYSNAME"));
        support.putParam("url", epar.getAddr("/upload/uploadpage")); // 跳转的链接
        return support;
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
        HttpSession session = request.getSession();

        // 附件-系统
        model.put(PARAMS_SYSNAME, String.valueOf(session.getAttribute(PARAMS_SYSNAME)));

        // 标签
        String tags = String.valueOf(session.getAttribute(PARAMS_TAGS));
        String[] splitTags = tags.split(",");
        StringBuilder t = new StringBuilder();
        t.append('[');
        for (String s : splitTags) {
            t.append('\'').append(s).append('\'').append(", ");
        }
        t.append(']');
        model.put(PARAMS_TAGS, t.toString());

        // 上传文件分组
        String uploadgroup = String.valueOf(session.getAttribute(PARAMS_UPLOAD_GROUP));
        model.put(PARAMS_UPLOAD_GROUP, uploadgroup);

        List<Upload> uploads = uploadService.queryByGroup(uploadgroup); // 附件列表
        model.put("isok", false); // 是否已经存在附件
        model.put("havetags", "[]"); // 已经存在的标签
        if (CollectionUtils.isNotEmpty(uploads)) {
            model.put("uploads", uploads);
            model.put("isok", true);

            // 保存已经存在的标签
            StringBuilder havetags = new StringBuilder();
            havetags.append('[');
            for (Upload upload : uploads) {
                if (StringUtils.isNotBlank(upload.getTag())) {
                    havetags.append('\'').append(upload.getTag()).append('\'').append(", ");
                }
            }
            havetags.append(']');
            model.put("havetags", havetags.toString());
        }
        return "upload/uploadpage";
    }

    /**
     * 上传应答入口
     * 
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "uploads")
    public void uploads(ModelMap model, MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
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

            String sysname = String.valueOf(session.getAttribute(PARAMS_SYSNAME));
            List<Upload> uploads = uploadService.save(multipartFiles, sysname, tags, request.getParameter("uploadgroup"));
            if (CollectionUtils.isNotEmpty(uploads)) {
                support.putParam("group", uploads.get(0).getGroup());
            }
            support.setSuccess(true);
            support.setTips("上传成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件错误 : {}", e.getMessage());
            support.setSuccess(false);
            support.setTips(e.getMessage());
        }
        // 这个里不能通过返回String类型或者Object类型(Object会自动转换成json字符串)来返回.前者有乱码问题,后者IE8不能识别,会自动弹出下载框
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print(JSONObject.fromObject(support).toString());
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
    public Support<FileUploadItem> fileUploadStatus(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
        response.setHeader("Content-Length", String.valueOf(upload.getFile().length()));
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
        } catch (UpLoadErrorException e) {
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
    @ResponseBody
    public Support<Object> deletePageByPdf(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, @PathVariable Integer end, HttpServletRequest request) {
        Support<Object> support = new Support<Object>();
        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
            uploadService.pdfDeletePage(upload.getFile(), start, end);
            support.putParam("pdfNumber", uploadService.pdfPageNumber(upload.getFile()));
        } catch (UpLoadErrorException e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
            return support;
        }

        support.setTips("删除成功");
        return support;
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
            return "upload/insertPdfPage";
        } catch (UpLoadErrorException e) {
            logger.error("pdf删除页面 : {}", e.getMessage());
            model.put("error", " 附件平台内部错误 : " + e.toString());
            return "upload/errorPdfPage";
        }
    }

    /**
     * 插入pdf页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "insertPdfPage/{fileId}/{start}")
    @ResponseBody
    public Support<Object> insertPdfPage(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, MultipartHttpServletRequest request) {
        Support<Object> support = new Support<Object>();
        Map<String, MultipartFile> fileMap = request.getFileMap();
        if (MapUtils.isEmpty(fileMap)) {
            support.setSuccess(false);
            support.setTips("请选择要插入的pdf文档");
            return support;
        }

        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
            uploadService.pdfInsertPage(upload.getFile(), fileMap.values().iterator().next(), start);
            support.putParam("pdfNumber", uploadService.pdfPageNumber(upload.getFile()));
        } catch (UpLoadErrorException e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
            return support;
        }

        support.setTips("插入成功");
        return support;
    }
}
