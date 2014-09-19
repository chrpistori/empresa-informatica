package kgp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kgp.enums.Prioridade;


/**
 * The persistent class for the tarefa database table.
 * 
 */
@Entity
public class Tarefa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private boolean ativo;

	@Temporal(TemporalType.DATE)
	@Column(name="data_registro")
	private Date dataRegistro;

	private String descricao;

	private int duracao;

	@Column(name="horas_pendentes")
	private int horasPendentes;

	private String nome;

	private int prioridade;

	private int situacao;

	//bi-directional many-to-one association to Iteracao
	@ManyToOne
	@JoinColumn(name="id_iteracao")
	private Iteracao iteracao;
	
	//bi-directional many-to-one association to UsuarioTarefa
	@OneToMany(mappedBy="tarefa", fetch=FetchType.EAGER)
	private List<UsuarioTarefa> usuarioTarefas;


	public Tarefa() {
	}
	
	public Prioridade getTipoPrioridade() {
		return Prioridade.findByCodigo(prioridade); 
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

	public Date getDataRegistro() {
		return this.dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getDuracao() {
		return this.duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public int getHorasPendentes() {
		return this.horasPendentes;
	}

	public void setHorasPendentes(int horasPendentes) {
		this.horasPendentes = horasPendentes;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPrioridade() {
		return this.prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public int getSituacao() {
		return this.situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Iteracao getIteracao() {
		return this.iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public List<UsuarioTarefa> getUsuarioTarefas() {
		return this.usuarioTarefas;
	}

	public void setUsuarioTarefas(List<UsuarioTarefa> usuarioTarefas) {
		this.usuarioTarefas = usuarioTarefas;
	}
}