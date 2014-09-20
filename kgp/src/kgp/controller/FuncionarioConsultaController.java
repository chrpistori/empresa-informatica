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

	public List<Funcionario> getLista() {
		return new FuncionarioDAO().listarFuncionariosAtivos();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
