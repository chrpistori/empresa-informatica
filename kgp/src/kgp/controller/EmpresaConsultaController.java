package kgp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import kgp.dao.EmpresaDAO;
import kgp.model.Empresa;

@ManagedBean(name = "empresaConsultaController")
@RequestScoped
public class EmpresaConsultaController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4046227005479097660L;
	
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
