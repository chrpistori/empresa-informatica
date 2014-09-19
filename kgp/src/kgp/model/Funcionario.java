package kgp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;

	private boolean ativo;

	private int cargo;

	private int codigo;

	private BigDecimal comissao;

	@Column(name="horas_trabalhadas")
	private BigDecimal horasTrabalhadas;

	private String nome;

	private BigDecimal salario;

	@Column(name="valor_hora")
	private BigDecimal valorHora;

	//bi-directional many-to-one association to Empresa
	@ManyToOne
	@JoinColumn(name="id_empresa")
	private Empresa empresa;

	public Funcionario() {
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

	public int getCargo() {
		return this.cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getComissao() {
		return this.comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public BigDecimal getHorasTrabalhadas() {
		return this.horasTrabalhadas;
	}

	public void setHorasTrabalhadas(BigDecimal horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSalario() {
		return this.salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public BigDecimal getValorHora() {
		return this.valorHora;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}