package kgp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kgp.enums.Perfil;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private boolean ativo;

	private int conta;

	private int criador;

	@Temporal(TemporalType.DATE)
	@Column(name="data_registro")
	private Date dataRegistro;

	private String email;

	private String nome;

	private int perfil;

	private String senha;

	@Column(name="telefone_1")
	private String telefone1;

	@Column(name="telefone_2")
	private String telefone2;

	private String usuario;

	//bi-directional many-to-one association to Projeto
	@OneToMany(mappedBy = "gerente", fetch = FetchType.EAGER)
	private List<Projeto> projetos;

	//bi-directional many-to-one association to UsuarioProjeto
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<UsuarioProjeto> usuarioProjetos;

	//bi-directional many-to-one association to UsuarioTarefa
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<UsuarioTarefa> usuarioTarefas;

	public Usuario() {
	}
	
	public Perfil getTipoPerfil() {
		return Perfil.findByCodigo(perfil);
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPerfil() {
		return this.perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone1() {
		return this.telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return this.telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Projeto> getProjetos() {
		return this.projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<UsuarioProjeto> getUsuarioProjetos() {
		return this.usuarioProjetos;
	}

	public void setUsuarioProjetos(List<UsuarioProjeto> usuarioProjetos) {
		this.usuarioProjetos = usuarioProjetos;
	}

	public List<UsuarioTarefa> getUsuarioTarefas() {
		return this.usuarioTarefas;
	}

	public void setUsuarioTarefas(List<UsuarioTarefa> usuarioTarefas) {
		this.usuarioTarefas = usuarioTarefas;
	}

}