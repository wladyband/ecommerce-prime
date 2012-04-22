package br.com.integrator.entities;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	private Integer id;

	@Column(name="cc_m_exp")
	private short ccMExp;

	@Column(name="cc_numero")
	private String ccNumero;

	@Column(name="cc_nome")
	private String ccNome;

	@Column(name="cc_tipo")
	private int ccTipo;

	@Column(name="data_ped")
	private Timestamp dataPed;

	@Column(name="cc_a_exp")
	private int ccAExp;

	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy="pedido", fetch = LAZY)
	private List<ItensPedido> itens =  new ArrayList<ItensPedido>();

	public Pedido() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public short getCcMExp() {
		return this.ccMExp;
	}

	public void setCcMExp(short ccMExp) {
		this.ccMExp = ccMExp;
	}

	public String getCcNumero() {
		return this.ccNumero;
	}

	public void setCcNumero(String ccNumero) {
		this.ccNumero = ccNumero;
	}

	public String getCcNome() {
		return this.ccNome;
	}

	public void setCcNome(String ccNome) {
		this.ccNome = ccNome;
	}

	public int getCcTipo() {
		return this.ccTipo;
	}

	public void setCcTipo(int ccTipo) {
		this.ccTipo = ccTipo;
	}

	public Timestamp getDataPed() {
		return this.dataPed;
	}

	public void setDataPed(Timestamp dataPed) {
		this.dataPed = dataPed;
	}

	public int getCcAExp() {
		return this.ccAExp;
	}

	public void setCcAExp(int ccAExp) {
		this.ccAExp = ccAExp;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItensPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItensPedido> itens) {
		this.itens = itens;
	}



}
