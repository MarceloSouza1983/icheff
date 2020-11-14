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

import br.com.santander.icheffv1.model.ReceitaCategoria;
import br.com.santander.icheffv1.service.ReceitaCategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class ReceitaCategoriaController {
	
	private final ReceitaCategoriaService receitaCategoriaService;

	public ReceitaCategoriaController(ReceitaCategoriaService receitaCategoriaService) {
		this.receitaCategoriaService = receitaCategoriaService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody ReceitaCategoria receitaCategoria) {
		
		receitaCategoria = this.receitaCategoriaService.create(receitaCategoria);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(receitaCategoria.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ReceitaCategoria receitaCategoria) {
		receitaCategoria.setId(id);
		
		this.receitaCategoriaService.update(receitaCategoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaCategoria> findById(@PathVariable Long id) {
		
		ReceitaCategoria receitaCategoria = this.receitaCategoriaService.findById(id);
		
		return ResponseEntity.ok(receitaCategoria);
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaCategoria>> findAll() {
		
		List<ReceitaCategoria> receitaCategoria = this.receitaCategoriaService.findAll();
		
		return ResponseEntity.ok(receitaCategoria);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.receitaCategoriaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
