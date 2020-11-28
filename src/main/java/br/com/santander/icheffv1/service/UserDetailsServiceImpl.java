package br.com.santander.icheffv1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.repository.UsuarioRepository;
import br.com.santander.icheffv1.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private UsuarioRepository usuarioRepository;

	@Autowired
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Usuario usuario = this.usuarioRepository.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
		
		return  new UserDetailsImpl(usuario);
		
	}
	
}