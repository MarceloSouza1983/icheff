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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.santander.icheffv1.model.IngredienteUnidade;
import br.com.santander.icheffv1.service.IngredienteUnidadeService;

@RestController
@RequestMapping("/api/ingredienteunidade")
public class IngredienteUnidadeController {
	
	private final IngredienteUnidadeService ingredienteUnidadeService;

	public IngredienteUnidadeController(IngredienteUnidadeService ingredienteUnidadeService) {
		this.ingredienteUnidadeService = ingredienteUnidadeService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody IngredienteUnidade ingredienteUnidade) {
		
		ingredienteUnidade = this.ingredienteUnidadeService.create(ingredienteUnidade);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(ingredienteUnidade.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<IngredienteUnidade> findById(@PathVariable Long id) {
		
		IngredienteUnidade ingredienteUnidade = this.ingredienteUnidadeService.findById(id);
		
		return ResponseEntity.ok(ingredienteUnidade);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<IngredienteUnidade>> findAll() {
		
		List<IngredienteUnidade> ingredienteUnidade = this.ingredienteUnidadeService.findAll();
		
		return ResponseEntity.ok(ingredienteUnidade);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.ingredienteUnidadeService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}