package kgp.dao;

import java.util.List;

import javax.persistence.Query;

import kgp.model.Usuario;

public class UsuarioDAO extends CrudDAO<Usuario> {

	public void salvar(Usuario u) {
		u.setAtivo(true);
		super.salvar(u);
	}

	public void desativaUsuario(Usuario u) {
		u.setAtivo(false);
		super.atualizar(u);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosAtivos(int conta) {
		Query query = getEntityManager()
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.ativo = 1 and u.perfil = 3 and u.conta = :conta ORDER BY u.nome");
		query.setParameter("conta", conta);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarGerentes(int conta) {
		Query query = getEntityManager()
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.perfil = 2 and u.conta = :conta ORDER BY u.nome");
		query.setParameter("conta", conta);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarMembros(int conta) {
		Query query = getEntityManager()
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.perfil = 3 and u.conta = :conta ORDER BY u.nome");
		query.setParameter("conta", conta);
		return query.getResultList();
	}

	public Usuario obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT u FROM Usuario u where u.id = :id");
		q.setParameter("id", id);
		return (Usuario) q.getSingleResult();
	}

	public Usuario obterPorLogin(String usuario, String senha) {
		Query q = getEntityManager()
				.createQuery(
						"SELECT u FROM Usuario u where u.usuario = :usuario and u.senha = :senha");
		q.setParameter("usuario", usuario);
		q.setParameter("senha", senha);
		return (Usuario) q.getSingleResult();
	}
}
