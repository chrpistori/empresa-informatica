package kgp.controller;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.TarefaDAO;
import kgp.dao.UsuarioTarefaDAO;
import kgp.model.Tarefa;
import kgp.model.Usuario;
import kgp.model.UsuarioTarefa;

@ManagedBean(name = "tarefaEditController")
@ViewScoped
public class TarefaEditController {
	Tarefa tarefa = new Tarefa();
	TarefaDAO tarefaDAO = new TarefaDAO();
	UsuarioTarefa usuarioTarefa = new UsuarioTarefa();
	UsuarioTarefaDAO usuarioTarefaDAO = new UsuarioTarefaDAO();
	Date dataRegistro = new Date();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	private int horasTrabalhadas = 0;

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			tarefaDAO.salvar(tarefa, usuarioLogado);

			tarefa = new Tarefa();
			FacesMessage facesMessage = new FacesMessage(
					"Tarefa inserida com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar a tarefa.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";

		}

	}

	public void preparar(Integer id) {
		TarefaDAO tarefaDAO = new TarefaDAO();
		tarefa = tarefaDAO.obterPorId(id);
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			TarefaDAO tarefaDAO = new TarefaDAO();
			tarefaDAO.desativaTarefa(tarefa);

			FacesMessage facesMessage = new FacesMessage(
					"Tarefa excluída com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o tarefa.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		}
	}

	public String atualizar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			tarefaDAO.atualizar(tarefa, usuarioLogado);

			FacesMessage facesMessage = new FacesMessage(
					"Tarefa editada com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar a tarefa.");
			context.addMessage(null, facesMessage);
			return "/restrito/principal?faces-redirect=true";
		}
	}

	public String salvarHorasTrabalhadas() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			usuarioTarefa.setTarefa(tarefa);
			usuarioTarefa.setUsuario(usuarioLogado);
			usuarioTarefa.setHorasTrabalhadas(horasTrabalhadas);
			usuarioTarefa.setDataRegistro(dataRegistro);

			usuarioTarefaDAO.salvar(usuarioTarefa);

			int falta = tarefa.getHorasPendentes() - horasTrabalhadas;

			tarefa.setHorasPendentes(falta);
			tarefaDAO.atualizar(tarefa, usuarioLogado);
			tarefaDAO = new TarefaDAO();

			usuarioTarefa = new UsuarioTarefa();
			FacesMessage facesMessage = new FacesMessage(
					"Horas atualizadas com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/tarefas?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar as horas.");
			context.addMessage(null, facesMessage);
			return "/restrito/tarefas?faces-redirect=true";

		}

	}

	// Getters & Setters

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public TarefaDAO getTarefaDAO() {
		return tarefaDAO;
	}

	public void setTarefaDAO(TarefaDAO tarefaDAO) {
		this.tarefaDAO = tarefaDAO;
	}

	public int getHorasTrabalhadas() {
		return horasTrabalhadas;

	}

	public void setHorasTrabalhadas(int horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
