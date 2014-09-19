package kgp.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the burndown database table.
 * 
 */
@Entity
public class Burndown implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int dia;

	@Column(name="horas_pendentes")
	private int horasPendentes;

	@Column(name="id_iteracao")
	private int idIteracao;

	private int mes;

	public Burndown() {
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

	public int getHorasPendentes() {
		return this.horasPendentes;
	}

	public void setHorasPendentes(int horasPendentes) {
		this.horasPendentes = horasPendentes;
	}

	public int getIdIteracao() {
		return this.idIteracao;
	}

	public void setIdIteracao(int idIteracao) {
		this.idIteracao = idIteracao;
	}

	public int getMes() {
		return this.mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

}