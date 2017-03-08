package acessorestrito.angularrestspringsecurity.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), CONTROLE_ACESSO("ROLE_CONTROLE_ACESSO");

	private String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}
