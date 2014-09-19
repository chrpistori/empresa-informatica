package kgp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import kgp.model.Cfd;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Usuario;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

public class ProjetoDAO extends CrudDAO<Projeto> {
	Projeto p = new Projeto();

	public void salvar(Projeto p) {
		p.setAtivo(true);
		super.salvar(p);
	}

	public void desativaProjeto(Projeto p) {
		p.setAtivo(false);
		super.atualizar(p);
	}

	public Integer getProximoId() {
		return ((Long) getEntityManager().createNativeQuery(
				"select max(id + 1) from projeto").getSingleResult())
				.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Projeto> listarProjetosAtivos(Usuario usuarioLogado) {
		Query query = null;
		if (usuarioLogado.getPerfil() == 1) {
		query = getEntityManager().createQuery(
				"SELECT p FROM Projeto p WHERE p.ativo = 1 and p.conta = :conta ORDER BY p.nome");
		query.setParameter("conta", usuarioLogado.getId());
		} else {
			query = getEntityManager().createQuery(
					"SELECT p FROM Projeto p WHERE p.ativo = 1 and p.criador = :criador ORDER BY p.nome");
			query.setParameter("criador", usuarioLogado.getId());
		}
		return query.getResultList();
	}

	public Projeto obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT p FROM Projeto p where p.id = :id");
		q.setParameter("id", id);
		return (Projeto) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Iteracao> listarIteracao(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT i FROM Iteracao i WHERE i.projeto.id = :id");
		q.setParameter("id", id);
		return q.getResultList();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Cfd> obterDadosCFD (Integer p, Integer mes) {
		Query q = getEntityManager().createNativeQuery(
				"SELECT " +
				"	count(0) as qtde_tarefa, " +
				"	t.situacao " +
				"FROM tarefa t " +
				"INNER JOIN iteracao i ON i.id = t.id_iteracao " +
				"WHERE i.id_projeto = ? " +
				"GROUP BY 2 " +
				"ORDER BY situacao"
				);
		q.setParameter(1, p);
		q.setParameter(2, mes);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		
		List<Cfd> listaCFD = new ArrayList<>();
		for (Map map : (List<Map>) q.getResultList()) {
			Cfd cfd = new Cfd();
			cfd.setQtdeTarefa(Integer.parseInt(((Long) map.get("qtde_tarefa")).toString()));
			cfd.setIdProjeto(p);
			cfd.setSituacao((Integer) map.get("situacao"));
			
			listaCFD.add(cfd);
		}
		return listaCFD;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cfd> graficoCFD(Integer p) {
		Query q = getEntityManager().createQuery(
				"SELECT c FROM Cfd c WHERE c.idProjeto = :p");
		q.setParameter("p", p);
		return (List<Cfd>) q.getResultList();
	}
}
