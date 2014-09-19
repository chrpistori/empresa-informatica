package kgp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the iteracao database table.
 * 
 */
@Entity
public class Iteracao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private boolean ativo;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inicio")
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="data_registro")
	private Date dataRegistro;

	@Temporal(TemporalType.DATE)
	@Column(name="data_termino")
	private Date dataTermino;

	@Column(name="limite_tarefas")
	private int limiteTarefas;

	private String nome;

	//bi-directional many-to-one association to Projeto
	@ManyToOne
	@JoinColumn(name="id_projeto")
	private Projeto projeto;

	//bi-directional many-to-one association to Tarefa
	@OneToMany(mappedBy="iteracao", fetch=FetchType.EAGER)
	private List<Tarefa> tarefas;

	public Iteracao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataRegistro() {
		return this.dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Date getDataTermino() {
		return this.dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public int getLimiteTarefas() {
		return this.limiteTarefas;
	}

	public void setLimiteTarefas(int limiteTarefas) {
		this.limiteTarefas = limiteTarefas;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Projeto getProjeto() {
		return this.projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public List<Tarefa> getTarefas() {
		return this.tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

}