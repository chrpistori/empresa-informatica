package kgp.dao;

import java.util.List;

import javax.persistence.Query;

import kgp.model.Empresa;
import kgp.model.Funcionario;

public class EmpresaDAO extends CrudDAO<Empresa> {
	public void salvar(Empresa e) {
		e.setAtivo(true);
		super.salvar(e);
	}

	public void desativaEmpresa(Empresa e) {
		e.setAtivo(false);
		
		// Desativando todos os funcionários pertencentes à empresa
		// TODO: Informar ao usuário que esta ação será realizada
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		List<Funcionario> funcionarios = e.getFuncionarios();
		for (Funcionario funcionario : funcionarios) {
			funcionario.setAtivo(false);
			funcionarioDAO.atualizar(funcionario);
		}
		
		super.atualizar(e);
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listarEmpresasAtivos() {
		Query query = getEntityManager()
				.createQuery(
						"SELECT f FROM Empresa f WHERE f.ativo = 1 ORDER BY f.nome");
		return query.getResultList();
	}

	public Empresa obterPorId(Integer id) {
		Query q = getEntityManager().createQuery(
				"SELECT f FROM Empresa f where f.id = :id");
		q.setParameter("id", id);
		return (Empresa) q.getSingleResult();
	}
	
	
}
