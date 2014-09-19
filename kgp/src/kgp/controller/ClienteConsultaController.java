package kgp.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import kgp.dao.ClienteDAO;
import kgp.model.Cliente;
import kgp.model.Usuario;

import org.primefaces.model.SelectableDataModel;

@ManagedBean(name = "clienteConsultaController")
@RequestScoped
public class ClienteConsultaController implements SelectableDataModel<Cliente> {

	@ManagedProperty("#{loginController.usuarioLogado}")
	private Usuario usuarioLogado;

	private Cliente cliente = new Cliente();
	ClienteDAO clienteDAO = new ClienteDAO();
	private List<Cliente> lista;

	// Getters & Setters

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getLista() {
		if (this.lista == null) {
			ClienteDAO clienteDAO = new ClienteDAO();
			if (usuarioLogado.getPerfil() == 1) {
				this.lista = clienteDAO.listarClientesAtivos(usuarioLogado
						.getId());
			} else {
				this.lista = clienteDAO.listarClientesAtivos(usuarioLogado
						.getConta());

			}
		}
		return this.lista;
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
