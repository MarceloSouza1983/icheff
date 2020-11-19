package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.util.List;

import br.com.santander.icheffv1.model.dashboard.DistribuicaoDeVenda;
import br.com.santander.icheffv1.model.dashboard.HistoricoDeVenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 7723297942842532995L;

	private Long ingredientesCadastrados;
	
	private Long receitasCadastradas;
	
	private Long clientesCadastrados;
	
	private Long vendasRealizadas;
		
	private List<HistoricoDeVenda> historicoDeVendas;
	
	private List<DistribuicaoDeVenda> distribuicaoDeVendas;

}
