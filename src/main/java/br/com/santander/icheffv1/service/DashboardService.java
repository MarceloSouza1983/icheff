package br.com.santander.icheffv1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.model.Dashboard;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.dashboard.DistribuicaoDeVenda;
import br.com.santander.icheffv1.model.dashboard.HistoricoDeVenda;

@Service
public class DashboardService {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private ReceitaService receitasService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VendaService vendaService;
	
	/* Mock */
	public Long getRandomNumber(int min, int max) {
	    return Math.round(((Math.random() * (max - min)) + min));
	}
	/* Fim Mock */

	public Dashboard dashboardBuilder() {
		
		Dashboard dashboard = new Dashboard();
		
		//Numéricos
		dashboard.setIngredientesCadastrados(ingredienteService.count());
		dashboard.setReceitasCadastradas(receitasService.count());
		dashboard.setClientesCadastrados(usuarioService.count());
		dashboard.setVendasRealizadas(vendaService.count());
		
		//Histórico de vendas
		List<HistoricoDeVenda> historicoDeVenda = new ArrayList<HistoricoDeVenda>();
		
		//Implementação do service
        for(Venda venda : vendaService.findAll()) {
        	
        	LocalDate data = venda.getDataVenda().toLocalDate();
        	
        	HistoricoDeVenda novaVenda = new HistoricoDeVenda(data, 1L);
        	
        	if(historicoDeVenda.contains(novaVenda)) {
        		int index = historicoDeVenda.indexOf(novaVenda);
        		historicoDeVenda.get(index).somaQuantidade();
        	} else {
        		historicoDeVenda.add(novaVenda);
        	}
        	
        }
		
		dashboard.setHistoricoDeVendas(historicoDeVenda);
		
		//Histórico de distribuiçãod e vendas
		List<DistribuicaoDeVenda> distribuicaoDeVendas = new ArrayList<DistribuicaoDeVenda>();
		
		/* Mock */
		distribuicaoDeVendas.add(new DistribuicaoDeVenda("Variados", getRandomNumber(25,55)));
		distribuicaoDeVendas.add(new DistribuicaoDeVenda("Peixes e frutos do mar", getRandomNumber(25,55)));
		distribuicaoDeVendas.add(new DistribuicaoDeVenda("Fitness", getRandomNumber(25,55)));
		distribuicaoDeVendas.add(new DistribuicaoDeVenda("Vegetarianos", getRandomNumber(25,55)));
        /* Fim Mock */
		
		//Implementação do service
		/*
        for(Venda venda : vendaService.findAll()) {
        	
        	DistribuicaoDeVenda novaVenda = new DistribuicaoDeVenda(venda.getCategoria(), 1L);
        	
        	if(distribuicaoDeVendas.contains(novaVenda)) {
        		int index = distribuicaoDeVendas.indexOf(novaVenda);
        		distribuicaoDeVendas.get(index).somaQuantidade();
        	} else {
        		distribuicaoDeVendas.add(novaVenda);
        	}
        	
        }
        */
		
		dashboard.setDistribuicaoDeVendas(distribuicaoDeVendas);
		
		return dashboard;

	}
	
}
