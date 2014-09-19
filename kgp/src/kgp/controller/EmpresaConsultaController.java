package kgp.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.EmpresaDAO;
import kgp.model.Empresa;

@ManagedBean(name = "empresaConsultaController")
@RequestScoped
public class EmpresaConsultaController {

	private Empresa empresa = new Empresa();
	EmpresaDAO empresaDAO = new EmpresaDAO();
	private List<Empresa> lista;

	// Getters & Setters

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getLista() {
		if (this.lista == null) {
			EmpresaDAO empresaDAO = new EmpresaDAO();
				this.lista = empresaDAO.listarEmpresasAtivos();
		}
		return this.lista;
	}

}
