/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
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

    // @Resource(name = "passwordEncoder")
    // private PasswordEncoder passwordEncoder; // 密码

    /**
     * 保存上传的文件
     * 
     * @param files
     *            文件
     * @param tags
     *            标签
     * @throws IOException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Upload> save(List<MultipartFile> files, List<String> tags, String group) throws UpLoadErrorException {
        if (CollectionUtils.isEmpty(files)) {
            return new ArrayList<Upload>();
        }

        List<Upload> failUploads = new ArrayList<Upload>();
        UpLoadErrorException error = null;
        int size = files.size();
        int tagSize = tags.size();
        if (tagSize != size) {
            throw new UpLoadErrorException("上传文件和上传文件标签数量必须相等");
        }
        for (int index = 0; index < size; index++) {
            MultipartFile multipartFile = files.get(index);
            String tag = tags.get(index);
            if (!multipartFile.isEmpty()) {
                logger.debug("保存上传的文件 : {}", multipartFile.getOriginalFilename());
                try {
                    File uploadFile = getUploadRandomFile();
                    FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);
                    Upload up = new Upload();
                    up.setFileName(multipartFile.getOriginalFilename());
                    up.setFilePath(uploadFile.getAbsolutePath());
                    up.setGroup(group);
                    up.setFileLength(uploadFile.length());
                    up.setIsEnabled(true);
                    up.setFileType(FileType.re(multipartFile.getOriginalFilename()));
                    up.setTag(tag);
                    uploadDao.create(up);
                    Upload target = new Upload();
                    BeanUtils.copyProperties(up, target, new String[] { "filepath" }); // 过滤掉真实文件地址
                    failUploads.add(target);
                } catch (Exception e) {
                    if (error == null) {
                        error = new UpLoadErrorException("");
                    }
                    error.addError(multipartFile.getOriginalFilename(), e.getMessage());
                }
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
     * 删除文件
     * 
     * @param fileId
     *            文件id
     * @param isDeleteFile
     *            是否删除在硬盘上对应的文件数据,false:则只删除数据中的文件记录
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteFile(String fileId, boolean isDeleteFile) {
        Upload find = uploadDao.find(fileId);
        String filepath = find.getFilePath();
        uploadDao.delete(find);
        if (isDeleteFile) {
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /** 根据文件id查找文件 */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = true)
    public Upload find(String fileId) {
        return uploadDao.find(fileId);
    }

    /** 根据文件id查找文件,并且校验文件类型,此方法会抛出错误 */
    public Upload findByCheck(String fileId, FileType fileType) throws UpLoadErrorException {
        if (StringUtils.isBlank(fileId)) {
            throw new UpLoadErrorException("fileId不能为空");
        }
        if (fileType == null) {
            throw new UpLoadErrorException("fileType不能为空");
        }

        Upload upload = find(fileId);
        if (upload == null) {
            throw new UpLoadErrorException("此附件信息丢失,请联系管理员");
        }
        File file = upload.getFile();
        if (file == null) {
            throw new UpLoadErrorException("此附件丢失,请联系管理员");
        }

        if (!fileType.equals(upload.getFileType())) {
            throw new UpLoadErrorException("此附件不是" + fileType.getResponseDataType().getName() + "类型文件,请联系管理员");
        }
        return upload;
    }

    /**
     * 删除pdf页面
     * 
     * @throws IOException
     */
    public void pdfDeletePage(File srcFile, Integer start, Integer end) throws UpLoadErrorException {
        if (srcFile == null || !srcFile.exists()) {
            throw new UpLoadErrorException("PDF文件不存在");
        }
        if (start == null || end == null) {
            throw new UpLoadErrorException("删除的pdf页面,起始页和结束页不能为空");
        }

        StringBuffer sb = new StringBuffer();
        int size = end.intValue() - start.intValue();
        for (int i = start.intValue(); i <= size; i++) {
            sb.append(",!").append(i);
        }
        sb.deleteCharAt(0);
        
        
        
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(srcFile.getPath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        PdfStamper pdfStamper = null;
        try {
            pdfStamper = new PdfStamper(pdfReader , new FileOutputStream(srcFile.getPath() + ".tem"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        pdfReader.selectPages(sb.toString());
        try {
            pdfStamper.close();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
//        
//
//        File tempFile = new File(srcFile.getPath() + ".tmp");
////        Document document = null;
//        PdfReader pdfReader = null;
////        PdfCopy pdfCopy = null;
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(srcFile);
//            pdfReader = new PdfReader(fileInputStream);
////            pdfReader.selectPages(sb.toString());
////            int newPdfPageNumber = pdfReader.getNumberOfPages();
////            document = new Document(pdfReader.getPageSize(1));
////            pdfCopy = new PdfCopy(document, new FileOutputStream(tempFile));
////            
////            document.open();
////            for (int i = 1; i <= newPdfPageNumber; i++) {
////                pdfCopy.addPage(pdfCopy.getImportedPage(pdfReader, i));
////            }
////            
//            
//            PdfStamper pdfStamper = new PdfStamper(pdfReader , new FileOutputStream(tempFile));
//            pdfReader.selectPages(sb.toString());
//            pdfStamper.close(); 
//        } catch (FileNotFoundException e) {
//            throw new UpLoadErrorException("创建PdfCopy失败 : " + e.toString());
//        } catch (BadPdfFormatException e) {
//            throw new UpLoadErrorException("删除PDF页失败 : " + e.toString());
//        } catch (DocumentException e) {
//            throw new UpLoadErrorException("创建PdfCopy失败 : " + e.toString());
//        } catch (IOException e) {
//            throw new UpLoadErrorException("删除PDF页失败 : " + e.toString());
//        } finally {
//            // 删除临时文件
//            // tempFile.delete();
////            pdfCopy.close();
//            pdfReader.close();
////            document.close();
//            IOUtils.closeQuietly(fileInputStream);
//        }

    }

    /** 插入pdf页面 */
    public void pdfInsertPage(File srcFile, File insertFile, Integer start, Integer end) {

    }

    /**
     * 获取pdf页数
     * 
     * @throws IOException
     */
    public int pdfPageNumber(File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            PdfReader pdfReader = new PdfReader(fileInputStream);
            return pdfReader.getNumberOfPages();
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
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
