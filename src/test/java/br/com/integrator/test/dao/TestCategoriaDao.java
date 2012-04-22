package br.com.integrator.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.integrator.dao.CategoriaDao;
import br.com.integrator.entities.Categoria;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestCategoriaDao {

    private CategoriaDao categoriaDao;
    private Integer id = 1;
	
    @Autowired
    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }    


	@Test
	public void testSalvar() {
		Categoria categoria = null;
		categoria = categoriaDao.salvar(getCategoria());
		assertNotNull(categoria);
		assertEquals(id, categoria.getId());
		assertEquals("Teste", categoria.getCatNome());
		
	}
	
	@Test
	public void testAtualizar() {
        Categoria categoria = categoriaDao.pesquisarPorId(id);
        categoria.setCatNome("Teste 2");

        categoria = categoriaDao.atualizar(categoria);
        assertNotNull(categoria);
        assertEquals("Teste 2", categoria.getCatNome());

	}

	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setCatNome("Teste");
		return categoria;
	}


	@Test
	public void testTodos() {
		List<Categoria> categorias = categoriaDao.todos();
		assertNotNull(categorias);
		assertEquals(1, categorias.size());	
		assertEquals("Teste 2", categorias.get(0).getCatNome());
		
	}
	
	@Test
	public void testListPesqParam() {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);  
		List<Categoria> categorias = 
			categoriaDao.
			listPesqParam("SELECT p FROM Categoria p WHERE p.id=:id", params);
		assertNotNull(categorias);
		assertEquals(1, categorias.size());	
		assertEquals("Teste 2", categorias.get(0).getCatNome());
		
	}
	
	@Test
	public void testPesqParam() {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);  
		Categoria categoria = 
			categoriaDao.
			pesqParam("SELECT p FROM Categoria p WHERE p.id=:id", params);
		assertNotNull(categoria);
		assertEquals("Teste 2", categoria.getCatNome());
		
	}	

	@Test
	public void testExcluir() {
		Categoria categoria = categoriaDao.pesquisarPorId(id);
		categoriaDao.excluir(categoria);
		assertNull(categoriaDao.pesquisarPorId(id));
	}	
	
	
}
