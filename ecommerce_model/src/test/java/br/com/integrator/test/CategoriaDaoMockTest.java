package br.com.integrator.test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import br.com.integrator.dao.CategoriaDao;
import br.com.integrator.dao.imp.CategoriaDaoImp;
import br.com.integrator.entities.Categoria;


public class CategoriaDaoMockTest {

    private CategoriaDao categoriaDao;
    private EntityManager entityManager;
    private Query query;
    private Categoria categoria;
    private Integer id; 
	
    @Before
	public void setUp() {
    	this.id = 1;
    	categoria = getCategoria();
    	query = createMock(Query.class);
    	entityManager = createMock(EntityManager.class);
    	categoriaDao = new CategoriaDaoImp();
    	((CategoriaDaoImp)categoriaDao).setEntityManager(entityManager);
    	
    	
	}

	@Test
	public void testSalvar() {
		entityManager.clear();
		entityManager.persist(categoria);
		replay(entityManager);
		
		categoriaDao.salvar(categoria);
		verify(entityManager);
	}
	
	@Test
	public void testAtualizar() {
		expect(entityManager.merge(categoria)).andReturn(categoria);
		replay(entityManager);
		
		categoriaDao.atualizar(categoria);
		verify(entityManager);
	}
	
	@Test
	public void testTodos() {
		expect(entityManager.createQuery("SELECT obj FROM Categoria obj")).andReturn(query);
		expect(query.getResultList()).andReturn(getList());
		replay(query, entityManager);

		categoriaDao.todos();
		verify(query);
		
	}
	
	@Test
	public void testListPesqParam() {
		expect(entityManager.createQuery("SELECT p FROM Categoria p WHERE p.id=:id")).andReturn(query);
		expect(query.getResultList()).andReturn(getList());
		replay(query, entityManager);

		categoriaDao.listPesqParam("SELECT p FROM Categoria p WHERE p.id=:id", getParams(id));
		verify(query);
	}
	
	@Test
	public void testPesqParam() {
		expect(entityManager.createQuery("SELECT p FROM Categoria p WHERE p.id=:id")).andReturn(query);
		expect(query.getResultList()).andReturn(getList());
		replay(query, entityManager);

		categoriaDao.pesqParam("SELECT p FROM Categoria p WHERE p.id=:id", getParams(id));
		verify(query);
	}	

	
	@Test
	public void testExcluir() {
		expect(entityManager.merge(categoria)).andReturn(categoria);
		entityManager.remove(categoria);
		replay(entityManager);
		
		categoriaDao.excluir(categoria);
		verify(entityManager);
		
	}	
	
	
	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setId(this.id);
		categoria.setCatNome("Teste"+this.id);
		return categoria;
	}

	private List<Categoria> getList() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		Categoria cat = null;
		
		for (int i = 1; i <= 10; i++) {
			cat = new Categoria();
			cat.setId(i);
			cat.setCatNome("Categoria"+i);
			categorias.add(cat);
		}
		
		return categorias;
	}
	
	private Map<String, Object> getParams(Integer paramId){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", paramId);
		
		return params;
	}
	
}
