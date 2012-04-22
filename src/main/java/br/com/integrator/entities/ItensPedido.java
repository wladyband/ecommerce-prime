package br.com.integrator.entities;


import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="itens_pedido")
public class ItensPedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private IPedidoPK id = new IPedidoPK();
	
	private Integer qtd;

	private Double preco;

	@ManyToOne
	@JoinColumn(name="ped_id", referencedColumnName = "id", insertable=false, updatable=false)
	private Pedido pedido; 
	
	
	@ManyToOne
	@JoinColumn(name="prod_id", referencedColumnName = "id", insertable=false, updatable=false)
	private Produto produto;

	public ItensPedido() {	}
	
	
	public ItensPedido(Integer qtd, Double preco, Integer pedido, Integer produto) {
		this.id = new IPedidoPK(pedido, produto);
		this.qtd = qtd;
		this.preco = preco;
	}

	
	
	@Transient
	private Double subTotal;//usado apenas no carrinho de compras
	

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public Integer getQtd() {
		return this.qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}


	public Double getPreco() {
		return this.preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public IPedidoPK getId() {
		return id;
	}

	public void setId(IPedidoPK id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((qtd == null) ? 0 : qtd.hashCode());
		result = prime * result
				+ ((subTotal == null) ? 0 : subTotal.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItensPedido))
			return false;
		final ItensPedido other = (ItensPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (qtd == null) {
			if (other.qtd != null)
				return false;
		} else if (!qtd.equals(other.qtd))
			return false;
		if (subTotal == null) {
			if (other.subTotal != null)
				return false;
		} else if (!subTotal.equals(other.subTotal))
			return false;
		return true;
	}



}
