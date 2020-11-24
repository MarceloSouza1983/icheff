package br.com.santander.icheffv1.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class VendaDTO implements Serializable {
	
	private static final long serialVersionUID = -7356871819938844721L;

	private Long id;
	private LocalDateTime dataVenda;
	private LocalDateTime dataPagamento;
	private Boolean pagamentoRealizado;
	private UsuarioVendaDTO usuario;
	private Long quantidadeReceitas;
	private Double valorVenda;
	private List<ReceitaResumoDTO> receitas;
	
}