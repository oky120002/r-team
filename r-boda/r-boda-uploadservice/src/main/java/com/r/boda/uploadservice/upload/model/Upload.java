package com.r.boda.uploadservice.upload.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "UPLOADSERVICE_USER")
public class Upload implements Serializable {
	private static final long serialVersionUID = 2879893099183340578L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	public String id;

	/** 电子邮件 */
	@Column(name = "filename", unique = true, nullable = false)
	private String fileName;

	/** 昵称 */
	@Column(name = "filepath")
	private String filepath;

	/** 文件对外的唯一标识 */
	@Column(name = "key")
	private String key;

	/** 文件分组 */
	@Column(name = "group")
	private String group;

	/** 是否启用 */
	@Column(name = "isEnabled")
	private Boolean isEnabled;
}
