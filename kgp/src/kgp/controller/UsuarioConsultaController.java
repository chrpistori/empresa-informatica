package kgp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import kgp.dao.UsuarioDAO;
import kgp.model.Usuario;

@ManagedBean(name = "usuarioConsultaController")
@RequestScoped
public class UsuarioConsultaController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private List<Usuario> lista;
	private String confirmarSenha;
	private List<Usuario> listaMembros;
	private List<Usuario> listaGerentes;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	// Getters & Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLista() {
		if (lista == null) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			if (usuarioLogado.getPerfil() == 1) {
				lista = usuarioDAO.listarUsuariosAtivos(usuarioLogado.getId());
			} else {

				lista = usuarioDAO.listarUsuariosAtivos(usuarioLogado
						.getConta());
			}
		}
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public List<Usuario> getListaMembros() {
		if (this.listaMembros == null) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			if (usuarioLogado.getPerfil() == 1) {
				this.listaMembros = usuarioDAO
						.listarUsuariosAtivos(usuarioLogado.getId());
			} else {

				this.listaMembros = usuarioDAO
						.listarUsuariosAtivos(usuarioLogado.getConta());
			}

		}
		return this.listaMembros;
	}

	public void setListaMembros(List<Usuario> listaMembros) {
		this.listaMembros = listaMembros;
	}

	public List<Usuario> getListaGerentes() {
		if (this.listaGerentes == null) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			if (usuarioLogado.getPerfil() == 1) {
				this.listaGerentes = usuarioDAO.listarGerentes(usuarioLogado
						.getId());
			} else {

				this.listaGerentes = usuarioDAO.listarGerentes(usuarioLogado
						.getConta());
			}

		}
		return this.listaGerentes;
	}

	public void setListaGerentes(List<Usuario> listaGerentes) {
		this.listaGerentes = listaGerentes;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
