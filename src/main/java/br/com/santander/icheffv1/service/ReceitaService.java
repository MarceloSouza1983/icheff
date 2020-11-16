package br.com.santander.icheffv1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.IngredienteDto;
import br.com.santander.icheffv1.dto.ReceitaDto;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.IngredienteReceita;
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
	
	public List<ReceitaDto> findAll(){
		List<Receita> receitas = this.receitaRepository.findAll();

		List<ReceitaDto> receitasDto = new ArrayList<>();
		for(Receita receita: receitas) {

			ReceitaDto dto = new ReceitaDto();

			dto.setCategoria(receita.getReceitaCategoria().getNome());
			dto.setNome(receita.getNome());
			dto.setDescricao(receita.getDescricao());
			dto.setImagem(receita.getImagem());
			dto.setPreco(receita.getPreco());
			dto.setListaIngredientes(new ArrayList<>());
			dto.setLinkVideo(receita.getLink());
			dto.setId(receita.getId());

			for(IngredienteReceita ingrediente : receita.getIngredientes()) {
				IngredienteDto ingredienteDto = new IngredienteDto();
				ingredienteDto.setNome(ingrediente.getIngrediente().getNome());
				ingredienteDto.setQuantidade(ingrediente.getQuantidade());
				ingredienteDto.setUnidadeSingular(ingrediente.getIngredienteUnidade().getNomeSingular());
				ingredienteDto.setUnidadePlural(ingrediente.getIngredienteUnidade().getNomePlural());

				dto.getListaIngredientes().add(ingredienteDto);
			}

			receitasDto.add(dto);
		}


		return receitasDto;
	}
	
	public Receita findById(Long id) {
		Optional
		.ofNullable(id)
		.orElseThrow( () -> new DataIntegrityException("O id não pode ser nulo"));

		return this.receitaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possivel encontrar uma receita com id " + id));
	}
	
}