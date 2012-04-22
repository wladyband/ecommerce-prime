package br.com.integrator.test.dao;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import examples.DaoSupport;
import examples.PersistentEntity;

/**
 * Classe de teste para DaoSupport 
 *
 */
public class DaoSupportTest {

	private EntityManager entityManager;
	private DaoSupport<Entity,Long> daoSupport;
	
	/**
	 * Inicializa um daoSupport utilizando como implementação a classe
	 * interna SimpleDaoSupport e configura seu entityManager para um
	 * mock object.
	 */
	@Before
	public void setUp() {
		entityManager = createMock(EntityManager.class);
		daoSupport = new SimpleDaoSupport();
		daoSupport.setEntityManager(entityManager);
	}
	
	/**
	 * Testa se o método findByPrimaryKey chama os métodos esperados
	 * no entityManager.
	 */
	@Test
	public void testFindByPrimaryKey() {
		Long pk = new Long(10);
		expect(entityManager.find(Entity.class, pk)).andReturn(new Entity(pk));
		replay(entityManager);
		daoSupport.findByPrimaryKey(pk);
		verify(entityManager);
	}

	/** 
	 * Testa se o método save faz uma inserção no banco utilizando o
	 * entityManager quando recebe uma nova entidade como parâmetro.
	 * 
	 */
	@Test
	public void testSaveInsert() {
		Entity entity = new Entity(null);
		entityManager.persist(entity);
		replay(entityManager);
		daoSupport.save( entity);
		verify(entityManager);
	}

	/** 
	 * Testa se o método save faz uma atualização no banco utilizando o
	 * entityManager quando recebe uma entidade já existente como parâmetro.
	 * 
	 */
	@Test
	public void testSaveUpdate() {
		Entity entity = new Entity(100L);
		expect(entityManager.merge(entity)).andReturn(entity);
		replay(entityManager);
		daoSupport.save(entity);
		verify(entityManager);
	}

	/**
	 * Testa se o método delete faz as chamadas esperadas no entityManager
	 * quando é utilizada uma entidade no estado JPA detached 
	 */
	@Test
	public void testDeleteDetached() {
		Entity entity = new Entity(100L);
		expect(entityManager.contains(entity)).andReturn(false);
		expect(entityManager.merge(entity)).andReturn(entity);
		entityManager.remove(entity);
		replay(entityManager);
		daoSupport.delete(entity);
		verify(entityManager);
	}

	/**
	 * Testa se o método delete faz as chamadas esperadas no entityManager
	 * quando é utilizada uma entidade no estado JPA managed 
	 */
	@Test
	public void testDeleteManaged() {
		Entity entity = new Entity(100L);
		expect(entityManager.contains(entity)).andReturn(true);
		entityManager.remove(entity);
		replay(entityManager);
		daoSupport.delete(entity);
		verify(entityManager);
	}

	
	/**
	 * Classe interna auxiliar aso testes de DaoSupport. Como a classe
	 * DaoSupport é abstrata necessária uma implementação concreta para
	 * ser utilizada nos métodos de teste.
	 * 
	 * Essa implementação é vazia pois queremos testar os métodos herdados
	 * de DaoSupport. 
	 *
	 */
	private class SimpleDaoSupport extends DaoSupport<Entity,Long> {
	}
	
	
	/**
	 * Classe interna auxiliar aos testes de DaoSupport. Essa classe permite
	 * a criação de instâncias de PersistentEntity utilizadas nos métodos
	 * testados. 
	 * 
	 * Outra opção possivel seria criar um mock object. 
	 *
	 */
	@SuppressWarnings("serial")
	private class Entity implements PersistentEntity<Long> {
		
		private Long id;
		
		Entity(Long id) {
			this.id = id;
		}
		
		public Long getId() {
			return id;
		}

		public boolean isNew() {
			return id == null;
		}
		
	}
	
}
