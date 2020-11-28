package br.com.santander.icheffv1.jwt;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtRequest {

	@NotBlank
	private String usuario;
	
	@NotBlank
	private String senha;
	
}