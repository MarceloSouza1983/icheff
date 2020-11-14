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

import br.com.santander.icheffv1.model.Ingrediente;
import br.com.santander.icheffv1.service.IngredienteService;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {
	
	private final IngredienteService ingredienteService;

	public IngredienteController(IngredienteService ingredienteService) {
		this.ingredienteService = ingredienteService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody Ingrediente ingrediente) {
		
		ingrediente = this.ingredienteService.create(ingrediente);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(ingrediente.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Ingrediente ingrediente) {
		ingrediente.setId(id);
		
		this.ingredienteService.update(ingrediente);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Ingrediente> findById(@PathVariable Long id) {
		
		Ingrediente ingrediente = this.ingredienteService.findById(id);
		
		return ResponseEntity.ok(ingrediente);
	}
	
	@GetMapping
	public ResponseEntity<List<Ingrediente>> findAll() {
		
		List<Ingrediente> ingrediente = this.ingredienteService.findAll();
		
		return ResponseEntity.ok(ingrediente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.ingredienteService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}