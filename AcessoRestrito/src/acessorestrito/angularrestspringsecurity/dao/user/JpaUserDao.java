package acessorestrito.angularrestspringsecurity.dao.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.Usuario;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaUserDao extends JpaDao<Usuario, Long> implements UserDao {
	public JpaUserDao() {
		super(Usuario.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = this.findByName(username);
		if (null == user) {
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);

		Root<Usuario> root = criteriaQuery.from(Usuario.class);

		TypedQuery<Usuario> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByName(String name) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(this.entityClass);

		Root<Usuario> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("name");
		criteriaQuery.where(builder.equal(namePath, name));

		TypedQuery<Usuario> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Usuario> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}
}
