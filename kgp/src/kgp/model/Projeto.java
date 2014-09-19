package kgp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the projeto database table.
 * 
 */
@Entity
public class Projeto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private boolean ativo;

	private int conta;

	private int criador;

	@Temporal(TemporalType.DATE)
	@Column(name="data_registro")
	private Date dataRegistro;

	@Temporal(TemporalType.DATE)
	@Column(name="data_termino")
	private Date dataTermino;

	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	private String descricao;

	private String nome;

	//bi-directional many-to-one association to Iteracao
	@OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER)
	private List<Iteracao> iteracoes;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_gerente")
	private Usuario gerente;

	//bi-directional many-to-one association to UsuarioProjeto
	@OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER)
	private List<UsuarioProjeto> usuarioProjetos;

	public Projeto() {
	}

	public int getId() {
		return id;
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

	public int getConta() {
		return this.conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	public int getCriador() {
		return this.criador;
	}

	public void setCriador(int criador) {
		this.criador = criador;
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

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Iteracao> getIteracoes() {
		return this.iteracoes;
	}

	public void setIteracoes(List<Iteracao> iteracoes) {
		this.iteracoes = iteracoes;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getGerente() {
		return this.gerente;
	}

	public void setGerente(Usuario gerente) {
		this.gerente = gerente;
	}

	public List<UsuarioProjeto> getUsuarioProjetos() {
		return this.usuarioProjetos;
	}

	public void setUsuarioProjetos(List<UsuarioProjeto> usuarioProjetos) {
		this.usuarioProjetos = usuarioProjetos;
	}

}