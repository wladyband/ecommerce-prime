package br.com.integrator.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.integrator.dao.CategoriaDao;
import br.com.integrator.dao.ProdutoDao;
import br.com.integrator.entities.Categoria;
import br.com.integrator.entities.Produto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestProdutoDao {

    private ProdutoDao produtoDao;
    private Integer id = 1;
	
    @Resource
    public void setProdutoDao(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }    

    private CategoriaDao categoriaDao;
	
    @Resource
    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }
    
    
   
	@Test
	public void testSalvar() {
		Produto produto = null;
		produto = produtoDao.salvar(getProduto());
		assertNotNull(produto);
		assertEquals(id, produto.getId());
		assertEquals("Teste", produto.getProdNome());
		
	}
	
	@Test
	public void testAtualizar() {
        Categoria cat = categoriaDao.pesquisarPorId(id);
        Produto produto = produtoDao.pesquisarPorId(id);
        produto.setCat(cat);
        produto.setProdNome("Teste 2");
        
        produto = produtoDao.atualizar(produto);
        assertNotNull(produto);
        assertEquals("Teste 2", produto.getProdNome());

	}

	private Produto getProduto() {
		Produto produto = new Produto();
		Categoria categoria = categoriaDao.salvar(getCategoria());
		
		produto.setProdNome("Teste");
		produto.setPreco(14.5);
		produto.setDescGd("");
		
		produto.setCat(categoria);
		
		return produto;
	}

	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setCatNome("Teste");
		return categoria;
	}
	

	@Test
	public void testTodos() {
		List<Produto> produtos = produtoDao.todos();
		assertNotNull(produtos);
		assertEquals(1, produtos.size());	
		assertEquals("Teste 2", produtos.get(0).getProdNome());
		
	}
	
	@Test
	public void testListPesqParam() {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("id", id);  
		List<Produto> produtos = produtoDao.listPesqParam("SELECT p FROM Produto p WHERE p.id=:id", params);
		assertNotNull(produtos);
		assertEquals(1, produtos.size());	
		assertEquals("Teste 2", produtos.get(0).getProdNome());
		
	}
	
	

	@Test
	public void testExcluir() {
		Produto produto = produtoDao.pesquisarPorId(id);
		produtoDao.excluir(produto);
		assertNull(produtoDao.pesquisarPorId(id));
	}	
	
}
