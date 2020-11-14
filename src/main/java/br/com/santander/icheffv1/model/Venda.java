package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {
	
	private static final long serialVersionUID = 9102081472587642363L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "ven_id")
	private Long id;
	
	@Column(columnDefinition = "TINYINT(1)", name= "ven_pagamento_realizado", length = 10)
	private int pagamentoRealizado;
	
	@Column(name= "ven_data_venda", length = 100, nullable = false)
	@NotNull
	private LocalDateTime dataVenda;
	
	@Column(name= "ven_data_pagamento", length = 100, nullable = false)
	@NotNull
	private LocalDateTime dataPagamento;
	
	@ManyToOne
	@JoinColumn(name = "endereco_venda")
	private UsuarioEndereco usuarioEndereco;
	
	public Venda() { }
	
	public Venda(Long id, int pagamentoRealizado, @NotNull LocalDateTime dataVenda,
			@NotNull LocalDateTime dataPagamento) {
		super();
		this.id = id;
		this.pagamentoRealizado = pagamentoRealizado;
		this.dataVenda = dataVenda;
		this.dataPagamento = dataPagamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPagamentoRealizado() {
		return pagamentoRealizado;
	}

	public void setPagamentoRealizado(int pagamentoRealizado) {
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