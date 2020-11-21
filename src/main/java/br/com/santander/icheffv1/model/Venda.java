package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {
	
	private static final long serialVersionUID = 9102081472587642363L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ven_id")
	private Long id;
	
	@Column(columnDefinition = "TINYINT(1)", name= "ven_pagamento_realizado", length = 10)
	private Boolean pagamentoRealizado;
	
	@Column(name = "ven_data_venda", length = 100, nullable = false)
	@NotNull
	private LocalDateTime dataVenda;
	
	@Column(name = "ven_data_pagamento", length = 100)
	private LocalDateTime dataPagamento;
	
	@ManyToOne
	@JoinColumn(name = "ven_usuario_id")
	private Usuario usuario;
	
	public Venda() {
		
	}
	
	public Venda(
		Long id,
		Boolean pagamentoRealizado,
		@NotNull LocalDateTime dataVenda,
		LocalDateTime dataPagamento,
		@NotNull Usuario usuario
	) {
		this.id = id;
		this.pagamentoRealizado = pagamentoRealizado;
		this.dataVenda = dataVenda;
		this.dataPagamento = dataPagamento;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getPagamentoRealizado() {
		return pagamentoRealizado;
	}

	public void setPagamentoRealizado(Boolean pagamentoRealizado) {
		this.pagamentoRealizado = pagamentoRealizado;
	}

	public LocalDateTime getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDateTime dataVenda) {
		this.dataVenda = dataVenda;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}