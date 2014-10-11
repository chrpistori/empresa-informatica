package kgp.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import kgp.dao.EmpresaDAO;
import kgp.dao.FuncionarioDAO;
import kgp.model.Empresa;
import kgp.model.Funcionario;

@ManagedBean(name = "funcionarioEditController")
@ViewScoped
public class FuncionarioEditController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083402389486863723L;

	private Funcionario funcionario = new Funcionario();
	private Integer codigoFuncionario;
	private Integer codigoEmpresa;
	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			funcionarioDAO.salvar(funcionario);

			funcionario = new Funcionario();
			FacesMessage facesMessage = new FacesMessage(
					"Funcionario inserido com sucesso.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionarios?faces-redirect=true"; // TODO

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar o funcionario.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionario?faces-redirect=true"; // TODO
		}
	}

	public void preparar(Integer id) {
		FuncionarioDAO funcionarioDao = new FuncionarioDAO();
		funcionario = funcionarioDao.obterPorId(id);
	}

	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			funcionarioDAO.atualizar(funcionario);

			FacesMessage facesMessage = new FacesMessage(
					"Funcionario editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionarios?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o funcionario.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionarios?faces-redirect=true";
		}
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.desativaFuncionario(funcionario);

			FacesMessage facesMessage = new FacesMessage(
					"Funcionario excluído com sucesso.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionarios?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o funcionario.");
			context.addMessage(null, facesMessage);
			return "/publico/funcionarios?faces-redirect=true";
		}
	}

	public void contratar(Integer codigoFuncionario, Integer codigoEmpresa) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
		Funcionario funcionario = funcionarioDAO.obterPorCodigo(codigoFuncionario);
		Empresa empresa = new EmpresaDAO().obterPorId(codigoEmpresa);
		funcionario.setEmpresa(empresa);
		
		funcionarioDAO.atualizar(funcionario);
		
		FacesMessage facesMessage = new FacesMessage(
			"Funcionario " + funcionario.getNome() + " contratado pela empresa " + empresa.getNome() + ".");
		context.addMessage(null, facesMessage);
		} catch(NoResultException nre) {
			nre.printStackTrace();
			context.addMessage(null, new FacesMessage("ID não foi encontrado!"));
		} catch(Exception ex) {
			ex.printStackTrace();
			context.addMessage(null, new FacesMessage("Erro ao fazer contratação!"));
		}
	}

	// Getters & Setters

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getCodigoFuncionario() {
		return codigoFuncionario;
	}

	public void setCodigoFuncionario(Integer codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}

	public Integer getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Integer codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
	}

}
