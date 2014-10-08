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

	public List<Empresa> getLista() {
		return new EmpresaDAO().listarEmpresasAtivos();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
