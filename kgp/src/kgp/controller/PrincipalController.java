package kgp.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import kgp.model.Usuario;

@ManagedBean
@SessionScoped
public class PrincipalController {

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	// Renderers
	
	public boolean renderAdm() {
		switch (usuarioLogado.getTipoPerfil()) {
		case ADMINISTRADOR:
			return true;
		default:
			return false;
		}
	}
	
	public boolean renderAdmGer() {
		switch (usuarioLogado.getTipoPerfil()) {
		case ADMINISTRADOR:
		case GERENTE:
			return true;
		default:
			return false;
		}
	}
	
	public boolean renderMembro() {
		switch (usuarioLogado.getTipoPerfil()) {
		case MEMBRO:
			return true;
		default:
			return false;
		}
	}
	
	// Getters & Setters

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
