package com.r.boda.uploadservice.upload.model;

public enum FileType {
    binary("application/octet-stream", ResponseDataType.binary), //
    jpg("image/jpeg", ResponseDataType.image), //
    jpeg("image/jpeg", ResponseDataType.image), //
    png("image/png", ResponseDataType.image), //
    gif("image/gif", ResponseDataType.image), //
    ico("image/x-icon", ResponseDataType.image), //
    tiff("image/tiff", ResponseDataType.image), //
    bmp("image/bmp", ResponseDataType.image), //
    txt("text/plain", ResponseDataType.txt), //
    sql("text/plain", ResponseDataType.txt), //
    pdf("application/pdf", ResponseDataType.pdf), //
    doc("application/msword", ResponseDataType.office), //
    docx("application/msword", ResponseDataType.office), //
    xls("application/vnd.ms-excel", ResponseDataType.office), //
    xlsx("application/vnd.ms-excel", ResponseDataType.office), //
    ppt("application/vnd.ms-powerpoint", ResponseDataType.office), //
    pptx("application/vnd.ms-powerpoint", ResponseDataType.office), //
    ini("text/plain", ResponseDataType.txt), //
    // dll, jar, exe, zip, bat, chm, log, reg, dat, unbeknown,
    ;

    /** response返回的ContentType字符串 */
    private String contentType;
    /** 实际返回的内容文件的分类 */
    private ResponseDataType responseDataType;

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @return the responseDataType
     */
    public ResponseDataType getResponseDataType() {
        return responseDataType;
    }

    FileType(String contentType, ResponseDataType responseDataType) {
        this.contentType = contentType;
        this.responseDataType = responseDataType;
    }

    /**
     * 根据文件名,解析文件类型,没有找到对应的文件类型,则返回二进制类型
     * 
     * @param fileName
     * @return
     */
    public static FileType re(String fileName) {
        try {
            FileType valueOf = FileType.valueOf(fileName.substring(fileName.lastIndexOf('.') + 1));
            return valueOf == null ? FileType.binary : valueOf;
        } catch (Exception e) {
            return FileType.binary;
        }

    }

    public enum ResponseDataType {
        binary("二进制文件流"), // 二进制
        txt("文本"), //
        office("Office"), //
        pdf("PDF"), //
        image("图片"), //
        audia("媒体"), //
        file("文件"), //
        ;

        private String name;

        public String getName() {
            return name;
        }

        ResponseDataType(String name) {
            this.name = name;
        }
    }

}
