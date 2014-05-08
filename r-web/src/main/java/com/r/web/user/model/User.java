/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.r.web.authority.model.AutUserDetails;

/**
 * 用户信息
 * 
 * @author rain
 */
@Entity
@Table(name = "RAIN_USER")
public class User implements Serializable {
	private static final long serialVersionUID = -1064414075421084764L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	private String id;

//	/** 权限用户 */
//	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
//	private AutUserDetails userDetails;

	/** 电子邮件 */
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	/** 昵称 */
	@Column(name = "nickname")
	private String nickname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public AutUserDetails getUserDetails() {
//		return userDetails;
//	}
//
//	public void setUserDetails(AutUserDetails userDetails) {
//		this.userDetails = userDetails;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
