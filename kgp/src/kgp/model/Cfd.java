package kgp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cfd database table.
 * 
 */
@Entity
public class Cfd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int dia;

	@Column(name="id_projeto")
	private int idProjeto;

	private int mes;

	@Column(name="qtde_tarefa")
	private int qtdeTarefa;

	private int situacao;

	public Cfd() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDia() {
		return this.dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getIdProjeto() {
		return this.idProjeto;
	}

	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}

	public int getMes() {
		return this.mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getQtdeTarefa() {
		return this.qtdeTarefa;
	}

	public void setQtdeTarefa(int qtdeTarefa) {
		this.qtdeTarefa = qtdeTarefa;
	}

	public int getSituacao() {
		return this.situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

}