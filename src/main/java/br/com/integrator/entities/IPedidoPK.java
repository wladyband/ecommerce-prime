package br.com.integrator.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IPedidoPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ped_id")  
	private Integer pedId;
	
	@Column(name = "prod_id")  
	private Integer prodId;


	public IPedidoPK() {
	}

	
	public IPedidoPK(Integer pedId, Integer prodId) {
		this.pedId = pedId;
		this.prodId = prodId;
	}



	public Integer getPedId() {
		return pedId;
	}

	public void setPedId(Integer pedId) {
		this.pedId = pedId;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedId == null) ? 0 : pedId.hashCode());
		result = prime * result + ((prodId == null) ? 0 : prodId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IPedidoPK))
			return false;
		final IPedidoPK other = (IPedidoPK) obj;
		if (pedId == null) {
			if (other.pedId != null)
				return false;
		} else if (!pedId.equals(other.pedId))
			return false;
		if (prodId == null) {
			if (other.prodId != null)
				return false;
		} else if (!prodId.equals(other.prodId))
			return false;
		return true;
	}


}
