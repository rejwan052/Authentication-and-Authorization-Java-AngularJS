package acessorestrito.angularrestspringsecurity.dao.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.Usuario;

public interface UserDao extends Dao<Usuario, Long> {
	Usuario loadUserByUsername(String username) throws UsernameNotFoundException;

	Usuario findByName(String name);
}
