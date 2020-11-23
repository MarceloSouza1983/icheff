package br.com.santander.icheffv1.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReceitaResumoDTO implements Serializable {

	private static final long serialVersionUID = 2581228662942425065L;
	
	private String nome;
	private Long quantidade;
	
}