package br.com.integrator.cart;

import br.com.integrator.entities.ItensPedido;

/*
 * O Item do carrinho armazena o produto
 * adicionado ao carrinho
 */


public class ItemCarrinho {

	private ItensPedido item;
	
	
	
	public void setItem(ItensPedido item) {
		this.item = item;
	}

	public ItensPedido getItem() {
		return item;
	}

	
	
	
}
