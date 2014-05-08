/**
 * 描          述:  <描述>
 * 修  改   人:  rain
 * 修改时间:  2013-4-7
 * <修改描述:>
 */
package com.r.web.authority.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.r.web.user.model.User;

/**
 * 权限用户 
 * 
 * @author rain
 */
@Entity
@Table(name = "RAIN_USER_DETAILS")
public class AutUserDetails implements org.springframework.security.core.userdetails.UserDetails {
	private static final long serialVersionUID = 3103092675687875537L;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "sys_uuid")
	@GenericGenerator(name = "sys_uuid", strategy = "uuid")
	private String id;

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

	/** 用户 */
	@OneToOne(mappedBy = "userDetails", fetch = FetchType.EAGER)
	private User user;

	/** 默认构造函数 */
	public AutUserDetails() {
		super();
	}

	/** 默认构造函数 */
	public AutUserDetails(String username, String nickname, String email, String password) {
		super();
		this.createDate = new Date();
		this.expiredDate = null;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.loginTimes = 0;

		this.username = username;
		this.password = password;
		this.isLock = false;
		this.isEnabled = true;
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
	 * @return 返回 id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param 对id进行赋值
	 */
	public void setId(String id) {
		this.id = id;
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

	/** 获取用户 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "User [" + username + "]";
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
		AutUserDetails other = (AutUserDetails) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
