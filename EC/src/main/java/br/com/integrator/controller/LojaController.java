package br.com.integrator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.integrator.dao.DaoGenerico;
import br.com.integrator.entities.Categoria;
import br.com.integrator.entities.Produto;

/*
 * Controla a loja e departamentos
 */

@Controller("lojaController")
@Scope("request")
public class LojaController {

	
	@Resource
	private  DaoGenerico<Produto,Integer> produtoDao;
	

	@Resource
	private  DaoGenerico<Categoria,Integer> categoriaDao;
	

	private DataModel model;
	
	public void setProdutoDao(DaoGenerico<Produto,Integer> produtoDao) {
		this.produtoDao = produtoDao;
	}


	public DaoGenerico<Produto,Integer> getProdutoDao() {
		return produtoDao;
	}
	

	public void setCategoriaDao(DaoGenerico<Categoria,Integer> categoriaDao) {
		this.categoriaDao = categoriaDao;
	}


	public DaoGenerico<Categoria,Integer> getCategoriaDao() {
		return categoriaDao;
	}


	// exibe os detalhes do produto selecionado
	public Produto getDetalhes() {
		//captura o id por GET
	 	String id = 
	 		(String) FacesContext.getCurrentInstance().
	 			getExternalContext().
	 			getRequestParameterMap().get("id");
	 	
	 	//evita gerar um erro por não haver o parâmetro
    	if(id==null){
    		id = "0";
    	}
		
		return produtoDao.pesquisarPorId(Integer.parseInt(id));
	}

	//utilizado para pesquisa
	private String pesquisa;
	
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}


	public String getPesquisa() {
		return pesquisa;
	}	

	
	public DataModel getPesqProduto(){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("pesq", "%"+pesquisa+"%");
		String query = "SELECT p FROM Produto p WHERE p.prodNome " +
						"LIKE :pesq OR p.descGd LIKE :pesq";
		
		model = new ListDataModel(produtoDao.listPesqParam(query, params));
		
		return model;
	}	
	
	
	// mostra todas as categorias 
	@SuppressWarnings("unchecked")
	public List<Categoria> getTodasCat() {	
		return	categoriaDao.todos();
			
	}	

	
	// mostra todos os produtos 
	@SuppressWarnings("unchecked")
	public List<Produto> getTodosProdutos() {	
		return	produtoDao.todos();
			
	}
	
	// mostra todos os produtos em um DataTable
	//que estão em promoção
	public List<Produto>  getTodosEmPromocao() {
		
		String query = "SELECT p FROM Produto p " +
						"WHERE p.desconto > 0";
		
		return	produtoDao.listPesq(query);
	}		
	
	 //mostra somente os produtos de uma determinada categoria
	 public DataModel getProdutosCat(){ 
		 
		 	String cat = (String) FacesContext.getCurrentInstance().
		 							getExternalContext().
		 							getRequestParameterMap().get("cat");
		 	 	
		 	
		 	//evita gerar um erro por não haver o parâmetro
	    	if(cat==null){
	    		cat = "0";
	    	}
	    	
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("id", Integer.parseInt(cat));    	
	    	
	    	String query="SELECT p FROM Produto p WHERE p.cat.id=:id";
	    	

			return new  ListDataModel(getProdutoDao().listPesqParam(query, params));

	
	  }
	    
		
}
