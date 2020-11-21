package br.com.santander.icheffv1.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BinaryOperator;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.ReceitaResumoDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.VendaRelacao;
import br.com.santander.icheffv1.repository.VendaRelacaoRepository;

@Service
public class VendaRelacaoService {
	
	private final VendaRelacaoRepository vendaRelacaoRepository;

	public VendaRelacaoService(VendaRelacaoRepository vendaRelacaoRepository) {
		this.vendaRelacaoRepository = vendaRelacaoRepository;
	}
	
	public VendaRelacao create(VendaRelacao vendaRelacao) {
		vendaRelacao.setId(null);
		return this.vendaRelacaoRepository.save(vendaRelacao);
	}
	
	public void deleteById(Long id) {
		this.findById(id);
		this.vendaRelacaoRepository.deleteById(id);
	}
	
	public List<VendaRelacao> findAll(){
		return this.vendaRelacaoRepository.findAll();
	}
	
	public VendaRelacao findById(Long id) {
		
		if(id == null) {
			throw new DataIntegrityException("O id não pode ser nulo");
		}

		return this.vendaRelacaoRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possível encontrar uma venda com id " + id));
	}
	
	public List<VendaRelacao> findByVenda(Venda venda){
		return this.vendaRelacaoRepository.findByVenda(venda).orElse(new ArrayList<VendaRelacao>());
	}
	
	public List<ReceitaResumoDTO> findReceitasResumoByVenda(Venda venda){
		List<ReceitaResumoDTO> receitas = new ArrayList<ReceitaResumoDTO>();
		
		for(VendaRelacao receitaRelacao : this.findByVenda(venda)) {
			ReceitaResumoDTO receitaResumo = new ReceitaResumoDTO();
			
			Receita receita = receitaRelacao.getReceita();
			
			receitaResumo.setNome(receita.getNome());
			receitaResumo.setQuantidade(receitaRelacao.getQuantidade());
			
			receitas.add(receitaResumo);
		}
		
		return receitas;
	}
	
	public Double valorDaVendaByVenda(Venda venda){
		
		Double valorTotal = 0.0;
		
		for(VendaRelacao receitaRelacao : this.findByVenda(venda)) {
			valorTotal += receitaRelacao.getQuantidade() * receitaRelacao.getReceita().getPreco();
		}
		
		return valorTotal;
	}
	
	public Long countByVenda(Venda venda){
		return this.findByVenda(venda).stream()
				.map(vr -> vr.getQuantidade())
				.reduce(Long::sum).orElse(0L);
	}
	
	public Long count() {
		return this.vendaRelacaoRepository.count();
	}
	
}