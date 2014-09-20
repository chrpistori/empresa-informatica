package kgp.dao;

import java.util.List;

import javax.persistence.Query;

import kgp.model.Funcionario;

public class FuncionarioDAO extends CrudDAO<Funcionario> {
	public void salvar(Funcionario f) {
		f.setAtivo(true);
		super.salvar(f);
	}

	public void desativaFuncionario(Funcionario f) {
		f.setAtivo(false);
		super.atualizar(f);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionariosAtivos() {
		Query query = getEntityManager()
				.createQuery(
						"SELECT f FROM Funcionario f WHERE f.ativo = 1 ORDER BY f.nome");
		return query.getResultList();
	}
	
	public Funcionario obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT f FROM Funcionario f where f.id = :id");
		q.setParameter("id", id);
		return (Funcionario) q.getSingleResult();
	}
	
	public Funcionario obterPorCodigo(Integer codigo) {
		Query q = getEntityManager().createQuery(
				"SELECT f FROM Funcionario f where f.codigo = :id");
		q.setParameter("id", codigo);
		return (Funcionario) q.getSingleResult();
	}
}
