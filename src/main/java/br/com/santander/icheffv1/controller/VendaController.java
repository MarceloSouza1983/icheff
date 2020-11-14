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

import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	private final VendaService vendaService;
	
	public VendaController(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody Venda venda) {
		
		venda = this.vendaService.create(venda);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(venda.getId())
				 .toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> findById(@PathVariable Long id) {
		
		Venda venda = this.vendaService.findById(id);
		
		return ResponseEntity.ok(venda);
	}
	
	@GetMapping
	public ResponseEntity<List<Venda>> findAll() {
		
		List<Venda> venda = this.vendaService.findAll();
		
		return ResponseEntity.ok(venda);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.vendaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
