package br.com.santander.icheffv1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.DistribuicaoDeVendaDTO;
import br.com.santander.icheffv1.dto.HistoricoDeVendaDTO;
import br.com.santander.icheffv1.dto.VendaDTO;
import br.com.santander.icheffv1.model.Dashboard;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.VendaRelacao;

@Service
public class DashboardService {
	
	private IngredienteService ingredienteService;

	private ReceitaService receitasService;
	
	private UsuarioService usuarioService;
	
	private VendaService vendaService;
	
	private VendaRelacaoService vendaRelacaoService;
	
	public DashboardService(
		IngredienteService ingredienteService,
		ReceitaService receitasService,
		UsuarioService usuarioService,
		VendaService vendaService,
		VendaRelacaoService vendaRelacaoService
	){
		this.ingredienteService = ingredienteService;
		this.receitasService = receitasService;
		this.usuarioService = usuarioService;
		this.vendaService = vendaService;
		this.vendaRelacaoService = vendaRelacaoService;
	}

	public Dashboard dashboardBuilder() {
		
		Dashboard dashboard = new Dashboard();
		
		//Numéricos
		dashboard.setIngredientesCadastrados(ingredienteService.count());
		dashboard.setReceitasCadastradas(receitasService.count());
		dashboard.setClientesCadastrados(usuarioService.count());
		dashboard.setVendasRealizadas(vendaService.count());
		
		//Histórico de vendas
		List<HistoricoDeVendaDTO> historicoDeVenda = new ArrayList<HistoricoDeVendaDTO>();
		
		//Implementação do service
        for(VendaDTO venda : vendaService.findAll()) {
        	
        	LocalDate data = venda.getDataVenda().toLocalDate();
        	
        	HistoricoDeVendaDTO novaVenda = new HistoricoDeVendaDTO(data, 1L);
        	
        	if(historicoDeVenda.contains(novaVenda)) {
        		int index = historicoDeVenda.indexOf(novaVenda);
        		historicoDeVenda.get(index).somaQuantidade();
        	} else {
        		historicoDeVenda.add(novaVenda);
        	}
        	
        }
		
		dashboard.setHistoricoDeVendas(historicoDeVenda);
		
		//Histórico de distribuiçãod e vendas
		List<DistribuicaoDeVendaDTO> distribuicaoDeVendas = new ArrayList<DistribuicaoDeVendaDTO>();
		
		//Implementação do service
        for(VendaRelacao vendaRelacao : vendaRelacaoService.findAll()){
        	
        	String categoria = vendaRelacao.getReceita().getReceitaCategoria().getNome();
        	Long quantidade = vendaRelacao.getQuantidade();
        	
        	DistribuicaoDeVendaDTO novaVenda = new DistribuicaoDeVendaDTO(categoria, 1L);
        	
        	if(distribuicaoDeVendas.contains(novaVenda)) {
        		int index = distribuicaoDeVendas.indexOf(novaVenda);
        		distribuicaoDeVendas.get(index).somaQuantidade(quantidade);
        	} else {
        		distribuicaoDeVendas.add(novaVenda);
        	}
        	
        }
		
		dashboard.setDistribuicaoDeVendas(distribuicaoDeVendas);
		
		return dashboard;

	}
	
}
