package br.com.integrator.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.integrator.cart.Carrinho;
import br.com.integrator.dao.DaoGenerico;
import br.com.integrator.entities.ItensPedido;
import br.com.integrator.entities.Produto;

@Controller("carrinhoController")
@Scope("session")
public class CarrinhoController {

	private Carrinho car;
	
	
	/*
	 * Injetado pelo Spring
	 */
	@Resource
	private  DaoGenerico<Produto,Integer> produtoDao;
	

	
	public CarrinhoController(){
		car = new Carrinho();
	}


	public Carrinho getCar(){
		return car;
	}
	
	
	public void setProdutoDao(DaoGenerico<Produto,Integer> produtoDao) {
		this.produtoDao = produtoDao;
	}


	public DaoGenerico<Produto,Integer> getProdutoDao() {
		return produtoDao;
	}
	
	//exibe os produtos do carrinho retornando um list
	@SuppressWarnings("unchecked")
	public List getCarrinho(){
		return car.listarProdutos();	
	}
	
	//capta a quantidade total de produtos adicionados
	public Integer getQtdProdutosAdicionados() {
		return car.listarProdutos().size();
	}
	
	//atualiza a quantidade somente para o carrinho
	public String atualizarQtd() {
		
		Iterator<?> itensCarrinho = car.listarProdutos().iterator();
		while (itensCarrinho.hasNext()) {
		  	ItensPedido item = (ItensPedido)itensCarrinho.next();
		  	//recalcula o sub-total
		  	item.setSubTotal(
		  			car.subTotal(item.getProduto().getPreco(), 
		  						item.getProduto().getDesconto(), 
		  						item.getQtd())
		  					);
		  	
		  	if (item.getQtd() < 1) {
		  		
		  		car.removerProduto(item.getProduto());
		  		
		  	}
		}

		return "sucesso";		
	}
	
	//remove o produto do carrinho
	public String removerProduto() {
		//captura o id por GET
		String id = (String) FacesContext.getCurrentInstance().
					getExternalContext().getRequestParameterMap().
					get("id");
		
		Produto p = produtoDao.pesquisarPorId(Integer.parseInt(id));
		car.removerProduto(p);
		
		return "sucesso";
	}
	
	

	//adiciona produtos ao carrinho
	public String adicionarAoCarrinho(){

		//captura o id do produto selecionado pelo cliente
		String id = (String) FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().
				get("id");
	
		Produto p = produtoDao.pesquisarPorId(Integer.parseInt(id));
		car.adicionarAoCarrinho(p);


		return "sucesso";
	}	
	
	//limpa o carrinho de compras
	public void removerTodosProdutos(){
		car.limpar();
	}

	
}
