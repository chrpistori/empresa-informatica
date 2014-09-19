package kgp.dao;

import javax.persistence.Query;

import kgp.model.Cfd;

public class CfdDAO extends CrudDAO<Cfd> {

	public Cfd obterCfd(int mes, int dia, int situacao) {
		Query q = getEntityManager().createQuery(
				"SELECT c FROM Cfd c WHERE c.dia = :dia AND c.situacao = :situacao");
		q.setParameter("dia", dia);
		q.setParameter("situacao", situacao);
		return (Cfd) q.getSingleResult();
	}

	public Integer obterTarefasPorColuna(int dia, int mes, int situacao) {
		Query q = getEntityManager().createNativeQuery(
				"SELECT count(0) as qtde_situacao FROM tarefa t WHERE EXTRACT(MONTH FROM t.data_registro) = ? AND EXTRACT(DAY FROM t.data_registro) = ? AND t.situacao = ?");
		q.setParameter(1, dia);
		q.setParameter(2, mes);
		q.setParameter(3, situacao);
		
		Integer qtdeSituacao = Integer.parseInt(q.getSingleResult().toString());
		return qtdeSituacao;
	}
	
}
