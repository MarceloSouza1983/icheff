package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.IngredienteUnidade;
import br.com.santander.icheffv1.repository.IngredienteUnidadeRepository;

@Service
public class IngredienteUnidadeService {
	
	private final IngredienteUnidadeRepository ingredienteUnidadeRepository;

	public IngredienteUnidadeService(IngredienteUnidadeRepository ingredienteUnidadeRepository) {
		this.ingredienteUnidadeRepository = ingredienteUnidadeRepository;
	}
	
	public IngredienteUnidade create(IngredienteUnidade ingredienteUnidade) {
		ingredienteUnidade.setId(null);
		return this.ingredienteUnidadeRepository.save(ingredienteUnidade);
	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.ingredienteUnidadeRepository.deleteById(id);
	}
	
	public List<IngredienteUnidade> findAll(){
		return this.ingredienteUnidadeRepository.findAll();
	}
	
	public IngredienteUnidade findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.ingredienteUnidadeRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar um ingrediente com id " + id));
	}
	
}