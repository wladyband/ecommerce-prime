package br.com.integrator.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;

import br.com.integrator.cart.Carrinho;
import br.com.integrator.dao.DaoGenerico;
import br.com.integrator.entities.ItensPedido;
import br.com.integrator.entities.Pedido;
import br.com.integrator.entities.Usuario;
import br.com.integrator.util.FacesUtils;

@Controller("usuarioController")
@Scope("session")
public class UsuarioController {

	@Resource
	private DaoGenerico<Usuario, Integer> usuarioDao;

	@Resource
	private DaoGenerico<Pedido, Integer> pedidoDao;

	@Resource
	private DaoGenerico<ItensPedido, Integer> itensPedidoDao;

	// adicionado para enviar e-mail
	@Resource
	private JavaMailSender enviarEmail;

	private Pedido pedido;

	private ItensPedido itens;

	private Usuario usuario;
	
	private boolean existeDados;

	public boolean isExisteDados() {
		return existeDados;
	}

	public void setExisteDados(boolean existeDados) {
		this.existeDados = existeDados;
	}

	public UsuarioController() {
		this.pedido = new Pedido();
		this.usuario = new Usuario();

	}

	// captura a sess�o do contexto criado
	// pelo JavaServer Faces
	FacesContext fc = FacesContext.getCurrentInstance();

	HttpSession session = (HttpSession) fc.getExternalContext().getSession(
			false);

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DaoGenerico<Usuario, Integer> getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(DaoGenerico<Usuario, Integer> usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public DaoGenerico<Pedido, Integer> getPedidoDao() {
		return pedidoDao;
	}

	public void setPedidoDao(DaoGenerico<Pedido, Integer> pedidoDao) {
		this.pedidoDao = pedidoDao;
	}

	public ItensPedido getItens() {
		return itens;
	}

	public void setItens(ItensPedido itens) {
		this.itens = itens;
	}

	public DaoGenerico<ItensPedido, Integer> getItensPedidoDao() {
		return itensPedidoDao;
	}

	public void setItensPedidoDao(
			DaoGenerico<ItensPedido, Integer> itensPedidoDao) {
		this.itensPedidoDao = itensPedidoDao;
	}

	// verifica a exist�ncia do usu�rio e o retorna
	private Usuario verificaU(String email) {
		String query = "SELECT u FROM Usuario u WHERE u.email=:email";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		return usuarioDao.pesqParam(query, params);

	}

	// atributos de pesquisa
	private Date data1;
	private Date data2;

	public Date getData1() {
		return data1;
	}

	public void setData1(Date data1) {
		this.data1 = data1;
	}

	public Date getData2() {
		return data2;
	}

	public void setData2(Date data2) {
		this.data2 = data2;
	}

	// atributos de pagina��o de resultados
	private int maxPorPagina = 3;

	private int paginaAtual = 0;

	// converte adicionando hora, minuto e segundo
	private long dataConvertida() {
		// adiciona a hora que n�o foi colocada no
		// intervalo de pesquisa
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.setTime(data2);
		gc1.add(GregorianCalendar.HOUR_OF_DAY, 23);
		gc1.add(GregorianCalendar.MINUTE, 59);
		gc1.add(GregorianCalendar.SECOND, 59);

		return gc1.getTimeInMillis();
	}

	// m�todo para a consulta a ser utilizada
	private String consultaComprados() {
		String query = "SELECT p.id, u.nome, p.dataPed, "
				+ "SUM(i.qtd), SUM(i.preco)"
				+ " FROM Pedido p JOIN p.usuario u JOIN p.itens i"
				+ " WHERE p.dataPed BETWEEN :data1 AND :data2"
				+ " GROUP By p.id, u.nome, p.dataPed";
		return query;
	}

	// mostra todos os produtos em um DataTable
	// comprados com os respectivos clientes
	// limitados pela pagina��o
	@SuppressWarnings("unchecked")
	public List getComprados() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data1", new Timestamp(data1.getTime()));
		params.put("data2", new Timestamp(dataConvertida()));

		return usuarioDao.listPesqParam(consultaComprados(), params,
				maxPorPagina, paginaAtual);

	}

	// navega para a primeira p�gina
	public String primeiraPagina() {
		paginaAtual = 0;
		return null;
	}

	// navega para a �ltima p�gina
	public String ultimaPagina() {
		int rest = getTotal() % maxPorPagina;

		if (rest != 0)
			paginaAtual = getTotal() - rest;
		else
			paginaAtual = getTotal() - maxPorPagina;

		return null;
	}

	// acessor da p�gina atual
	public int getPaginaAtual() {
		return paginaAtual;
	}

