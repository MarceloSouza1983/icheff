package br.com.santander.icheffv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.ReceitaCategoria;
import br.com.santander.icheffv1.repository.ReceitaCategoriaRepository;

@Service
public class ReceitaCategoriaService {
	
	private final ReceitaCategoriaRepository receitaCategoriaRepository;

	public ReceitaCategoriaService(ReceitaCategoriaRepository receitaCategoriaRepository) {
		this.receitaCategoriaRepository = receitaCategoriaRepository;
	}
	
	public ReceitaCategoria create(ReceitaCategoria receitaCategoria) {
		receitaCategoria.setId(null);
		return this.receitaCategoriaRepository.save(receitaCategoria);
	}
	
	public ReceitaCategoria update(ReceitaCategoria receitaCategoriaNova) {

		ReceitaCategoria receitaCategoriaAntiga = this.findById(receitaCategoriaNova.getId());

		receitaCategoriaAntiga.setNome(receitaCategoriaNova.getNome());
		
		return this.receitaCategoriaRepository.save(receitaCategoriaAntiga);

	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.receitaCategoriaRepository.deleteById(id);
	}
	
	public List<ReceitaCategoria> findAll(){
		return this.receitaCategoriaRepository.findAll();
	}
	
	public ReceitaCategoria findById(Long id) {
		if( id == null ) {
			throw new DataIntegrityException("O id não pode ser nulo");
		}


		return this.receitaCategoriaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possível encontrar uma categoria com id " + id));
	}
	

}
