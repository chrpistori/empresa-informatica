package kgp.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.ProjetoDAO;
import kgp.dao.UsuarioProjetoDAO;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Usuario;
import kgp.model.UsuarioProjeto;

import org.primefaces.model.chart.CartesianChartModel;

@ManagedBean(name = "projetoEditController")
@ViewScoped
public class ProjetoEditController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Projeto projeto = new Projeto();
	private List<Iteracao> listaIteracoes;
	private List<Usuario> usuariosSelecionados;

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	CartesianChartModel categoryModel = new CartesianChartModel();

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			if (projeto.getDataTermino().compareTo(projeto.getDataInicio()) < 0) {
				FacesMessage facesMessage = new FacesMessage(
						"A data de término não pode ser menor do que a data de início.");
				context.addMessage(null, facesMessage);
				return "/restrito/projeto?faces-redirect=true";
			}

			ProjetoDAO projetoDAO = new ProjetoDAO();

			if (usuarioLogado.getPerfil() == 1) {
				projeto.setConta(usuarioLogado.getId());
			} else {
				projeto.setConta(usuarioLogado.getConta());
			}

			projeto.setCriador(usuarioLogado.getId());

			projetoDAO.salvar(projeto);

			UsuarioProjetoDAO upDAO = new UsuarioProjetoDAO();
			for (Usuario u : usuariosSelecionados) {
				UsuarioProjeto up = new UsuarioProjeto();
				up.setUsuario(u);
				up.setProjeto(projeto);
				up.setDataRegistro(new Date());
				upDAO.salvar(up);
			}
			projeto = new Projeto();

			FacesMessage facesMessage = new FacesMessage(
					"Projeto inserido com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/projetos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar o projeto.");
			context.addMessage(null, facesMessage);
			return "/restrito/projeto?faces-redirect=true";
		}

	}

	public void preparar(Integer id) {
		ProjetoDAO projetoDAO = new ProjetoDAO();
		projeto = projetoDAO.obterPorId(id);
		listaIteracoes = projetoDAO.listarIteracao(projeto.getId());
		projeto.setIteracoes(listaIteracoes);
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			ProjetoDAO projetoDAO = new ProjetoDAO();
			projetoDAO.desativaProjeto(projeto);

			FacesMessage facesMessage = new FacesMessage(
					"Projeto excluído com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/projetos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o projeto.");
			context.addMessage(null, facesMessage);
			return "/restrito/projetos?faces-redirect=true";
		}
	}

	public String atualizar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			if (projeto.getDataTermino().compareTo(projeto.getDataInicio()) < 0) {
				FacesMessage facesMessage = new FacesMessage(
						"A data de término não pode ser menor do que a data de início.");
				context.addMessage(null, facesMessage);
				return "/restrito/projeto_editar?faces-redirect=true";
			}

			ProjetoDAO projetoDAO = new ProjetoDAO();
			projetoDAO.atualizar(projeto);

			UsuarioProjetoDAO upDAO = new UsuarioProjetoDAO();
			for (Usuario u : usuariosSelecionados) {
				UsuarioProjeto up = new UsuarioProjeto();
				up.setUsuario(u);
				up.setProjeto(projeto);
				up.setDataRegistro(new Date());
				upDAO.salvar(up);
			}
			projeto = new Projeto();

			FacesMessage facesMessage = new FacesMessage(
					"Projeto editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/projetos?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o projeto.");
			context.addMessage(null, facesMessage);
			return "/restrito/projeto_editar?faces-redirect=true";
		}

	}

	// Getters & Setters

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Iteracao> getListaIteracoes() {
		return this.listaIteracoes;
	}

	public List<Usuario> getUsuariosSelecionados() {
		return usuariosSelecionados;
	}

	public void setUsuariosSelecionados(List<Usuario> usuariosSelecionados) {
		this.usuariosSelecionados = usuariosSelecionados;
	}

	public CartesianChartModel getCategoryModel() {
		if (categoryModel == null) {
			categoryModel = new CartesianChartModel();
		}
		return categoryModel;
	}

	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
