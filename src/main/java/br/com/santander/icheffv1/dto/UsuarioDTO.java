package br.com.santander.icheffv1.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.santander.icheffv1.model.Tipo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 447629492527514331L;
	
	private Long Id;
	private String nome;
	private Tipo tipo;
	private String login;
	private LocalDateTime dataCadastro;
	private Long quantidadeCompras;
	
}