package acessorestrito.angularrestspringsecurity.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@javax.persistence.Entity
public class AccessToken implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String token;

	@ManyToOne
	private Usuario user;

	@Column
	private Date expiry;

	protected AccessToken() {
	}

	public AccessToken(Usuario user, String token) {
		this.user = user;
		this.token = token;
	}

	public AccessToken(Usuario user, String token, Date expiry) {
		this(user, token);
		this.expiry = expiry;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getToken() {
		return this.token;
	}

	public Usuario getUser() {
		return this.user;
	}

	public Date getExpiry() {
		return this.expiry;
	}

	public boolean isExpired() {
		if (null == this.expiry) {
			return false;
		}

		return this.expiry.getTime() > System.currentTimeMillis();
	}
}
