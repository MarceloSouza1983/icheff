package br.com.santander.icheffv1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReceitaCategoriaDTO implements Serializable {

	private static final long serialVersionUID = -4214848340860921308L;

	private Long id;
	private String nome;
	private Boolean vegana;
	private Long quantidadeReceitas;
	
}
