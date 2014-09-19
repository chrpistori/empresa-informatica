package kgp.dao;

import java.util.List;

import javax.persistence.Query;

import kgp.model.Cliente;

public class ClienteDAO extends CrudDAO<Cliente> {
	public void salvar(Cliente c) {
		c.setAtivo(true);
		super.salvar(c);
	}

	public void desativaCliente(Cliente c) {
		c.setAtivo(false);
		super.atualizar(c);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientesAtivos(int id) {
		Query query = getEntityManager()
				.createQuery(
						"SELECT c FROM Cliente c WHERE c.ativo = 1 and c.conta = :conta ORDER BY c.nome");
		query.setParameter("conta", id);
		return query.getResultList();
	}

	public Cliente obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT c FROM Cliente c where c.id = :id");
		q.setParameter("id", id);
		return (Cliente) q.getSingleResult();
	}
}
