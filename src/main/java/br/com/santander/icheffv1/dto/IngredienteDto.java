package br.com.santander.icheffv1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class IngredienteDto implements Serializable {

	private static final long serialVersionUID = -4607783023205249223L;

	private String nome;
	private double quantidade;
	private String unidadeSingular;
	private String unidadePlural;
	
}