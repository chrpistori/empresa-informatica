package kgp.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.IteracaoDAO;
import kgp.dao.TarefaDAO;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Tarefa;
import kgp.model.Usuario;

@ManagedBean(name = "iteracaoEditController")
@ViewScoped
public class IteracaoEditController implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Projeto projeto = new Projeto();
	Iteracao iteracao = new Iteracao();
	Tarefa tarefa = new Tarefa();
	
	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	public void salvarTarefa() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			TarefaDAO tarefaDAO = new TarefaDAO();
			tarefa.setIteracao(iteracao);
			tarefa.setSituacao(1);
			tarefaDAO.salvar(tarefa, usuarioLogado);
			tarefa = new Tarefa();

			FacesMessage facesMessage = new FacesMessage(
					"Tarefa cadastrada com sucesso.");
			context.addMessage(null, facesMessage);

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao cadastrar a tarefa.");
			context.addMessage(null, facesMessage);

		}

	}

	public String salvar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			if (iteracao.getDataTermino().compareTo(iteracao.getDataInicio()) < 0) {
				FacesMessage facesMessage = new FacesMessage(
						"A data de término não pode ser menor do que a data de início.");
				context.addMessage(null, facesMessage);
				return "";
			}

			if (iteracao.getProjeto().getDataTermino()
					.compareTo(iteracao.getDataTermino()) < 0) {
				FacesMessage facesMessage = new FacesMessage(
						"A data de término da iteração não pode ser maior do que a data de término do projeto.");
				context.addMessage(null, facesMessage);
				return "/restrito/iteracoes?faces-redirect=true";
			} else if (iteracao.getDataInicio().compareTo(
					iteracao.getProjeto().getDataInicio()) < 0) {
				FacesMessage facesMessage = new FacesMessage(
						"A data de início da iteração não pode ser menor do que a data de início do projeto.");
				context.addMessage(null, facesMessage);
				return "";

			}

			IteracaoDAO iteracaoDAO = new IteracaoDAO();
			iteracaoDAO.salvar(iteracao);
			iteracao = new Iteracao();

			FacesMessage facesMessage = new FacesMessage(
					"Iteração cadastrada com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/iteracoes?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao cadastrar a iteração.");
			context.addMessage(null, facesMessage);
			return "/restrito/iteracao?faces-redirect=true";
		}
	}

	public void preparar(Integer id) {
		IteracaoDAO iteracaoDao = new IteracaoDAO();
		iteracao = iteracaoDao.obterPorId(id);
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			IteracaoDAO iteracaoDAO = new IteracaoDAO();
			iteracaoDAO.desativaIteracao(iteracao);

			FacesMessage facesMessage = new FacesMessage(
					"Iteração excluída com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/iteracoes?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir a iteração.");
			context.addMessage(null, facesMessage);
			return "/restrito/iteracoes?faces-redirect=true";
		}
	}

	// Getters & Setters

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public Iteracao getIteracao() {
		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}
}
