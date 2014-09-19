package kgp.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import kgp.dao.UsuarioDAO;
import kgp.model.Usuario;

@ManagedBean
@SessionScoped
public class LoginController {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private Usuario usuarioLogado;
	private Usuario usuario = new Usuario();

	public boolean validaLogin() {
		FacesMessage msg = null;

		try {
			usuarioLogado = usuarioDAO.obterPorLogin(usuario.getUsuario(),
					usuario.getSenha());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Seja bem vindo(a) ", usuarioLogado.getNome());
			return true;
		} catch (Exception e) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário ou senha incorretos.", null);
			return false;
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String atualizar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			usuarioDAO.atualizar(usuarioLogado);

			FacesMessage facesMessage = new FacesMessage(
					"Usuário editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o usuário.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		}
	}

	public String logado() {
		if (validaLogin())
			return "/restrito/principal?faces-redirect=true";
		return "";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/publico/login?faces-redirect=true";
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
