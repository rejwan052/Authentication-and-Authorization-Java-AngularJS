package acessorestrito.angularrestspringsecurity.dao;

import org.springframework.security.crypto.password.PasswordEncoder;

import acessorestrito.angularrestspringsecurity.dao.user.UserDao;
import acessorestrito.angularrestspringsecurity.entity.Role;
import acessorestrito.angularrestspringsecurity.entity.Usuario;

import java.util.Date;

public class DataBaseInitializer {

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;

	protected DataBaseInitializer() {
	}

	public DataBaseInitializer(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void initDataBase() {
	}
}
