package br.com.santander.icheffv1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Ingrediente;
import br.com.santander.icheffv1.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	private final IngredienteRepository ingredienteRepository;

	public IngredienteService(IngredienteRepository ingredienteRepository) {
		this.ingredienteRepository = ingredienteRepository;
	}
	
	public Ingrediente create(Ingrediente ingrediente) {
		ingrediente.setId(null);
		return this.ingredienteRepository.save(ingrediente);
	}
	
	public Ingrediente update(Ingrediente ingredienteNovo) {

		Ingrediente ingredienteAntigo = this.findById(ingredienteNovo.getId());

		ingredienteAntigo.setNome(ingredienteNovo.getNome());
		ingredienteAntigo.setCusto(ingredienteNovo.getCusto());
		ingredienteAntigo.setAtivo(ingredienteNovo.getAtivo());
		ingredienteAntigo.setIngredienteUnidade(ingredienteNovo.getIngredienteUnidade());
		
		return this.ingredienteRepository.save(ingredienteAntigo);

	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.ingredienteRepository.deleteById(id);
	}
	
	public List<Ingrediente> findAll(){
		return this.ingredienteRepository.findAll();
	}
	
	public Ingrediente findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.ingredienteRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar um ingrediente com id " + id));
	}
	
}