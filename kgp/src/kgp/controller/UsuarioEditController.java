package kgp.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.UsuarioDAO;
import kgp.model.Usuario;

@ManagedBean(name = "usuarioEditController")
@ViewScoped
public class UsuarioEditController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			String senha = this.usuario.getSenha();

			if (!senha.equals(this.confirmarSenha)) {
				FacesMessage facesMessage = new FacesMessage(
						"A senha não foi confirmada corretamente");
				context.addMessage(null, facesMessage);
				return "/restrito/usuario?faces-redirect=true";
			}

			usuario.setDataRegistro(new Date());

			if (usuarioLogado.getPerfil() == 1) {
				usuario.setConta(usuarioLogado.getId());
			} else {
				usuario.setConta(usuarioLogado.getConta());
			}
			usuario.setCriador(usuarioLogado.getId());
			usuarioDAO.salvar(usuario);

			usuario = new Usuario();
			FacesMessage facesMessage = new FacesMessage(
					"Usuário inserido com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuarios?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar o usuário.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuario?faces-redirect=true";
		}

	}

	public String salvarAdmin() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			usuario.setDataRegistro(new Date());

			usuario.setPerfil(1);
			usuarioDAO.salvar(usuario);

			usuario = new Usuario();
			FacesMessage facesMessage = new FacesMessage(
					"Cadastro realizado com sucesso. Entre com o usuário e senha.");
			context.addMessage(null, facesMessage);
			return "/publico/login?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao realizar o cadastro.");
			context.addMessage(null, facesMessage);
			return "/publico/cadastro?faces-redirect=true";
		}

	}

	public void preparar(Integer id) {
		UsuarioDAO usuarioDao = new UsuarioDAO();
		usuario = usuarioDao.obterPorId(id);
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.desativaUsuario(usuario);

			FacesMessage facesMessage = new FacesMessage(
					"Usuário excluído com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuarios?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o usuário.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuarios?faces-redirect=true";
		}
	}

	public String atualizar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			usuarioDAO.atualizar(usuario);

			FacesMessage facesMessage = new FacesMessage(
					"Usuário editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuarios?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o usuário.");
			context.addMessage(null, facesMessage);
			return "/restrito/usuarios?faces-redirect=true";
		}
	}

	// Getters & Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
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
