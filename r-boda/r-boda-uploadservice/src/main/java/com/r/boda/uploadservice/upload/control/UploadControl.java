/**
 * 
 */
package com.r.boda.uploadservice.upload.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.kingdee.boda.facade.loan.LoanBillFacade;
import com.r.boda.uploadservice.core.EnviromentalParameter;
import com.r.boda.uploadservice.core.UpLoadErrorException;
import com.r.boda.uploadservice.support.listener.FileUploadItem;
import com.r.boda.uploadservice.upload.model.FileType;
import com.r.boda.uploadservice.upload.model.Upload;
import com.r.boda.uploadservice.upload.service.UploadService;
import com.r.web.support.abs.AbstractControl;
import com.r.web.support.bean.Support;

/**
 * 支持视图控制器
 * 
 * @author oky
 * 
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadControl extends AbstractControl {
    private static final String PARAMS_UPLOAD_GROUP = "group"; // 附件分组
    private static final String PARAMS_TAGS = "tags"; // 附件标签
    private static final String PARAMS_SYSNAME = "sysname"; // 附件-系统
    private static final String PARAMS_EMP = "emp";// 操作人
    private static final String PARAMS_AUT = "aut"; // 权限

    @Resource(name = "upload.uploadService")
    private UploadService uploadService;

    @Resource(name = "epar")
    private EnviromentalParameter epar;

    @Resource(name = "loanBillFacade")
    private LoanBillFacade facade;
    private HttpSession session;

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
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
        String tags = request.getParameter(PARAMS_TAGS);
        session.setAttribute(PARAMS_TAGS, tags);
        session.setAttribute(PARAMS_UPLOAD_GROUP, request.getParameter(PARAMS_UPLOAD_GROUP));
        session.setAttribute(PARAMS_SYSNAME, request.getParameter(PARAMS_SYSNAME));
        session.setAttribute(PARAMS_EMP, request.getParameter(PARAMS_EMP));
        // 权限
        if (StringUtils.isNotBlank(tags)) {
            Map<String, Integer> autMap = new HashMap<String, Integer>();
            String[] split = tags.split(",");
            for (String tag : split) {
                String aut = request.getParameter(PARAMS_AUT + "[" + tag + "]");
                if (StringUtils.isNotBlank(aut)) {
                    autMap.put(tag, Integer.valueOf(aut));
                } else {
                    autMap.put(tag, Integer.MIN_VALUE);
                }
            }
            session.setAttribute(PARAMS_AUT, autMap);
        }

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
        logger.debug("进入上传页面");
        session = request.getSession();

        model.put(PARAMS_SYSNAME, String.valueOf(session.getAttribute(PARAMS_SYSNAME))); // 附件-系统
        @SuppressWarnings("unchecked")
        Map<String, Integer> autMap = (Map<String, Integer>) session.getAttribute(PARAMS_AUT); // 权限

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

            StringBuilder havetags = new StringBuilder();
            havetags.append('[');
            for (Upload upload : uploads) {
                // 保存已经存在的标签
                if (StringUtils.isNotBlank(upload.getTag())) {
                    havetags.append('\'').append(upload.getTag()).append('\'').append(", ");
                }
                // 权限
                if (autMap.containsKey(upload.getTag())) {
                    int intValue = autMap.get(upload.getTag()).intValue();
                    if(intValue == 0){  // 为0则不显示上传按钮,不在列表里面显示这一行
                        havetags.append('\'').append(upload.getTag()).append('\'').append(", ");
                    }
                    upload.setAut(intValue);
                } else {
                    upload.setAut(Integer.MIN_VALUE);
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
        response.setContentType("text/html; charset=utf-8");
        HttpSession session = request.getSession();
        logger.debug("开始上传");
        Support<Upload> support = new Support<Upload>();
        try {
            Map<String, MultipartFile> fileMap = request.getFileMap();
            List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
            List<String> tags = new ArrayList<String>();
            for (Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile file = entry.getValue();
                if (!file.isEmpty()) {
                    logger.debug("上传文件[{}] 大小 : {} B .", file.getOriginalFilename(), file.getSize());
                    FileType fileType = FileType.re(file.getOriginalFilename());
                    // 校验文件格式
                    switch (fileType) {
                    case gif:
                    case jpeg:
                    case jpg:
                    case png:
                    case pdf:
                        break;
                    default:
                        support.setSuccess(false);
                        support.setTips("不支持您上传的文件!");
                        response.getWriter().print(JSONObject.fromObject(support).toString());
                        return;
                    }

                    multipartFiles.add(file);
                    tags.add(request.getParameter(entry.getKey() + "_tag"));
                }
            }

            if (CollectionUtils.isNotEmpty(multipartFiles)) {
                String sysname = String.valueOf(session.getAttribute(PARAMS_SYSNAME));
                List<Upload> uploads = uploadService.save(multipartFiles, sysname, tags, request.getParameter("uploadgroup"));
                if (CollectionUtils.isNotEmpty(uploads)) {
                    support.putParam("group", uploads.get(0).getGroup());
                }
                support.setSuccess(true);
                support.setTips("上传成功!");
            } else {
                support.setSuccess(false);
                support.setTips("不能上传空文件!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            support.setSuccess(false);
            support.setTips(e.getMessage());
        }
        // 这个里不能通过返回String类型或者Object类型(Object会自动转换成json字符串)来返回.前者有乱码问题,后者IE8不能识别,会自动弹出下载框
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
    @RequestMapping(value = "deleteUpload/{fileId}")
    @ResponseBody
    public Support<Object> deleteUpload(@PathVariable String fileId, HttpServletRequest request) {
        Support<Object> support = new Support<Object>();
        try {
            uploadService.unEnabledFile(fileId);
            support.setSuccess(true);
            support.setTips("删除成功");

            Upload file = uploadService.find(fileId);
            String group = file.getGroup();
            String tag = file.getTag();
            String emp = String.valueOf(request.getSession().getAttribute(PARAMS_EMP));
            facade.persisApplyNote(group, tag, emp);
        } catch (Exception e) {
            support.setSuccess(false);
            support.setTips("删除失败!" + e.getMessage());
            logger.error(e.getMessage(), e);
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
     * 下载文件,通过 group和tag下载文件
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String group = request.getParameter("group");
        String tag = request.getParameter("tag");
        List<Upload> uploads = null;
        if (StringUtils.isNotBlank(tag)) {
            uploads = uploadService.queryByGroupAndTag(group, tag);
        } else {
            uploads = uploadService.queryByGroup(group);
        }
        if (CollectionUtils.isNotEmpty(uploads) && uploads.size() == 1) {
            Upload upload = uploads.get(0);
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
        } else {
            // 没有找到对应的附件
        }
    }

    /**
     * 删除pdf页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws Exception
     * @throws IOException
     */
    @RequestMapping(value = "deletePageByPdf/{fileId}/{start}/{end}")
    @ResponseBody
    public Support<Object> deletePageByPdf(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, @PathVariable Integer end, HttpServletRequest request) throws Exception {
        Support<Object> support = new Support<Object>();
        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
            uploadService.pdfDeletePage(upload.getFile(), start, end);
            support.putParam("pdfNumber", uploadService.pdfPageNumber(upload.getFile()));

            Upload file = uploadService.find(fileId);
            String group = file.getGroup();
            String tag = file.getTag();
            String emp = String.valueOf(request.getSession().getAttribute(PARAMS_EMP));
            facade.persisApplyNote(group, tag, emp);
        } catch (UpLoadErrorException e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
            return support;
        }

        support.setTips("删除成功");
        return support;
    }

    /**
     * 判断附件是否纯在
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "isExist")
    public void isExist(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String group = request.getParameter("group");
        String tag = request.getParameter("tag");
        response.setContentType("text/html; charset=utf-8");
        List<Upload> uploads = null;
        try {
            if (StringUtils.isBlank(tag)) {
                uploads = uploadService.queryByGroup(group);
            } else {
                uploads = uploadService.queryByGroupAndTag(group, tag);
            }
        } catch (Exception e) {
            response.getWriter().print("查询附件失败 : " + e.toString());
            return;
        }
        if (CollectionUtils.isNotEmpty(uploads)) {
            response.getWriter().print("true");
        } else {
            response.getWriter().print("false");
        }
    }

    /**
     * 插入pdf页面
     * 
     * @param fileId
     *            文件id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "insertPdfPage/{fileId}/{start}")
    public void insertPdfPage(ModelMap model, @PathVariable String fileId, @PathVariable Integer start, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        Support<Object> support = new Support<Object>();
        Map<String, MultipartFile> fileMap = request.getFileMap();
        if (MapUtils.isEmpty(fileMap)) {
            support.setSuccess(false);
            support.setTips("请选择要插入的pdf文档");
            response.getWriter().print(JSONObject.fromObject(support).toString());
        }

        Upload upload = null;
        try {
            upload = uploadService.findByCheck(fileId, FileType.pdf);
            uploadService.pdfInsertPage(upload.getFile(), fileMap.values().iterator().next(), start);
            support.putParam("pdfNumber", uploadService.pdfPageNumber(upload.getFile()));

            Upload file = uploadService.find(fileId);
            String group = file.getGroup();
            String tag = file.getTag();
            String emp = String.valueOf(request.getSession().getAttribute(PARAMS_EMP));
            facade.persisApplyNote(group, tag, emp);
        } catch (UpLoadErrorException e) {
            support.setSuccess(false);
            support.setTips(e.getMessage());
            response.getWriter().print(JSONObject.fromObject(support).toString());
            return;
        }

        support.setTips("插入成功");
        response.getWriter().print(JSONObject.fromObject(support).toString());
    }
}
