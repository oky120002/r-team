/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.user.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.r.web.authority.bean.AutGrantedAuthority;

/**
 * 用户信息
 * 
 * @author rain
 */
@Entity
@Table(name = "RAIN_USER")
public class User implements UserDetails {
	private static final long serialVersionUID = -1064414075421084764L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	public String id;

	/** 电子邮件 */
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	/** 昵称 */
	@Column(name = "nickname")
	private String nickname;

	// spring security 必要属性

	/** 用户名 */
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	/** 密码 */
	@Column(name = "password", nullable = false)
	private String password;

	/** 登陆次数 */
	@Column(name = "loginTimes")
	private Integer loginTimes;

	/** 创建时间 */
	@Column(name = "createdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	/** 过期时间 */
	@Column(name = "expireddate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredDate;

	/** 用户是否没有被锁定 */
	@Column(name = "isAccountNonLocked")
	private Boolean isAccountNonLocked;

	/** 用户证书是否没有过期 */
	@Column(name = "isCredentialsNonExpired")
	private Boolean isCredentialsNonExpired;

	/** 是否启用 */
	@Column(name = "isEnabled")
	private Boolean isEnabled;

	/** 是否锁定 */
	@Column(name = "isLock")
	private Boolean isLock;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(AutGrantedAuthority.USER);
	}

	/** 获取密码 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/** 获取用户名 */
	@Override
	public String getUsername() {
		return this.username;
	}

	/** 用户是否没有过期 */
	@Override
	public boolean isAccountNonExpired() {
		if (this.expiredDate == null) {
			return true;
		} else {
			if (this.expiredDate.before(new Date())) {
				return false;
			} else {
				return true;
			}
		}
	}

	/** 用户是否没有锁定 */
	@Override
	public boolean isAccountNonLocked() {
		if (this.isAccountNonLocked == null) {
			return false;
		}
		return this.isAccountNonLocked.booleanValue();
	}

	/** 用户证书是否没有过期 */
	@Override
	public boolean isCredentialsNonExpired() {
		if (this.isCredentialsNonExpired == null) {
			return false;
		}
		return this.isCredentialsNonExpired.booleanValue();
	}

	/** 用户是否启用 */
	@Override
	public boolean isEnabled() {
		if (this.isEnabled == null) {
			return false;
		}
		return this.isEnabled.booleanValue();
	}

	/**
	 * @return 返回 createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param 对createDate进行赋值
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return 返回 expiredDate
	 */
	public Date getExpiredDate() {
		return expiredDate;
	}

	/**
	 * 设置过期时间
	 * 
	 * @param 对expiredDate进行赋值
	 */
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	/**
	 * @return 返回 isAccountNonLocked
	 */
	public Boolean getIsAccountNonLocked() {
		return isAccountNonLocked;
	}

	/**
	 * 设置用户是否没有锁定
	 * 
	 * @param 对isAccountNonLocked进行赋值
	 */
	public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	/**
	 * @return 返回 isCredentialsNonExpired
	 */
	public Boolean getIsCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	/**
	 * @param 对isCredentialsNonExpired进行赋值
	 */
	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	/**
	 * @return 返回 isEnabled
	 */
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * @param 对isEnabled进行赋值
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @param 对username进行赋值
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param 对password进行赋值
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 返回 loginTimes
	 */
	public Integer getLoginTimes() {
		return loginTimes == null ? Integer.valueOf(0) : loginTimes;
	}

	/**
	 * @param 对loginTimes进行赋值
	 */
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	@Override
	public String toString() {
		return MessageFormatter.format("User [ {}({}) ]", this.username, this.nickname).getMessage();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
