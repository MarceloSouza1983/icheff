package br.com.santander.icheffv1.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.jwt.JwtRequest;
import br.com.santander.icheffv1.jwt.JwtResponse;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.repository.UsuarioRepository;
import br.com.santander.icheffv1.security.JwtUtil;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
	
	private final UsuarioRepository usuarioRepository;
	
	private final JwtUtil jwtUtil;
	
	public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
	}

	public JwtResponse authenticate(JwtRequest request) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			request.getUsuario(),
			request.getSenha()
		));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = this.usuarioRepository.findByLogin(request.getUsuario()).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
		
		String token = jwtUtil.generateToken(usuario);
		
		String location = usuario.isAdmin() ? "/admin.html" : "/categorias.html";
		
		return JwtResponse.builder().token(token).location(location).build();

	}
	
}