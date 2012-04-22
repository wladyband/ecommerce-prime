package examples;

/**
 * 
 * Classe de exceção padrão utilizada para todos os serviços de persistência.
 * 
 * Podem ser criadas subclasses mais específicas em cada componente de persistência
 * se for desejado.
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = -1432728222378235769L;

	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, Throwable t) {
		super(message,t);
	}
	
	public DAOException(Throwable t) {
		super(t);
	}
}
