package kgp.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import kgp.dao.CfdDAO;
import kgp.dao.IteracaoDAO;
import kgp.dao.ProjetoDAO;
import kgp.dao.TarefaDAO;
import kgp.enums.SituacaoTarefa;
import kgp.model.Cfd;
import kgp.model.Iteracao;
import kgp.model.Projeto;
import kgp.model.Tarefa;
import kgp.model.Usuario;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@ViewScoped
public class DashboardController implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_COLUMN_COUNT = 4;
	private int columnCount = DEFAULT_COLUMN_COUNT;

	private Dashboard dashboard;
	Map<String, Tarefa> tarefasMap;

	private Integer projetoId;
	private Integer iteracaoId;
	private Tarefa tarefaSelecionada;
	private Integer situacaoAnterior;
	private Tarefa tarefa = new Tarefa();
	private Iteracao iteracao = new Iteracao();
	private Projeto projeto = new Projeto();

	private CartesianChartModel cfdModel = new CartesianChartModel();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	int count = 0;

	public DashboardController() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Application application = null;
		application = fc.getApplication();
		dashboard = (Dashboard) application.createComponent(fc,
				"org.primefaces.component.Dashboard",
				"org.primefaces.component.DashboardRenderer");
		dashboard.setId("dashboard");
	}

	public void atualizarKanban() {
		tarefasMap = new HashMap<>();
		FacesContext fc = FacesContext.getCurrentInstance();
		Application application = fc.getApplication();

		DashboardModel model = new DefaultDashboardModel();

		DashboardColumn novo = new DefaultDashboardColumn();
		DashboardColumn aFazer = new DefaultDashboardColumn();
		DashboardColumn emAndamento = new DefaultDashboardColumn();
		DashboardColumn concluido = new DefaultDashboardColumn();

		model.addColumn(novo);
		model.addColumn(aFazer);
		model.addColumn(emAndamento);
		model.addColumn(concluido);

		dashboard.setModel(model);

		List<Tarefa> tarefas = new TarefaDAO()
				.listarTarefasAtivasPorIteracao(iteracaoId);

		for (int i = 0; i < tarefas.size(); i++) {
			Tarefa tarefa = tarefas.get(i);

			count++;

			String panelId = "panel_" + count;

			Panel panel = (Panel) application.createComponent(fc,
					"org.primefaces.component.Panel",
					"org.primefaces.component.PanelRenderer");
			panel.setStyleClass(getStyleClassFromTarefa(tarefa));
			panel.setId(panelId);
			panel.setHeader(tarefa.getNome());
			
			String descricaoTarefa = tarefa.getDescricao();

			HtmlOutputText descricaoOutputText = new HtmlOutputText();
			descricaoOutputText.setValue(descricaoTarefa);
			
			panel.getChildren().add(descricaoOutputText);

			tarefasMap.put("panel_" + count, tarefa);

			getDashboard().getChildren().add(panel);

			if (tarefa.getSituacao() == SituacaoTarefa.NOVO.getCodigo()) {
				novo.addWidget(panel.getId());
			} else if (tarefa.getSituacao() == SituacaoTarefa.A_FAZER
					.getCodigo()) {
				aFazer.addWidget(panel.getId());
			} else if (tarefa.getSituacao() == SituacaoTarefa.EM_ANDAMENTO
					.getCodigo()) {
				emAndamento.addWidget(panel.getId());
			} else if (tarefa.getSituacao() == SituacaoTarefa.CONCLUIDO
					.getCodigo()) {
				concluido.addWidget(panel.getId());
			}
		}
	}

	/** Método que retorna a tarefa ao estado inicial após o cancelamento
	 * 
	 */
	public void cancelarAtribuicao() {
		tarefaSelecionada.setSituacao(SituacaoTarefa.NOVO.getCodigo());
		atualizarKanban();
	}

	/** Método que é executado sempre que se solta uma tarefa para em outra coluna
	 * 
	 * @param event
	 */

	public void handleReorder(DashboardReorderEvent event) {

		String panelId = dashboard.getModel().getColumn(event.getColumnIndex())
				.getWidget(event.getItemIndex());
		tarefaSelecionada = tarefasMap.get(panelId);
		tarefaSelecionada.setDataRegistro(new Date());
		situacaoAnterior = tarefaSelecionada.getSituacao();

		if (event.getColumnIndex().equals(0)) {
			tarefaSelecionada.setSituacao(SituacaoTarefa.NOVO.getCodigo());
		} else if (event.getColumnIndex().equals(1)) {
			tarefaSelecionada.setSituacao(SituacaoTarefa.A_FAZER.getCodigo());
		} else if (event.getColumnIndex().equals(2)) {
			tarefaSelecionada.setSituacao(SituacaoTarefa.EM_ANDAMENTO
					.getCodigo());
		} else if (event.getColumnIndex().equals(3)) {
			tarefaSelecionada.setSituacao(SituacaoTarefa.CONCLUIDO.getCodigo());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		mes++;

		if (!renderModalAtribuir()) {
			TarefaDAO tarefaDAO = new TarefaDAO();
			tarefaDAO.atualizar(tarefaSelecionada, usuarioLogado);
			tarefaDAO.limpar();
		}
		
		List<Cfd> cfds = new ProjetoDAO().obterDadosCFD(projetoId, mes);
		CfdDAO cfdDAO = new CfdDAO();
		for (Cfd cfd : cfds) {
			try {
				cfd.setDia(dia);
				cfd.setMes(mes);
				Cfd registroCfd = cfdDAO.obterCfd(cfd.getMes(), cfd.getDia(), cfd.getSituacao());
				if (registroCfd != null)
					cfd.setId(registroCfd.getId());
					cfdDAO.atualizar(cfd);
			} catch (Exception e) {
				cfdDAO.salvar(cfd);
			}
		}
		
		atualizaCfd();

		String colunaOrigem = null;
		String colunaDestino = null;

		switch (event.getSenderColumnIndex()) {

		case 0:
			colunaOrigem = "Novo";
			break;
		case 1:
			colunaOrigem = "A Fazer";
			break;
		case 2:
			colunaOrigem = "Em Andamento";
			break;
		case 3:
			colunaOrigem = "Concluído";
			break;
		default:
			break;
		}

		switch (event.getColumnIndex()) {

		case 0:
			colunaDestino = "Novo";
			break;
		case 1:
			colunaDestino = "A Fazer";
			break;
		case 2:
			colunaDestino = "Em Andamento";
			break;
		case 3:
			colunaDestino = "Concluído";
			break;
		default:
			break;
		}

		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Tarefa movida");
		message.setDetail("da coluna " + colunaOrigem + " para a coluna: "
				+ colunaDestino);

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Renderers
	 * 
	 * @return boolean
	 */
	public boolean renderModalAtribuir() {
		return tarefaSelecionada != null && tarefaSelecionada.getSituacao() != SituacaoTarefa.NOVO.getCodigo()
				&& situacaoAnterior.equals(SituacaoTarefa.NOVO.getCodigo());
	}

	/**
	 *  Popula combobox de Iterações
	 * @return List<Iteracao>
	 */
	public List<Iteracao> listarIteracoes() {
		return new ProjetoDAO().listarIteracao(projetoId);
	}

	// Cadastro de Tarefa

	public void salvarTarefa() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			TarefaDAO tarefaDAO = new TarefaDAO();
			tarefa.setIteracao(new IteracaoDAO().obterPorId(iteracaoId));
			tarefa.setSituacao(SituacaoTarefa.NOVO.getCodigo());
			tarefa.setHorasPendentes(tarefa.getDuracao());
			tarefaDAO.salvar(tarefa, usuarioLogado);
			tarefa = new Tarefa();
			atualizarKanban();

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

	public void atualizarTarefa() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			TarefaDAO tarefaDAO = new TarefaDAO();
			tarefaDAO.atualizar(tarefaSelecionada, usuarioLogado);

			FacesMessage facesMessage = new FacesMessage(
					"Tarefa atualizada com sucesso.");
			context.addMessage(null, facesMessage);
			atualizarKanban();
		} catch (Exception e) {

			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao atualizar a tarefa.");
			context.addMessage(null, facesMessage);
		}
	}

	public String getStyleClassFromTarefa(Tarefa t) {
		switch (t.getTipoPrioridade()) {
		case ALTA:
			return "panel-alta-prioridade";
		case MEDIA:
			return "panel-media-prioridade";
		case BAIXA:
			return "panel-baixa-prioridade";
		default:
			return "panel-sem-prioridade";
		}

	}

	public CartesianChartModel mostraGraficoCFD() {
		cfdModel = new CartesianChartModel();

		List<Cfd> cfds = new ProjetoDAO().graficoCFD(projetoId);

		ChartSeries serie1 = new ChartSeries(SituacaoTarefa.NOVO.getDescricao());
		ChartSeries serie2 = new ChartSeries(SituacaoTarefa.A_FAZER.getDescricao());
		ChartSeries serie3 = new ChartSeries(SituacaoTarefa.EM_ANDAMENTO.getDescricao());
		ChartSeries serie4 = new ChartSeries(SituacaoTarefa.CONCLUIDO.getDescricao());

		for (Cfd cfd : cfds) {
			switch (cfd.getSituacao()) {
			case 1:
				serie1.set(String.valueOf(cfd.getDia()), cfd.getQtdeTarefa());
				break;
			case 2:
				serie2.set(String.valueOf(cfd.getDia()), cfd.getQtdeTarefa());
				break;
			case 3:
				serie3.set(String.valueOf(cfd.getDia()), cfd.getQtdeTarefa());
				break;
			case 4:
				serie4.set(String.valueOf(cfd.getDia()), cfd.getQtdeTarefa());
				break;
			default:
				throw new IllegalArgumentException("Nenhuma série para a situação " +
						cfd.getSituacao());
			}
		}

		cfdModel.addSeries(serie4);
		cfdModel.addSeries(serie3);
		cfdModel.addSeries(serie2);
		cfdModel.addSeries(serie1);

		return cfdModel;
	}
	
	public void atualizaCfd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		mes++;
		
		CfdDAO cfdDAO = new CfdDAO();
		for(int situacao = 1; situacao < 5; situacao++){
			try{
				Integer resultado = cfdDAO.obterTarefasPorColuna(mes, dia, situacao);
				if(resultado == 0) {
					Cfd cfdSemTarefa = cfdDAO.obterCfd(mes, dia, situacao);
					if(cfdSemTarefa != null) {
						cfdSemTarefa.setQtdeTarefa(0);
						cfdSemTarefa.setIdProjeto(projetoId);
						cfdDAO.atualizar(cfdSemTarefa);
					} else {
						cfdDAO.salvar(cfdSemTarefa);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Cfd cfdSemTarefa = new Cfd();
				cfdSemTarefa.setDia(dia);
				cfdSemTarefa.setMes(mes);
				cfdSemTarefa.setSituacao(situacao);
				cfdSemTarefa.setQtdeTarefa(0);
				cfdDAO.salvar(cfdSemTarefa);
			}
		}
	}

	public Date date() {
		return new Date();
	}

	public void editarTarefaSelecionada(String panelId) {
		tarefaSelecionada = tarefasMap.get(panelId);
	}

	// Getters & Setters

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}

	public static int getDefaultColumnCount() {
		return DEFAULT_COLUMN_COUNT;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}

	public Integer getSituacaoAnterior() {
		return situacaoAnterior;
	}

	public void setSituacaoAnterior(Integer situacaoAnterior) {
		this.situacaoAnterior = situacaoAnterior;
	}

	public Integer getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(Integer projetoId) {
		this.projetoId = projetoId;

		if (!projetoId.equals(null))
			projeto = new ProjetoDAO().obterPorId(projetoId);
		iteracao = null;
	}

	public Integer getIteracaoId() {
		return iteracaoId;
	}

	public void setIteracaoId(Integer iteracaoId) {
		this.iteracaoId = iteracaoId;

		if (!iteracaoId.equals(null))
			iteracao = new IteracaoDAO().obterPorId(iteracaoId);
	}

	public Tarefa getTarefaNova() {
		return tarefa;
	}

	public void setTarefaNova(Tarefa tarefaNova) {
		this.tarefa = tarefaNova;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public CartesianChartModel getCfdModel() {
		return cfdModel;
	}

	public void setCfdModel(CartesianChartModel cfdModel) {
		this.cfdModel = cfdModel;
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
