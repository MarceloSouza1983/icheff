package br.com.santander.icheffv1.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.santander.icheffv1.jwt.JwtRequest;
import br.com.santander.icheffv1.jwt.JwtResponse;
import br.com.santander.icheffv1.service.AuthService;

@RestController
@RequestMapping("/api/login")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping
	public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid JwtRequest request){
		
		JwtResponse response = authService.authenticate(request);
		
		return ResponseEntity.ok(response);
		
	}
	
}
