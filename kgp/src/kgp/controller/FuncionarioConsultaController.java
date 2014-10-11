package kgp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.FuncionarioDAO;
import kgp.model.Funcionario;

@ManagedBean(name = "funcionarioConsultaController")
@RequestScoped
public class FuncionarioConsultaController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5147607811331389046L;
	private Funcionario funcionario = new Funcionario();
	private List<Funcionario> funcionariosEmpresa = new ArrayList<Funcionario>();

	public List<Funcionario> getLista() {
		return new FuncionarioDAO().listarFuncionariosAtivos();
	}
	
	public void buscarFuncionarios(Integer empresaId) {
		this.setFuncionariosEmpresa(new FuncionarioDAO().listarFuncionariosEmpresa(empresaId));
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionariosEmpresa() {
		return funcionariosEmpresa;
	}

	public void setFuncionariosEmpresa(List<Funcionario> funcionariosEmpresa) {
		this.funcionariosEmpresa = funcionariosEmpresa;
	}
}
