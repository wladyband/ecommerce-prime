package br.com.integrator.entities;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="produtos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)    
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Integer id;

	@Column(name="desc_gd")
	@Lob
	private String descGd;

	@Column(name="prod_nome")
	private String prodNome;

	private Double preco;
	
	private Double desconto;

	@Column(name="desc_peq")
	private String descPeq;

	private String imagem;

	@ManyToOne(fetch=LAZY)
	@JoinColumn(name="cat_id", referencedColumnName = "id")
	private Categoria cat;

	@OneToMany(mappedBy="produto", fetch = LAZY, cascade = ALL)
	private Set<ItensPedido> itens;
	

	public Produto() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescGd() {
		return this.descGd;
	}

	public void setDescGd(String descGd) {
		this.descGd = descGd;
	}

	public String getProdNome() {
		return this.prodNome;
	}

	public void setProdNome(String prodNome) {
		this.prodNome = prodNome;
	}

	public Double getPreco() {
		return this.preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getDesconto() {
		return desconto;
	}	
	
	public String getDescPeq() {
		return this.descPeq;
	}

	public void setDescPeq(String descPeq) {
		this.descPeq = descPeq;
	}

	public String getImagem() {
		return this.imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Categoria getCat() {
		return this.cat;
	}

	public void setCat(Categoria cat) {
		this.cat = cat;
	}

	public Set<ItensPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItensPedido> itens) {
		this.itens = itens;
	}





}
