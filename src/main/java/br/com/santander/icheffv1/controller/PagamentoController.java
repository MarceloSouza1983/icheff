package br.com.santander.icheffv1.controller;

import java.time.LocalDateTime;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.santander.icheffv1.model.Pagamento;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.service.VendaService;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
	
	private final VendaService vendaService;
	
	public PagamentoController(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PutMapping
	public ResponseEntity<Void> pagamento(@Valid @RequestBody Pagamento pagamento){
		
		Random aleatorio = new Random();
		
		int numero = aleatorio.nextInt(100);
		
		//Venda
		Venda venda = this.vendaService.findById(pagamento.venda_id);
		
		if(venda.getPagamentoRealizado()) {
			return ResponseEntity.status(400).header("status-pagamento", "Pagamento já efetuado").build();
		}
		
		//Pagamento não aprovado!
		if(numero <= 80) {
			
			return ResponseEntity.status(401).header("status-pagamento", "Pagamento não aprovado").build();
			
		//Pagamento aprovado!
		} else {
			
			//Atualizar a venda
			venda.setDataPagamento(LocalDateTime.now());
			venda.setPagamentoRealizado(true);
			
			this.vendaService.save(venda);
			
			return ResponseEntity.status(200).header("status-pagamento", "Pagamento aprovado").build();
			
		}
		
	}
	
}