	private int total;
	// total de resultados encontrados na consulta
	public int getTotal() {
		return this.total;

	}

	private List<?> listaResultsPesq = null;
	// separa a pesquisa do total encontrado
	// possibilitando a utiliza��o tamb�m no relat�rio
	public String todosResultsPesq() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data1", new Timestamp(data1.getTime()));
		params.put("data2", new Timestamp(dataConvertida()));
		
		this.setListaResultsPesq(usuarioDao.listPesqParam(consultaComprados(), params)); 
		this.total = this.getListaResultsPesq().size();
		
		if(total == 0) {
			setExisteDados(false);
		} else {
			setExisteDados(true);
			
		}
	
		return "";

	}

	public void setListaResultsPesq(List<?> listaResultsPesq) {
		this.listaResultsPesq = listaResultsPesq;
	}

	public List<?> getListaResultsPesq() {
		return listaResultsPesq;
	}

	public int getProximaPagina() {
		int total = getTotal();
		int soma = paginaAtual + maxPorPagina;
		int proxima = (soma > total) ? total : soma;
		return proxima;
	}

	public int getMaxPorPagina() {
		return maxPorPagina;
	}

	// navega para a pr�xima p�gina
	public String proxima() {
		int soma = paginaAtual + maxPorPagina;
		if (soma < getTotal())
			paginaAtual += maxPorPagina;

		return null;
	}

	// navega para a p�gina anterior
	public String anterior() {
		paginaAtual -= maxPorPagina;

		if (paginaAtual < 0)
			paginaAtual = 0;

		return null;
	}

	// converte uma string para Timestamp
	private Timestamp convertTimestamp(String t) throws ParseException {
		// trata nanoseconds "yyyy-mm-dd hh:mm:ss.nanoseconds"
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		return new Timestamp(sdfIn.parse(t).getTime());
	}

	@SuppressWarnings("unchecked")
	// executa o relat�rio atrav�s do actionListener
	public String executarRelatorio() throws ParseException {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();

		// pega o caminho do arquivo .jasper da aplica��o
		InputStream reportStream = context.getExternalContext()
				.getResourceAsStream("/relatorios/relatorio.jasper");

		// envia a resposta com o MIME Type PDF
		response.setContentType("application/pdf");
		
		 // for�a a abertura de download
		  response.setHeader("Content-disposition",
		  "attachment;filename=relatorio.pdf");
		 

		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();

			// envia o t�tulo para o relat�rio, usando o par�metro criado
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("TITULO", "Relat�rio de Compras Efetuadas no Site");

			List dados = new ArrayList();

			Map record = null;

			// varre a consulta e separa os objetos
			for (Iterator iterator = this.listaResultsPesq.iterator(); iterator
					.hasNext();) {

				Object[] o = (Object[]) iterator.next();
				record = new HashMap();
				// coloca em um Map cada um dos campos criados
				// manualmente pelo relat�rio
				record.put("pedido", Integer.parseInt(o[0].toString()));
				record.put("nome", o[1].toString());
				record.put("data", this.convertTimestamp(o[2].toString()));
				record.put("qtd", Integer.parseInt(o[3].toString()));
				record.put("total", Double.parseDouble(o[4].toString()));

				// adiciona o List dados
				dados.add(record);

			}

			// cria uma fonte de dados para cole��es
			JRBeanCollectionDataSource fonteDados = new JRBeanCollectionDataSource(
					dados);

			// envia para o navegador o PDF gerado
			JasperRunManager.runReportToPdfStream(reportStream,
					servletOutputStream, parametros, fonteDados);

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// evita erro do JSF ap�s completar
			// a gera��o do relat�rio
			// avisando o FacesContext que a resposta est� completa
			context.responseComplete();

		}
		return null;
	}

	// valida a entrada de e-mails no cadastro e �rea de login
	public void validaEmail(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		String digitado = (String) objeto;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(digitado);
		boolean matchFound = m.matches();

		if (!matchFound) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage("Formato inv�lido.");
			context.addMessage(componente.getClientId(context), message);
		}
	}

	// executa o login no sistema
	// direcionando o usu�rio ao local indicado
	public String login() {

		Usuario u = verificaU(usuario.getEmail());
		// verifica se a senha resultante �
		// igual a senha encontrada no banco de dados
		if (u != null && u.getSenha().equals(usuario.getSenha())) {

			// cria uma sess�o contendo o e-mail do usuario
			session.setAttribute("user", usuario.getEmail());

			// deixa o objeto usuario agora com os valores encontrados no banco
			usuario = u;

			// caso a sess�o msg esteja com valor, a remove
			if (session.getAttribute("msg") != null) {
				session.removeAttribute("msg");
			}

			// redireciona para a �rea do usu�rio
			return "logado";

		} else {
			FacesUtils.mensErro("Usu�rio ou senha inv�lidos");
		}

		return null;

	}

	// vai para o cadastro de novo usu�rio
	public String novoUsuario() {
		this.usuario = new Usuario();

		return "novo";
	}

	// caso o usu�rio seja salvo com sucesso
	public String salvar() {
		// se n�o h� usu�rio logado
		if (usuario.getId() == null) {
			// verifica se o cadastro n�o possui
			// um usu�rio com o mesmo e-mail
			Usuario u = verificaU(usuario.getEmail());

			if (u != null && u.getEmail().equals(usuario.getEmail())) {
				// lan�a um erro global informando da exist�ncia do usu�rio
				FacesUtils.mensErro("Usu�rio existente");
				return null; // n�o redireciona a lugar algum

			} else { // caso o usu�rio n�o seja cadastrado

				// salva no banco de dados
				usuario = usuarioDao.salvar(usuario);
				// cria uma sess�o contendo o e-mail do usuario
				session.setAttribute("user", usuario.getEmail());

			}

		} else { // caso esteja logado

			// pega os dados encontrados no formul�rio
			// e os salva
			usuarioDao.atualizar(usuario);

		}

		return "logado";
	}

	// vai para a �rea de usu�rio
	public String logar() {
		if (session.getAttribute("user") != null)
			return "logado"; // se estiver logado, vai direto a �rea
		else
			return "logar"; // mostra o formul�rio de login
	}

	// sai da �rea do usu�rio
	public String logout() {
		session.removeAttribute("user");
		return "logar";

	}

	// m�todo respons�vel por finalizar a compra
	public String finalizaCompra() {

		// recupera os dados que est�o em sess�o em carrinhoController
		FacesContext fCtx = FacesContext.getCurrentInstance();
		ELContext elCtx = fCtx.getELContext();
		ExpressionFactory ef = fCtx.getApplication().getExpressionFactory();
		ValueExpression ve = ef.createValueExpression(elCtx,
				"#{carrinhoController}", CarrinhoController.class);

		CarrinhoController carc = (CarrinhoController) ve.getValue(elCtx);

		// coloca na vari�vel Carrinho
		Carrinho carrinho = carc.getCar();

		// verifica se h� algum produto no carrinho
		// se n�o houver, pode ser que o pedido tenha sido efetivado
		// e o usu�rio fez reload no navegador
		if (carrinho.getTotal() > 0) {
			// pega o usu�rio logado
			pedido.setUsuario(usuario);
			// data atual
			pedido.setDataPed(new Timestamp((new Date()).getTime()));

			// salva o pedido e recura
			// o id que ser� armazenado nos itens
			Pedido pedidoConfirmado = pedidoDao.salvar(pedido);

			// varre o carrinho para guardar os produtos no banco de dados
			Iterator<?> itensCarrinho = carrinho.listarProdutos().iterator();
			while (itensCarrinho.hasNext()) {

				ItensPedido item = (ItensPedido) itensCarrinho.next();

				// salva os produtos comprados neste pedido
				itensPedidoDao.salvar(new ItensPedido(item.getQtd(), item
						.getSubTotal(), pedidoConfirmado.getId(), item
						.getProduto().getId()));

			}

			// envia o e-mail do pedido efetuado
			MimeMessagePreparator preparator = new MimeMessagePreparator() {

				public void prepare(MimeMessage mimeMessage) throws Exception {

					MimeMessageHelper MMhelper = new MimeMessageHelper(
							mimeMessage, true, "ISO-8859-1");
					MMhelper.setTo(usuario.getEmail());
					//MMhelper.setFrom("exemplo@integrator.com.br");
					MMhelper.setFrom("email_tgi@javacia.com.br");
					MMhelper.setSubject("Pedido N�mero: " + pedido.getId());
					MMhelper.setText(
							"<html><body>"
									+ "Caro Sr. "
									+ usuario.getNome()
									+ ", <br> "
									+ "Seu pedido de n�mero <b>"
									+ pedido.getId()
									+ "</b>"
									+ " foi efetuado com sucesso. <br>"
									+ "Obrigado por comprar na <b>Loja OnLineShop</b>"
									+ "</body></html>", true);

				}
			};
			try {
				this.enviarEmail.send(preparator);
			} catch (MailException ex) {
				System.err.println(ex.getMessage());
			}

			FacesUtils.mensInfo("Obrigado por comprar");
		}
		// limpa o carrinho
		carrinho.limpar();

		// n�o vai a lugar algum
		return null;
	}

}
