package br.com.santander.icheffv1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class IngredienteDto implements Serializable {

	private String nome;
	private double quantidade;
	private String unidadeSingular;
	private String unidadePlural;
}
