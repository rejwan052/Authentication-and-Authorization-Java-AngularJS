package acessorestrito.angularrestspringsecurity.rest.resources;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import Uteis.Conversores;
import acessorestrito.angularrestspringsecurity.JsonViews;
import acessorestrito.angularrestspringsecurity.dao.JpaDao;
import acessorestrito.angularrestspringsecurity.dao.user.UserDao;
import acessorestrito.angularrestspringsecurity.entity.AccessToken;
import acessorestrito.angularrestspringsecurity.entity.Usuario;
import acessorestrito.angularrestspringsecurity.entity.Role;
import acessorestrito.angularrestspringsecurity.service.UserService;
import acessorestrito.angularrestspringsecurity.transfer.UserTransfer;
import javassist.expr.NewArray;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/user")
public class UserResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDaoInterface;

	@Autowired
	public PasswordEncoder passwordEncoder;

	private UserDao userDao;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public String list() throws IOException {
		// this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		List<Usuario> allEntries = this.userDaoInterface.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetails)) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;

		return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
	}

	/**
	 * Authenticates a user and creates an access token.
	 *
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return The generated access token.
	 */
	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AccessToken authenticate(@FormParam("username") String username, @FormParam("password") String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Object principal = authentication.getPrincipal();
		if (!(principal instanceof Usuario)) {
			throw new WebApplicationException(401);
		}

		return this.userService.createAccessToken((Usuario) principal);
	}

	@Path("Cadastrar")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(@FormParam("username") String username, @FormParam("password") String password) {
		String saida;

		try {

			String senhaMD5 = passwordEncoder.encode(password).toString();
			Usuario userUser = new Usuario(username, senhaMD5);
			this.userDaoInterface.save(userUser);
			saida = "sucesso";

		} catch (Exception ex) {
			saida = "falha";
		}
		return saida;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{id}")
	public Usuario read(@PathParam("id") Long id) {
		this.logger.info("read(): " + id);

		Usuario usuario = this.userDaoInterface.find(id);

		if (usuario == null) {
			throw new WebApplicationException(404);
		}
		this.logger.info("toles: " + usuario.getRoles());

		return usuario;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ObterRoles")
	public List<String> ObterRoles() {

		List<String> listaDeAcesso = new ArrayList<>();
		listaDeAcesso.add((Role.ADMIN).toString());
		listaDeAcesso.add((Role.USER).toString());
		listaDeAcesso.add((Role.CONTROLE_ACESSO).toString());

		return listaDeAcesso;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ObterRolesUsuario")
	public Usuario ObterRoles(@QueryParam("id") Long id) {

		Usuario usuario = this.userDaoInterface.find(id);
		return usuario;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/Alteracao")
	public Usuario Alterar(Usuario usuario) {

		this.logger.info("update():");
		Usuario user = this.userDaoInterface.find(usuario.getId());

		String senhaMD5;

		if (user.getPassword().equals(usuario.getPassword()))
			senhaMD5 = usuario.getPassword();
		else
			senhaMD5 = passwordEncoder.encode(usuario.getPassword()).toString();

		usuario.setPassword(senhaMD5);

		return this.userDaoInterface.save(usuario);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/AddRoles")
	public Usuario Alterar(@QueryParam("lista") String ListaRolesTrueEnviadaController, Usuario usuario) {

		String[] comparacaoRoles = ListaRolesTrueEnviadaController.split(",");

		List<String> listaComparacaoDeRoles = new ArrayList<>();

		for (String roles : comparacaoRoles) {
			listaComparacaoDeRoles.add(roles);

		}
		if (ListaRolesTrueEnviadaController.contains("ADMIN"))
			usuario.addRole(Role.ADMIN);
		if (ListaRolesTrueEnviadaController.contains("USER"))
			usuario.addRole(Role.USER);
		if (ListaRolesTrueEnviadaController.contains("CONTROLE_ACESSO"))
			usuario.addRole(Role.CONTROLE_ACESSO);

		return this.userDaoInterface.save(usuario);

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list/{id}")
	public void delete(@PathParam("id") Long id) {
		this.logger.info("delete(id)");

		this.userDaoInterface.delete(id);
	}

	private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

}
