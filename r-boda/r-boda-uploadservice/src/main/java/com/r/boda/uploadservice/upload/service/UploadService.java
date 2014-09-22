/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.boda.uploadservice.upload.service;

import java.io.ByteArrayOutputStream;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.r.boda.uploadservice.core.EnviromentalParameter;
import com.r.boda.uploadservice.core.UpLoadErrorException;
import com.r.boda.uploadservice.upload.dao.UploadDao;
import com.r.boda.uploadservice.upload.model.FileType;
import com.r.boda.uploadservice.upload.model.Upload;
import com.r.core.util.RandomUtil;
import com.r.core.util.TaskUtil;
import com.r.web.support.abs.AbstractService;

/**
 * 用户Service<br />
 * 
 * @author rain
 */
@Service("upload.uploadService")
public class UploadService extends AbstractService {

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
        UpLoadErrorException error = new UpLoadErrorException();
        boolean isError = false;
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
                    Upload up = new Upload();
                    String fileFullPath = getUploadRandomFile();
                    File uploadFile = image2pdf(multipartFile, fileFullPath);
                    if (uploadFile != null) { // 转换成pdf成功
                        String originalFilename = multipartFile.getOriginalFilename();
                        String name = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
                        up.setFileName(name + ".pdf");
                        up.setFileType(FileType.pdf);
                    } else { // 转换成pdf失败,直接保存
                        uploadFile = new File(fileFullPath);
                        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);
                        up.setFileName(multipartFile.getOriginalFilename());
                        up.setFileType(FileType.re(multipartFile.getOriginalFilename()));
                    }
                    up.setFilePath(uploadFile.getAbsolutePath());
                    up.setGroup(group);
                    up.setFileLength(uploadFile.length());
                    up.setIsEnabled(true);
                    up.setTag(tag);

                    // 校验pdf单页平均大小不超过400K
                    if (FileType.pdf.equals(up.getFileType())) {
                        int pdfPageNumber = pdfPageNumber(up.getFile());
                        long kb = up.getFileLength() / 1024;
                        if (kb > 400 * pdfPageNumber) {
                            error.addError(multipartFile.getOriginalFilename(), "文件不符合要求,无法上传到服务器,请修改pdf扫描设置,以保证pdf单页平均大小不超过400K");
                            isError = true;
                            break;
                        }
                    }
                    uploadDao.create(up);
                    Upload target = new Upload();
                    BeanUtils.copyProperties(up, target, new String[] { "filepath" }); // 过滤掉真实文件地址
                    failUploads.add(target);
                } catch (OutOfMemoryError ome) {
                    error.addError(multipartFile.getOriginalFilename(), "上传文件过大,系统内存溢出");
                    isError = true;
                } catch (Exception e) {
                    error.addError(multipartFile.getOriginalFilename(), e.toString());
                    isError = true;
                }
            }
        }
        if (isError) {
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
     * 根据附件分组和标签查询附件
     * 
     * @param group
     * @param tag
     * @return
     */
    public List<Upload> queryByGroupAndTag(String group, String tag) {
        Upload up = new Upload();
        up.setGroup(group);
        up.setTag(tag);
        up.setIsEnabled(true);
        return uploadDao.queryByExample(up);
    }

    /**
     * 禁用文件
     * 
     * @param fileId
     *            文件id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void unEnabledFile(String fileId) {
        Upload upload = uploadDao.find(fileId);
        upload.setIsEnabled(false);
        uploadDao.update(upload);
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

    /**
     * 插入pdf页面
     * 
     * @throws IOException
     */
    public void pdfInsertPage(File srcFile, MultipartFile multipartFile, Integer start) throws IOException {
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
        switch (fileType) {
        case pdf:
            pdfInsertPdf(srcFile, multipartFile.getInputStream(), start);
            break;
        case gif:
        case jpeg:
        case jpg:
        case png:
            imageInsertPdf(srcFile, multipartFile, start);
            break;
        default:
            throw new UpLoadErrorException("不支持" + fileType.getResponseDataType().getName() + "类型[" + fileType.name() + "]的文件插入到pdf中");
        }
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
            throw new UpLoadErrorException(e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    /**
     * 向pdf插入pdf
     * 
     * @param inputStream
     * @param doc
     * @param copy
     * @param fileType
     * @throws IOException
     * @throws DocumentException
     */
    private void pdfInsertPdf(File srcFile, InputStream inputStreamFile, Integer start) {
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
                if (start.intValue() == i) { // 从选择的pdf位置插入新上传的pdf
                    PdfReader insertReader = new PdfReader(inputStreamFile);
                    int insertPages = insertReader.getNumberOfPages();
                    for (int j = 1; j <= insertPages; j++) {
                        doc.newPage();
                        copy.addPage(copy.getImportedPage(insertReader, j));
                    }
                }
                doc.newPage();
                copy.addPage(copy.getImportedPage(reader, i));
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
            IOUtils.closeQuietly(inputStreamFile);
        }

        TaskUtil.sleep(50); // 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        File tempFile = new File(tempPath);
        srcFile.delete();
        TaskUtil.sleep(50);// 等待0.05秒,让磁盘能够反映过来,好让文件重命名
        tempFile.renameTo(srcFile);
    }

    /**
     * 向pdf插入图片
     * 
     * @param srcFile
     * @param multipartFile
     * @param start
     * @throws FileNotFoundException
     */
    private void imageInsertPdf(File srcFile, MultipartFile multipartFile, Integer start) throws FileNotFoundException {
        String filePathTemp = getUploadRandomFile() + ".temp";
        File image2pdf = image2pdf(multipartFile, filePathTemp);

        pdfInsertPdf(srcFile, new FileInputStream(image2pdf), start);

        // 删除临时文件
        TaskUtil.sleep(50);
        image2pdf.delete();
    }

    /**
     * 图片转换成pdf,且保存到文件 uploadFile
     * 
     * @param multipartFile
     * @param uploadFilePath
     * @return 如果成功转换则返回true,失败返回false
     */
    private File image2pdf(MultipartFile multipartFile, String uploadFilePath) {
        if (FileType.pdf.equals(FileType.re(multipartFile.getOriginalFilename()))) {
            return null;
        }
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            File file = new File(uploadFilePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            Image jpg = Image.getInstance(inputStreamToByte(multipartFile.getInputStream()));
            jpg.setAlignment(Image.MIDDLE);
            // 压缩图片
            float heigth = jpg.getHeight();
            float width = jpg.getWidth();
            int p = 0;
            float p2 = 0.0f;
            if (heigth > width) {
                p2 = 297 / heigth * 100;
            } else {
                p2 = 210 / width * 100;
            }
            p = Math.round(p2);
            jpg.scalePercent(p + 3);// 表示是原来图像的比例;
            // 压缩图片
            doc.newPage();
            doc.add(jpg);
            TaskUtil.sleep(50); // 等待文件生成完成
            return new File(uploadFilePath);
        } catch (DocumentException de) {
            logger.error("转换pdf文档失败.", de);
            return null;
        } catch (IOException ioe) {
            logger.error("保存pdf文件 或者 获取图片流失败.", ioe);
            return null;
        } catch (Exception e) {
            logger.error("未知错误.", e);
            return null;
        } finally {
            doc.close();
        }
    }

    /**
     * 读取输入流到字节数组
     * 
     * @param is
     * @return
     * @throws IOException
     */
    private byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        try {
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            return bytestream.toByteArray();
        } finally {
            bytestream.close();
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
    private String getUploadRandomFile() {
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
        return sb.toString();
    }
}
