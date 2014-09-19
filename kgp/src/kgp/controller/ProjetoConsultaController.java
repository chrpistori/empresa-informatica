package kgp.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import kgp.dao.ProjetoDAO;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Usuario;

@ManagedBean(name = "projetoConsultaController")
@RequestScoped
public class ProjetoConsultaController implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Projeto> lista;
	private List<Iteracao> listaIteracoes;
	private Projeto projeto = new Projeto();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	public List<Iteracao> listarIteracoes() {

		ProjetoDAO projetoDAO = new ProjetoDAO();
		this.listaIteracoes = projetoDAO.listarIteracao(projeto.getId());
		return this.listaIteracoes;
	}

	// Getters & Setters

	public List<Iteracao> getListaIteracoes() {
		if (this.listaIteracoes == null) {
			ProjetoDAO projetoDAO = new ProjetoDAO();
			this.listaIteracoes = projetoDAO.listarIteracao(projeto.getId());
		}
		return this.listaIteracoes;
	}

	public void setListaIteracoes(List<Iteracao> listaIteracoes) {
		this.listaIteracoes = listaIteracoes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setLista(List<Projeto> lista) {
		this.lista = lista;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Projeto> getLista() {
		if (this.lista == null) {
			ProjetoDAO projetoDAO = new ProjetoDAO();

			this.lista = projetoDAO.listarProjetosAtivos(usuarioLogado);
		}
		return this.lista;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
