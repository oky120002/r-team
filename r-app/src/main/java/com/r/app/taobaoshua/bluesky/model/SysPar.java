/**
 * 
 */
package com.r.app.taobaoshua.bluesky.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.r.app.taobaoshua.bluesky.model.enumsyspar.SysParName;

/**
 * @author oky
 * 
 */
@Entity
@Table(name = "syspar")
public class SysPar {
	@Id
	@Column
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	private String id;

	@Column
	@Enumerated(EnumType.STRING)
	private SysParName name; // 查询项

	@Column
	private String value; // 值

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
	 * @return the name
	 */
	public SysParName getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(SysParName name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
