package br.com.santander.icheffv1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.ReceitaCategoriaDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.ReceitaCategoria;
import br.com.santander.icheffv1.repository.ReceitaCategoriaRepository;
import br.com.santander.icheffv1.repository.ReceitaRepository;

@Service
public class ReceitaCategoriaService {
	
	private final ReceitaCategoriaRepository receitaCategoriaRepository;
	
	private final ReceitaRepository receitaRepository;

	public ReceitaCategoriaService(ReceitaCategoriaRepository receitaCategoriaRepository, ReceitaRepository receitaRepository) {
		this.receitaCategoriaRepository = receitaCategoriaRepository;
		this.receitaRepository = receitaRepository;
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
	
	public List<ReceitaCategoriaDTO> findAll(){
		
		List<ReceitaCategoria> categorias = this.receitaCategoriaRepository.findAll();
		
		List<ReceitaCategoriaDTO> categoriasDTO = new ArrayList<ReceitaCategoriaDTO>();
		
		List<Receita> receitas = this.receitaRepository.findAll();
		
		for(ReceitaCategoria receitaCategoria : categorias) {
			ReceitaCategoriaDTO categoriaDTO = new ReceitaCategoriaDTO();
			
			categoriaDTO.setId(receitaCategoria.getId());
			categoriaDTO.setNome(receitaCategoria.getNome());
			categoriaDTO.setVegana(receitaCategoria.getVegana());
			
			Long totalReceitas = 0L;
			
			for(Receita receita : receitas) {
				if(receita.getReceitaCategoria().getNome().equals(receitaCategoria.getNome())) {
					totalReceitas++;
				}
			}
			
			categoriaDTO.setQuantidadeReceitas(totalReceitas);
			
			categoriasDTO.add(categoriaDTO);
		}
		
		return categoriasDTO;
		
	}
	
	public ReceitaCategoria findById(Long id) {
		if( id == null ) {
			throw new DataIntegrityException("O id não pode ser nulo");
		}

		return this.receitaCategoriaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possível encontrar uma categoria com id " + id));
	}
	
}