package br.com.santander.icheffv1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class IngredienteDto implements Serializable {

	private static final long serialVersionUID = -4607783023205249223L;

	private Long id;
	private Long ingredienteId;
	private String nome;
	private double quantidade;
	private Long unidadeId;
	private String unidade;
	private String unidadeSingular;
	private String unidadePlural;
	
}