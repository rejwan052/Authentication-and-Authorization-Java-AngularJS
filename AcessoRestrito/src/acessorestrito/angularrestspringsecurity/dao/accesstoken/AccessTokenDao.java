package acessorestrito.angularrestspringsecurity.dao.accesstoken;

import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.AccessToken;

public interface AccessTokenDao extends Dao<AccessToken, Long> {
	AccessToken findByToken(String accessTokenString);
}
