package br.com.santander.icheffv1.jwt;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JwtResponse {

	private String token;
	
	private String location;
	
}
