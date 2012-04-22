package br.com.integrator.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SegurancaFiltro implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpSession sessao = ((HttpServletRequest) request).getSession();
		
		//tenta capturar a sess�o chamada user
		String logado = (String)sessao.getAttribute("user");
		
		//verifica se a sess�o existe
		if(logado != null){
			chain.doFilter(request, response);
		}
		else{
			//envia uma mensagem caso o usu�rio
			//n�o tenha se logado
			sessao.setAttribute("msg", "Entre com o usu�rio e a senha");
			
			//redireciona para a p�gina de login
			((HttpServletResponse)response).sendRedirect("logar.jsf");		
		}
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
