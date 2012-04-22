package br.com.integrator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.integrator.dao.CategoriaDao;
import br.com.integrator.entities.Categoria;

/**
 * Hello world!
 *
 */
public class App {
    
	public static void main( String[] args ) {
        //System.out.println( "Hello World!" );
        
        //ApplicationContext springContext = new ClassPathXmlApplicationContext("classpath*:src/config.applicationContext.xml"); 
		ApplicationContext springContext = new ClassPathXmlApplicationContext("classpath:**/applicationContext*.xml"); 
		CategoriaDao categoriaDao =(CategoriaDao)springContext.getBean("categoriaDao");  
        
		Categoria categoria = null;
		categoria = categoriaDao.salvar(new App().getCategoria());
		
		System.out.println(categoria.getCatNome());
		
		
		
        categoriaDao.salvar(new Categoria());
        
    }
	
	private Categoria getCategoria() {
		Categoria categoria = new Categoria();
		categoria.setCatNome("Teste");
		return categoria;
	}
}
