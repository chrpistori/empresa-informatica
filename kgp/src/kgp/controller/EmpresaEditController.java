package kgp.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.EmpresaDAO;
import kgp.model.Empresa;

@ManagedBean(name = "empresaEditController")
@ViewScoped
public class EmpresaEditController {

	private Empresa empresa = new Empresa();
	EmpresaDAO empresaDAO = new EmpresaDAO();

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			empresaDAO.salvar(empresa);

			empresa = new Empresa();
			FacesMessage facesMessage = new FacesMessage(
					"Empresa inserido com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresas?faces-redirect=true"; // TODO

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar o empresa.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresa?faces-redirect=true"; // TODO
		}
	}

	public void preparar(Integer id) {
		EmpresaDAO empresaDao = new EmpresaDAO();
		empresa = empresaDao.obterPorId(id);
	}

	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			empresaDAO.atualizar(empresa);

			FacesMessage facesMessage = new FacesMessage(
					"Empresa editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresas?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o empresa.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresas?faces-redirect=true";
		}
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresaDAO.desativaEmpresa(empresa);

			FacesMessage facesMessage = new FacesMessage(
					"Empresa excluído com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresas?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o empresa.");
			context.addMessage(null, facesMessage);
			return "/restrito/empresas?faces-redirect=true";
		}
	}

	// Getters & Setters

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
