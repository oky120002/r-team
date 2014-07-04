package com.r.boda.uploadservice.upload.model;

public enum FileType {
	jpg, png, gif, tif, //
	txt, pdf, doc, docx, xls, xlsx, ppt, pptx, ini, //
	dll, jar, exe, zip, bat, chm, log, reg, dat, unbeknown, ;

	/**
	 * 根据文件名,解析文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static FileType re(String fileName) {
		try {
			FileType valueOf = FileType.valueOf(fileName.substring(fileName.lastIndexOf('.') + 1));
			return valueOf == null ? FileType.unbeknown : valueOf;
		} catch (Exception e) {
			return FileType.unbeknown;
		}

	}
}
