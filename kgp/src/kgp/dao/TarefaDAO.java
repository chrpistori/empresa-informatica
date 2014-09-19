package kgp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import kgp.model.Tarefa;
import kgp.model.Usuario;

public class TarefaDAO extends CrudDAO<Tarefa> {

	public Tarefa obterPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void desativaTarefa(Tarefa tarefa) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<Tarefa> listarTarefasAtivas() {
		Query query = getEntityManager().createQuery(
				"SELECT t FROM Tarefa t WHERE t.ativo = 1");
		return (List<Tarefa>) query.getResultList();
	}
	
	

	public void salvar(final Tarefa t, final Usuario u) {
		transacao(new Transacao() {
			@Override
			public void executar(EntityManager em) {
				t.setAtivo(true);
				t.setDataRegistro(new Date());
				em.persist(t);
			}
		});
	}

	public void atualizar(final Tarefa t, final Usuario u) {
		transacao(new Transacao() {

			@Override
			public void executar(EntityManager em) {
				t.setDataRegistro(new Date());
				em.merge(t);
			}
		});
	}

	public void atualizar(Tarefa t) {
		throw new RuntimeException(
				"Para atualizar uma tarefa, utilize atualizar(tarefa, usuario)");
	}

	public void salvar(Tarefa t) {
		throw new RuntimeException(
				"Para salvar uma tarefa, utilize salvar(tarefa, usuario)");
	}

	@SuppressWarnings("unchecked")
	public List<Tarefa> listarTarefasAtivasPorIteracao(Integer iteracaoId) {
		Query query = getEntityManager()
				.createQuery(
						"SELECT t FROM Tarefa t WHERE t.ativo = 1 and t.iteracao.id = :iteracaoId");
		query.setParameter("iteracaoId", iteracaoId);
		return (List<Tarefa>) query.getResultList();
	}

}
