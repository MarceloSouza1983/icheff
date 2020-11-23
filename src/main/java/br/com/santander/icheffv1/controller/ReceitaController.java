package br.com.santander.icheffv1.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.santander.icheffv1.dto.ReceitaDTO;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.service.ReceitaService;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {
	
	private final ReceitaService receitaService;

	public ReceitaController(ReceitaService receitaService) {
		this.receitaService = receitaService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody Receita receita) {
		
		receita = this.receitaService.create(receita);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(receita.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Receita receita) {
		receita.setId(id);
		
		this.receitaService.update(receita);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Receita> findById(@PathVariable Long id) {
		
		Receita receita = this.receitaService.findById(id);
		
		return ResponseEntity.ok(receita);
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaDTO>> findAll() {
		
		List<ReceitaDTO> receita = this.receitaService.findAll();
		
		return ResponseEntity.ok(receita);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.receitaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}