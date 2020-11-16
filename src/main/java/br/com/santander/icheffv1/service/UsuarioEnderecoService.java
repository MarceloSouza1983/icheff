package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.model.UsuarioEndereco;
import br.com.santander.icheffv1.repository.UsuarioEnderecoRepository;
import br.com.santander.icheffv1.repository.UsuarioRepository;

@Service
public class UsuarioEnderecoService {
	
	private final UsuarioEnderecoRepository usuarioEnderecoRepository;

	public UsuarioEnderecoService(UsuarioEnderecoRepository usuarioEnderecoRepository) {
		this.usuarioEnderecoRepository = usuarioEnderecoRepository;
	}
	
	public UsuarioEndereco create(UsuarioEndereco usuario) {
		usuario.setId(null);
		return this.usuarioEnderecoRepository.save(usuario);
	}
	
	public UsuarioEndereco update(UsuarioEndereco usuarioNovo) {

		UsuarioEndereco usuarioAntigo = this.findById(usuarioNovo.getId());

		usuarioAntigo.setApelido(usuarioNovo.getApelido());
		usuarioAntigo.setCep(usuarioNovo.getCep());
		usuarioAntigo.setCidade(usuarioNovo.getCidade());
		usuarioAntigo.setComplemento(usuarioNovo.getComplemento());
		usuarioAntigo.setEstado(usuarioNovo.getEstado());
		usuarioAntigo.setLogradouro(usuarioNovo.getLogradouro());
		usuarioAntigo.setNumero(usuarioNovo.getNumero());
		
		return this.usuarioEnderecoRepository.save(usuarioAntigo);

	}

	public void deleteById(Long id) {
		this.findById(id);

		this.usuarioEnderecoRepository.deleteById(id);
	}
	
	public List<UsuarioEndereco> findAll(){
		return this.usuarioEnderecoRepository.findAll();
	}
	
	/* public Page<UsuarioEndereco> findAll(Pageable pageable){
		return this.usuarioEnderecoRepository.findAll(pageable);
	}
	 
	 */
	
	
	public UsuarioEndereco findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.usuarioEnderecoRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar um endereço com id " + id));
	}
	
}