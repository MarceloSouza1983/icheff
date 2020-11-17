package br.com.santander.icheffv1.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.santander.icheffv1.model.UsuarioEndereco;
import br.com.santander.icheffv1.service.UsuarioEnderecoService;

@RestController
@RequestMapping("/api/enderecos")
public class UsuarioEnderecoController {
	
	private final UsuarioEnderecoService usuarioEnderecoService;

	public UsuarioEnderecoController(UsuarioEnderecoService usuarioEnderecoService) {
		this.usuarioEnderecoService = usuarioEnderecoService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody UsuarioEndereco usuarioEndereco) {
	
		
		usuarioEndereco = this.usuarioEnderecoService.create(usuarioEndereco);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(usuarioEndereco.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UsuarioEndereco usuarioEndereco) {
		usuarioEndereco.setId(id);
		
		this.usuarioEnderecoService.update(usuarioEndereco);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioEndereco> findById(@PathVariable Long id) {
		
		UsuarioEndereco usuarioEndereco = this.usuarioEnderecoService.findById(id);
		
		return ResponseEntity.ok(usuarioEndereco);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioEndereco>> findAll() {
		
		List<UsuarioEndereco> usuarioEndereco = this.usuarioEnderecoService.findAll();
		
		return ResponseEntity.ok(usuarioEndereco);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.usuarioEnderecoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}