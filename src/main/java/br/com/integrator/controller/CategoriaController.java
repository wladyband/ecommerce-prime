package br.com.integrator.controller;

import javax.annotation.Resource;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.integrator.dao.DaoGenerico;
import br.com.integrator.entities.Categoria;
import br.com.integrator.util.FacesUtils;

@Controller("categoriaController")
@Scope("session")
public class CategoriaController {

	private Categoria categoria;
	private DataModel model;
		
	/*
	 * Recurso injetado pelo Spring
	 * 
	 */
	@Resource
	private  DaoGenerico<Categoria,Integer> categoriaDao;
	
	public CategoriaController(){}
	
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}	
	
	public void setCategoriaDao(DaoGenerico<Categoria,Integer> categoriaDao) {
		this.categoriaDao = categoriaDao;
	}


	public DaoGenerico<Categoria,Integer> getCategoriaDao() {
		return categoriaDao;
	}

	//cadastra uma nova categoria
	public String novaCat() {
		this.categoria = new Categoria();
		
		return "formCategoria.xhtml";
	}
	
	
	// mostra todas as categorias em um DataTable
	public DataModel getTodos() {
		return model = new ListDataModel(categoriaDao.todos());		
	}
	
	//pega a categoria selecionada na tabela
	//para editar ou excluir
	public Categoria getCategoriaParaEditarExcluir() {
		Categoria categoria = (Categoria) model.getRowData();
		return categoria;
	}

	//edita a categoria
	public String editar() {
		setCategoria(getCategoriaParaEditarExcluir());
		
		return "formCategoria";
	}	
	
	//salva uma nova categoria 
	//ou que está em edição
	public String salvar(){
		//verifica se não é uma categoria em edição
		if (categoria.getId() == null) {
			categoriaDao.salvar(categoria);
			FacesUtils.mensInfo("Cadastrado com sucesso");
		} else {
			categoriaDao.atualizar(categoria);	
			FacesUtils.mensInfo("Atualizado com sucesso");
		}
		return "sucesso";
	}
	
	//exclui a categoria selecionada
	//no DataTable
	public String excluir(){	
		Categoria categoria = getCategoriaParaEditarExcluir();
		categoriaDao.excluir(categoria);
		FacesUtils.mensInfo("Excluído com sucesso");
		return "mostrarCategorias";
	}	
	
}