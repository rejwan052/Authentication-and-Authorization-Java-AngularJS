package acessorestrito.angularrestspringsecurity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.accesstoken.AccessTokenDao;
import acessorestrito.angularrestspringsecurity.dao.user.UserDao;
import acessorestrito.angularrestspringsecurity.entity.AccessToken;
import acessorestrito.angularrestspringsecurity.entity.Usuario;

import java.util.UUID;

public class DaoUserService implements UserService {
	private UserDao userDao;

	private AccessTokenDao accessTokenDao;

	protected DaoUserService() {
	}

	public DaoUserService(UserDao userDao, AccessTokenDao accessTokenDao) {
		this.userDao = userDao;
		this.accessTokenDao = accessTokenDao;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userDao.loadUserByUsername(username);
	}

	@Override
	@Transactional
	public Usuario findUserByAccessToken(String accessTokenString) {
		AccessToken accessToken = this.accessTokenDao.findByToken(accessTokenString);

		if (null == accessToken) {
			return null;
		}

		if (accessToken.isExpired()) {
			this.accessTokenDao.delete(accessToken);
			return null;
		}

		return accessToken.getUser();
	}

	@Override
	@Transactional
	public AccessToken createAccessToken(Usuario user) {
		AccessToken accessToken = new AccessToken(user, UUID.randomUUID().toString());
		return this.accessTokenDao.save(accessToken);
	}
}
