package com.r.boda.uploadservice.upload.model;

public enum FileType {
	binary("application/octet-stream", ResponseDataType.binary), //
	jpg("image/jpeg", ResponseDataType.image), //
	jpeg("image/jpeg", ResponseDataType.image), //
	png("image/png", ResponseDataType.image), //
	gif("image/gif", ResponseDataType.image), //
	tif("image/tif", ResponseDataType.image), //
	bmp("image/bmp", ResponseDataType.image), //
	txt("text/plain", ResponseDataType.txt), //
	pdf("", ResponseDataType.pdf), //
	doc("", ResponseDataType.office_doc), //
	docx("", ResponseDataType.office_docx), //
	// xls, xlsx, ppt, pptx, ini, //
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
		binary, // 二进制
		txt, //
		office_doc, //
		office_docx, pdf, //
		image, //
		audia, //
		file, //
		;
	}

}
