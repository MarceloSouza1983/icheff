package br.com.santander.icheffv1.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.santander.icheffv1.model.Tipo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UsuarioVendaDTO implements Serializable {

	private static final long serialVersionUID = 447629492527514331L;
	
	private Long Id;
	private String nome;
	
	//Endereco
	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String cidade;
	private String estado;
	private String telefone;
	private String celular;
	
}