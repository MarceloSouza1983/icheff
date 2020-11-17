package br.com.santander.icheffv1.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReceitaDto implements Serializable {

	private static final long serialVersionUID = 5799136004759553183L;
	private Long id;
	private String categoria;
	private String nome;
	private String descricao;
	private String imagem;
	private List<IngredienteDto> listaIngredientes;
	private String linkVideo;
	private double preco;
	
}