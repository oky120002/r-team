/**
 * 
 */
package com.r.web.authority.bean;

import org.springframework.security.core.GrantedAuthority;

/**
 * spring
 * 
 * @author oky
 * 
 */
public class AutGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -811813633934700132L;

	/** 用户 */
	public static final AutGrantedAuthority USER = new AutGrantedAuthority("USER");

	private String authority;

	public AutGrantedAuthority() {
		this.authority = "USER";
	}

	public AutGrantedAuthority(String authority) {
		super();
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
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
		AutGrantedAuthority other = (AutGrantedAuthority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

}
