package kgp.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kgp.controller.LoginController;
import kgp.controller.Paginas;
import kgp.model.Usuario;

public class AuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		LoginController loginController = (LoginController) req.getSession()
				.getAttribute("loginController");

		if (loginController != null && loginController.getUsuarioLogado() != null) {
			boolean podeAcessar = false;

			Usuario usuarioLogado = loginController.getUsuarioLogado();
			if (usuarioLogado == null) {
				denyAccess(req, response);
			} else {
				List<String> paginasPermitidas = new Paginas().getPaginasPorPerfil(usuarioLogado.getTipoPerfil());

				for (String pagina : paginasPermitidas) {
					if (req.getRequestURI().endsWith(pagina)) {
						podeAcessar = true;
					}
				}

				if (podeAcessar) {
					chain.doFilter(request, response);
				} else {
					denyAccess(req, response);
				}
			}

		} else {
			denyAccess(req, response);
		}
	}

	private void denyAccess(ServletRequest request, ServletResponse response)
			throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(req.getContextPath() + "/publico/acesso_negado.jsf");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
