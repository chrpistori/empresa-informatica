package kgp.dao;

import java.util.List;

import javax.persistence.Query;

import kgp.model.Iteracao;
import kgp.model.Tarefa;

public class IteracaoDAO extends CrudDAO<Iteracao> {
	public void salvar(Iteracao i) {
		i.setAtivo(true);
		super.salvar(i);
	}

	@SuppressWarnings("unchecked")
	public List<Iteracao> listarIteracoesAtivos() {
		Query query = getEntityManager().createQuery(
				"SELECT i FROM Iteracao i WHERE i.ativo = 1 ORDER BY i.nome");
		return query.getResultList();
	}

	public void desativaIteracao(Iteracao i) {
		i.setAtivo(false);
		super.atualizar(i);
	}

	public Iteracao obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT i FROM Iteracao i where i.id = :id");
		q.setParameter("id", id);
		return (Iteracao) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Tarefa> listarTarefas(Iteracao iteracao) {
		Query query = getEntityManager().createQuery(
				"SELECT t FROM Tarefa t WHERE t.iteracao = :iteracao");
		query.setParameter("iteracao", iteracao);
		return query.getResultList();

	}

}