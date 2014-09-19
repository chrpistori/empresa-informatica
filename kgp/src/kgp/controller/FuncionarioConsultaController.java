package kgp.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.FuncionarioDAO;
import kgp.model.Funcionario;

@ManagedBean(name = "funcionarioConsultaController")
@RequestScoped
public class FuncionarioConsultaController {

	private Funcionario funcionario = new Funcionario();
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	private List<Funcionario> lista;

	// Getters & Setters

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getLista() {
		if (this.lista == null) {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				this.lista = funcionarioDAO.listarFuncionariosAtivos();
		}
		return this.lista;
	}

}
