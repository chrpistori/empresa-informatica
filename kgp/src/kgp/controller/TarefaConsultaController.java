package kgp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.TarefaDAO;
import kgp.model.Tarefa;

@ManagedBean (name = "tarefaConsultaController")
@RequestScoped
public class TarefaConsultaController implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tarefa> lista;
	
	// Getters & Setters

	public List<Tarefa> getLista() {
		if (lista == null) {
			TarefaDAO tarefaDAO = new TarefaDAO();
			lista = tarefaDAO.listarTarefasAtivas();
		}
		return lista;
	}

	public void setLista(List<Tarefa> lista) {
		this.lista = lista;
	}
}
