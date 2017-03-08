package acessorestrito.angularrestspringsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import acessorestrito.angularrestspringsecurity.entity.AccessToken;
import acessorestrito.angularrestspringsecurity.entity.Usuario;

public interface UserService extends UserDetailsService
{
    Usuario findUserByAccessToken(String accessToken);

    AccessToken createAccessToken(Usuario user);
}
