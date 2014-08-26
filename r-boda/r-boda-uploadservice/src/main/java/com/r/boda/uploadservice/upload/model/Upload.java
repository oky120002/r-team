package com.r.boda.uploadservice.upload.model;

import java.io.File;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "UPLOAD")
public class Upload implements Serializable {
    private static final long serialVersionUID = 2879893099183340578L;

    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    private String id;

    /** 文件名 */
    @Column(length = 1024)
    private String fileName;

    /** 文件实际地址 */
    @Column(length = 1024)
    private String filePath;

    @Column
    private Long fileLength; // 文件长度

    /** 文件分组 */
    // group在某些数据库中是关键字,不能用作列名
    @Column(name = "_group", length = 1024)
    private String group;

    /** 此上传文件的标签 */
    @Column(length = 1024)
    private String tag;

    /** 是否启用 */
    @Column
    private Boolean isEnabled;

    @Column
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath
     *            the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileLength
     */
    public Long getFileLength() {
        return fileLength;
    }

    /**
     * @param fileLength
     *            the fileLength to set
     */
    public void setFileLength(Long fileLength) {
        this.fileLength = fileLength;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the isEnabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled
     *            the isEnabled to set
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return the fileType
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    /** 获得附件对应的文件,文件路径为空,文件不存在,文件不是"file"类型",则返回null */
    public File getFile() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }
}
