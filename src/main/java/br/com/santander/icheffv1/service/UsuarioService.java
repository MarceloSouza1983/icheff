package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario create(Usuario usuario) {
		usuario.setId(null);
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuarioNovo) {

		Usuario usuarioAntigo = this.findById(usuarioNovo.getId());

		usuarioAntigo.setNome(usuarioNovo.getNome());
		
		return this.usuarioRepository.save(usuarioAntigo);

	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.usuarioRepository.deleteById(id);
	}
	
	public List<Usuario> findAll(){
		return this.usuarioRepository.findAll();
	}
	
	public Usuario findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.usuarioRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar um usuário com id " + id));
	}
	
}