package kgp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.IteracaoDAO;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Tarefa;

@ManagedBean(name = "iteracaoConsultaController")
@RequestScoped
public class IteracaoConsultaController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Iteracao> lista;
	private List<Tarefa> listaTarefas;

	Projeto projeto = new Projeto();
	Iteracao iteracao = new Iteracao();
	Tarefa tarefa = new Tarefa();

	// Getters & Setters
	
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Tarefa> getListaTarefas() {
		if (listaTarefas == null) {
			IteracaoDAO iteracaoDAO = new IteracaoDAO();
			listaTarefas = iteracaoDAO.listarTarefas(iteracao);
		}
		return listaTarefas;
	}

	public void setListaTarefas(List<Tarefa> listaTarefas) {
		this.listaTarefas = listaTarefas;
	}

	public Iteracao getIteracao() {
		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public List<Iteracao> getLista() {
		if (lista == null) {
			IteracaoDAO iteracaoDAO = new IteracaoDAO();
			lista = iteracaoDAO.listarIteracoesAtivos();
		}
		return lista;
	}
	
}
