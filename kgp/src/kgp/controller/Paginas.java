package kgp.controller;

import java.util.Arrays;
import java.util.List;

import kgp.enums.Perfil;

public class Paginas {
	private final String CLIENTE = "cliente.jsf";
	private final String CLIENTES = "clientes.jsf";
	private final String PROJETO = "projeto.jsf";
	private final String PROJETOS = "projetos.jsf";
	private final String ITERACAO = "iteracao.jsf";
	private final String ITERACOES = "iteracoes.jsf";
	private final String MENU_INTERNO = "menu_interno.jsf";
	private final String MENU_SISTEMA = "menu_sistema.jsf";
	private final String PRINCIPAL = "principal.jsf";
	private final String USUARIO = "usuario.jsf";
	private final String USUARIOS = "usuarios.jsf";
	private final String TAREFAS = "tarefas.jsf";
	private final String CFD = "cfd.jsf";
	private final String BURNDOWN = "burndown.jsf";

	public List<String> getPaginasPorPerfil(Perfil perfil) {
		switch (perfil) {
		case ADMINISTRADOR:
			return Arrays.asList(CLIENTE, CLIENTES, PROJETO, PROJETOS,
					ITERACAO, ITERACOES, MENU_INTERNO, MENU_SISTEMA, PRINCIPAL,
					USUARIO, USUARIOS, TAREFAS, CFD, BURNDOWN);

		case GERENTE:
			return Arrays.asList(PROJETO, CLIENTES, PROJETOS, ITERACAO,
					ITERACOES, MENU_INTERNO, MENU_SISTEMA, PRINCIPAL, USUARIO,
					USUARIOS, TAREFAS, CFD, BURNDOWN);

		case MEMBRO:
			return Arrays.asList(PROJETOS, MENU_INTERNO, MENU_SISTEMA,
					PRINCIPAL, TAREFAS, CFD, BURNDOWN);
		default:
			throw new RuntimeException(
					"Não foi encontrado nenhum perfil igual a " + perfil);
		}
	}
}
