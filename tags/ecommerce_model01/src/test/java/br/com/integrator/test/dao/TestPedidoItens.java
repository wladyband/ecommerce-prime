package br.com.integrator.test.dao;

import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.integrator.dao.ItensPedidoDao;
import br.com.integrator.dao.PedidoDao;
import br.com.integrator.dao.ProdutoDao;
import br.com.integrator.dao.UsuarioDao;
import br.com.integrator.entities.ItensPedido;
import br.com.integrator.entities.Pedido;
import br.com.integrator.entities.Produto;
import br.com.integrator.entities.Usuario;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestPedidoItens {

    private PedidoDao pedidoDao;
	
    @Resource
    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }    

    private ItensPedidoDao itensPedidoDao;
	
    @Resource
    public void setItensPedidoDao(ItensPedidoDao itensPedidoDao) {
        this.itensPedidoDao = itensPedidoDao;
    }	
	
    private ProdutoDao produtoDao;
	
    @Resource
    public void setProdutoDao(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }  
    
    private UsuarioDao usuarioDao;
	
    @Resource
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }    
    
    
    
	@Test
	public void testSalvar() {
		Produto produto = produtoDao.pesquisarPorId(1);
		Usuario usuario = usuarioDao.pesquisarPorId(1);
		
	
		Pedido pedido = new Pedido();
		
		//simula o usuário logado
		pedido.setUsuario(usuario);
		//data atual
		pedido.setDataPed(new Timestamp((new Date()).getTime()));
		
		
		//salva o pedido para recuperar o id que será armazenado nos itens
		Pedido pedidoConfirmado = pedidoDao.salvar(pedido);
		
	  	
	  	ItensPedido itemP = 
	  		itensPedidoDao.salvar(
	  			new ItensPedido(2,12.5,pedidoConfirmado.getId(),produto.getId())
	  			);
	  	
	  	Produto produto2 = produtoDao.pesquisarPorId(2);
	  	
	  	ItensPedido itemP2 = 
	  		itensPedidoDao.salvar(
	  			new ItensPedido(1,16.5,pedidoConfirmado.getId(),produto2.getId())
	  			);
		
		assertNotNull(itemP);
		
		assertNotNull(itemP2);  	
		  	
		}		
	

}
