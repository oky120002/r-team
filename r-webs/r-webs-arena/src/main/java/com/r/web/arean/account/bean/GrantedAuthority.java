/**
 * 
 */
package com.r.web.arean.account.bean;

/**
 * spring
 * 
 * @author oky
 * 
 */
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {
	private static final long serialVersionUID = -811813633934700132L;

	/** 用户 */
	public static final GrantedAuthority USER = new GrantedAuthority("USER");

	private String authority;

	public GrantedAuthority() {
		this.authority = "USER";
	}

	public GrantedAuthority(String authority) {
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
		GrantedAuthority other = (GrantedAuthority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

}
