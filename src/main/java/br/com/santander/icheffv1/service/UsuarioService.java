package br.com.santander.icheffv1.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.UsuarioDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Role;
import br.com.santander.icheffv1.model.Tipo;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.repository.RoleRepository;
import br.com.santander.icheffv1.repository.UsuarioRepository;
import br.com.santander.icheffv1.repository.VendaRepository;

@Service
public class UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	private final VendaRepository vendaRepository;
	
	private final RoleRepository roleRepository;
	
	private BCryptPasswordEncoder encoder;

	public UsuarioService(UsuarioRepository usuarioRepository, VendaRepository vendaRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
		this.usuarioRepository = usuarioRepository;
		this.vendaRepository = vendaRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
	}
	
	@Transactional
	public Usuario create(Usuario usuario) {
		usuario.setId(null);
		usuario.setTipo(Tipo.CLIENTE);
		usuario.setDataCadastro(LocalDateTime.now());
		
		Role role = roleRepository.findByName("usuario").get().get(0);
		HashSet<Role> roleUsuario = new HashSet<Role>();
		roleUsuario.add(role);
		
		usuario.setRoles(roleUsuario);
		
		/*
		usuario.getRoles().stream()
				  .filter( r -> r.getId() == null)
				  .forEach(r -> {
					  r = this.roleRepository.save(r);
				  });
		*/
		
		//bCrypt
		usuario.setSenha(this.encoder.encode(usuario.getSenha()));
		
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario update(Usuario usuarioNovo) {

		Usuario usuarioAntigo = this.findById(usuarioNovo.getId());

		usuarioAntigo.setNome(usuarioNovo.getNome());
		usuarioAntigo.setSenha(usuarioNovo.getSenha());
		usuarioAntigo.setLogin(usuarioNovo.getLogin());
		usuarioAntigo.setBairro(usuarioNovo.getBairro());
		usuarioAntigo.setCelular(usuarioNovo.getCelular());
		usuarioAntigo.setCep(usuarioNovo.getCep());
		usuarioAntigo.setCidade(usuarioNovo.getCidade());
		usuarioAntigo.setComplemento(usuarioNovo.getComplemento());
		usuarioAntigo.setDataNascimento(usuarioNovo.getDataNascimento());
		usuarioAntigo.setEstado(usuarioNovo.getEstado());
		usuarioAntigo.setLogradouro(usuarioNovo.getLogradouro());
		usuarioAntigo.setNumero(usuarioNovo.getNumero());
		usuarioAntigo.setTelefone(usuarioNovo.getTelefone());
		
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