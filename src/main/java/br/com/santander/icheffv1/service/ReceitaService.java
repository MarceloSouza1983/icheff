package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.repository.ReceitaRepository;

@Service
public class ReceitaService {
	
	private final ReceitaRepository receitaRepository;

	public ReceitaService(ReceitaRepository receitaRepository) {
		this.receitaRepository = receitaRepository;
	}
	
	public Receita create(Receita receita) {
		receita.setId(null);
		return this.receitaRepository.save(receita);
	}
	
	public Receita update(Receita receitaNova) {

		Receita receitaAntiga = this.findById(receitaNova.getId());

		receitaAntiga.setNome(receitaNova.getNome());
		receitaAntiga.setDescricao(receitaNova.getDescricao());
		receitaAntiga.setLink(receitaNova.getLink());
		receitaAntiga.setPreco(receitaNova.getPreco());
		
		return this.receitaRepository.save(receitaAntiga);

	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.receitaRepository.deleteById(id);
	}
	
	public List<Receita> findAll(){
		return this.receitaRepository.findAll();
	}
	
	public Receita findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.receitaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar uma receita com id " + id));
	}
	
}