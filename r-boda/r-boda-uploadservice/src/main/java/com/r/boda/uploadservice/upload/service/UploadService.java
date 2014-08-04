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
import java.io.InputStream;
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
import com.r.core.util.TaskUtil;

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
    public List<Upload> save(List<MultipartFile> files, String sysname, List<String> tags, String group) throws UpLoadErrorException {
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

        String tempPath = srcFile.getPath() + ".temp";
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        FileOutputStream tempOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            tempOutputStream = new FileOutputStream(tempPath);
            pdfReader = new PdfReader(fileInputStream);
            pdfStamper = new PdfStamper(pdfReader, tempOutputStream);
            pdfReader.selectPages(calPdfSelectRanges(start.intValue(), end.intValue(), pdfReader.getNumberOfPages()));
            pdfStamper.close();
        } catch (DocumentException e) {
            throw new UpLoadErrorException("加载或者关闭pdf文档错误 : " + e.toString());
        } catch (FileNotFoundException e) {
            throw new UpLoadErrorException("加载pdf临时文件错误 : " + e.toString());
        } catch (IOException e) {
            throw new UpLoadErrorException("pdf文件流错误 : " + e.toString());
        } finally {
            if (pdfReader != null) {
                pdfReader.close();
            }
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(tempOutputStream);
        }

        TaskUtil.sleep(50); // 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        File tempFile = new File(tempPath);
        srcFile.delete();
        TaskUtil.sleep(50);// 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        tempFile.renameTo(srcFile);
    }

    /** 插入pdf页面 */
    public void pdfInsertPage(File srcFile, MultipartFile multipartFile, Integer start) {
        if (srcFile == null || !srcFile.exists()) {
            throw new UpLoadErrorException("PDF文件不存在");
        }
        if (multipartFile == null) {
            throw new UpLoadErrorException("被插入的文件不存在");
        }
        if (start == null) {
            throw new UpLoadErrorException("插入的起始页不能为空");
        }
        FileType fileType = FileType.re(multipartFile.getOriginalFilename());
        switch (fileType.getResponseDataType()) {
        // pdf
        case pdf:
            break;
        default:
            throw new UpLoadErrorException("不支持" + fileType.getResponseDataType().getName() + "类型的文件插入到pdf中");
        }

        String tempPath = srcFile.getPath() + ".temp";
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        Document doc = null;
        PdfCopy copy = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            fileOutputStream = new FileOutputStream(tempPath);
            doc = new Document();
            copy = new PdfCopy(doc, fileOutputStream);

            doc.open();
            PdfReader reader = new PdfReader(fileInputStream);
            int pageNumber = reader.getNumberOfPages();
            if (start.intValue() < 1) {
                start = Integer.valueOf(1);
            }
            if (pageNumber < start.intValue()) {
                start = Integer.valueOf(pageNumber);
            }
            for (int i = 1; i <= pageNumber; i++) {
                doc.newPage();
                copy.addPage(copy.getImportedPage(reader, i));
                if (start.intValue() == i) { // 从选择的pdf位置插入新上传的pdf
                    insertPage(multipartFile.getInputStream(), doc, copy, fileType);
                }
            }
            doc.close();
        } catch (DocumentException e) {
            throw new UpLoadErrorException("加载或者关闭pdf文档错误 : " + e.toString());
        } catch (FileNotFoundException e) {
            throw new UpLoadErrorException("加载pdf临时文件错误 : " + e.toString());
        } catch (IOException e) {
            throw new UpLoadErrorException("pdf文件流错误 : " + e.toString());
        } finally {
            if (copy != null) {
                copy.close();
            }
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }

        TaskUtil.sleep(50); // 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        File tempFile = new File(tempPath);
        srcFile.delete();
        TaskUtil.sleep(50);// 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        tempFile.renameTo(srcFile);
    }

    /**
     * 获取pdf页数
     * 
     * @throws IOException
     */
    public int pdfPageNumber(File file) throws UpLoadErrorException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            PdfReader pdfReader = new PdfReader(fileInputStream);
            return pdfReader.getNumberOfPages();
        } catch (IOException e) {
            throw new UpLoadErrorException(e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    private void insertPage(InputStream inputStream, Document doc, PdfCopy copy, FileType fileType) throws IOException, BadPdfFormatException {
        switch (fileType) {
        // pdf
        case pdf:
            PdfReader insertReader = new PdfReader(inputStream);
            int insertPages = insertReader.getNumberOfPages();
            for (int j = 1; j <= insertPages; j++) {
                doc.newPage();
                copy.addPage(copy.getImportedPage(insertReader, j));
            }
            break;
        // 图片
        case jpeg:
        case bmp:
        case gif:
        case jpg:
        case png:
        case tiff:
            break;

        }
    }

    /**
     * 计算pdf选择范围
     * 
     * @param cutStart
     *            截断起始页码
     * @param cutEnd
     *            截断结束页码
     * @param pdfPageNumber
     *            总页码
     * @return 选择的页码字符串
     */
    private List<Integer> calPdfSelectRanges(int cutStart, int cutEnd, int pdfPageNumber) {
        int temp = -1;
        if (cutStart > cutEnd) {
            temp = cutStart;
            cutStart = cutEnd;
            cutEnd = temp;
        }

        if (cutStart < 1) {
            cutStart = 1;
        }
        if (pdfPageNumber < cutStart) {
            cutStart = pdfPageNumber;
        }

        List<Integer> list = new ArrayList<Integer>();

        for (int number = 1; number <= pdfPageNumber; number++) {
            if (number < cutStart || cutEnd < number) {
                list.add(Integer.valueOf(number));
            }
        }

        return list;
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
        sb.append(epar.getFileSavePath()).append(File.separatorChar);
        sb.append(c1).append(File.separatorChar);
        sb.append(c2).append(File.separatorChar);
        sb.append(c3).append(File.separatorChar);
        sb.append(c4).append(File.separatorChar);
        sb.append(randomFile).append(".up");
        return new File(sb.toString());
    }
}
