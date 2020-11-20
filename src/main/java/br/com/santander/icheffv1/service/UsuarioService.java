package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.UsuarioDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Tipo;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.repository.UsuarioRepository;
import br.com.santander.icheffv1.repository.VendaRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final VendaRepository vendaRepository;

	public UsuarioService(UsuarioRepository usuarioRepository, VendaRepository vendaRepository) {
		this.usuarioRepository = usuarioRepository;
		this.vendaRepository = vendaRepository;
	}
	
	public Usuario create(Usuario usuario) {
		usuario.setId(null);
		usuario.setTipo(Tipo.CLIENTE);
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuarioNovo) {

		Usuario usuarioAntigo = this.findById(usuarioNovo.getId());

		usuarioAntigo.setNome(usuarioNovo.getNome());
		usuarioAntigo.setSenha(usuarioNovo.getSenha());
		usuarioAntigo.setLogin(usuarioNovo.getLogin());
		
		return this.usuarioRepository.save(usuarioAntigo);

	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.usuarioRepository.deleteById(id);
	}
	
	public List<UsuarioDTO> findAll(){
		
		List<UsuarioDTO> usuarios = this.usuarioRepository.findAll().stream()
				.map(usuario -> {
					UsuarioDTO usuarioDTO = new UsuarioDTO();
					
					usuarioDTO.setId(usuario.getId());
					usuarioDTO.setNome(usuario.getNome());
					usuarioDTO.setTipo(usuario.getTipo());
					usuarioDTO.setLogin(usuario.getLogin());
					usuarioDTO.setDataCadastro(usuario.getDataCadastro());
					usuarioDTO.setNome(usuario.getNome());
					
					Long qtdComprasUsuario = this.vendaRepository.countByUsuario(usuario).orElse(0L);
					
					usuarioDTO.setQuantidadeCompras(qtdComprasUsuario);
					
					return usuarioDTO;
				})
				.collect(Collectors.toList());
		
		return usuarios;
	}
	
	public Usuario findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.usuarioRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar um usuário com id " + id));
	}
	
	public Long count() {
		return this.usuarioRepository.count();
	}
	
}