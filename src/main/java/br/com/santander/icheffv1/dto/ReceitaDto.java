package br.com.santander.icheffv1.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReceitaDTO implements Serializable {

	private static final long serialVersionUID = 5799136004759553183L;

	private Long id;
	private String categoria;
	private String nome;
	private Long porcoes;
	private String descricao;
	private String imagem;
	private List<IngredienteDto> ingredientes;
	private String linkVideo;
	private double preco;
	private Long categoriaId;
	private double custo;
	private Boolean ativa;
	
}