package kgp.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kgp.dao.ClienteDAO;
import kgp.model.Cliente;
import kgp.model.Usuario;

import org.primefaces.model.SelectableDataModel;

@ManagedBean(name = "clienteEditController")
@ViewScoped
public class ClienteEditController implements SelectableDataModel<Cliente> {

	private Cliente cliente = new Cliente();
	ClienteDAO clienteDAO = new ClienteDAO();

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			cliente.setConta(usuarioLogado.getId());
			clienteDAO.salvar(cliente);

			cliente = new Cliente();
			FacesMessage facesMessage = new FacesMessage(
					"Cliente inserido com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/clientes?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao salvar o cliente.");
			context.addMessage(null, facesMessage);
			return "/restrito/cliente?faces-redirect=true";
		}
	}

	public void preparar(Integer id) {
		ClienteDAO clienteDao = new ClienteDAO();
		cliente = clienteDao.obterPorId(id);
	}

	public String atualizar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			clienteDAO.atualizar(cliente);

			FacesMessage facesMessage = new FacesMessage(
					"Cliente editado com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/clientes?faces-redirect=true";
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao editar o cliente.");
			context.addMessage(null, facesMessage);
			return "/restrito/clientes?faces-redirect=true";
		}
	}

	public String excluir() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.desativaCliente(cliente);

			FacesMessage facesMessage = new FacesMessage(
					"Cliente excluído com sucesso.");
			context.addMessage(null, facesMessage);
			return "/restrito/clientes?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage facesMessage = new FacesMessage(
					"Falha ao excluir o cliente.");
			context.addMessage(null, facesMessage);
			return "/restrito/clientes?faces-redirect=true";
		}
	}

	// Getters & Setters

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public Cliente getRowData(String arg0) {
		return null;
	}

	@Override
	public Object getRowKey(Cliente arg0) {
		return null;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
