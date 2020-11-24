package br.com.santander.icheffv1.controller;

import java.net.URI;
import java.time.LocalDateTime;
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

import br.com.santander.icheffv1.dto.VendaDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.model.CarrinhoItem;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.VendaRelacao;
import br.com.santander.icheffv1.service.ReceitaService;
import br.com.santander.icheffv1.service.UsuarioService;
import br.com.santander.icheffv1.service.VendaRelacaoService;
import br.com.santander.icheffv1.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {
	
	private final VendaService vendaService;
	
	private final UsuarioService usuarioService;
	
	private final VendaRelacaoService vendaRelacaoService;
	
	private final ReceitaService receitaService;
		
	public VendaController(
			VendaService vendaService,
			UsuarioService usuarioService,
			VendaRelacaoService vendaRelacaoService,
			ReceitaService receitaService
	) {
		this.vendaService = vendaService;
		this.usuarioService = usuarioService;
		this.vendaRelacaoService = vendaRelacaoService;
		this.receitaService = receitaService;
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create(@Valid @RequestBody List<CarrinhoItem> listaReceitas) {
		
		Usuario usuario = this.usuarioService.findById(2L); //JWT??
		
		for(CarrinhoItem item : listaReceitas) {
			if(item.getQuantidade() <= 0) {
				throw new DataIntegrityException("A quantidade da venda não pode ser menor ou igual a zero.");
			}
		}
		
		Venda venda = new Venda(
			null,
			false,
			LocalDateTime.now(),
			null,
			usuario
		);

		this.vendaService.create(venda);
		
		for(CarrinhoItem item : listaReceitas) {
			
			Receita receita = this.receitaService.findById(item.getReceita_id());
			
			VendaRelacao vendaRelacao = new VendaRelacao(null, item.getQuantidade(), receita, venda);
			
			this.vendaRelacaoService.create(vendaRelacao);
		}
		
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
	public ResponseEntity<List<VendaDTO>> findAll() {
		
		List<VendaDTO> venda = this.vendaService.findAll();
		
		return ResponseEntity.ok(venda);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.vendaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
