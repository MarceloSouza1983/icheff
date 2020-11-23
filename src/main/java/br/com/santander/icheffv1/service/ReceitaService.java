package br.com.santander.icheffv1.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.IngredienteDto;
import br.com.santander.icheffv1.dto.ReceitaDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.IngredienteReceita;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.repository.IngredienteReceitaRepository;
import br.com.santander.icheffv1.repository.ReceitaRepository;

@Service
public class ReceitaService {
	
	private final ReceitaRepository receitaRepository;
	
	private final IngredienteReceitaRepository ingredienteReceitaRepository;
	
	public ReceitaService(ReceitaRepository receitaRepository, IngredienteReceitaRepository ingredienteReceitaRepository) {
		this.receitaRepository = receitaRepository;
		this.ingredienteReceitaRepository = ingredienteReceitaRepository;
	}
	
	public Receita create(Receita receita) {
		receita.setId(null);
		receita.setDataCadastro(LocalDateTime.now());
		Receita receitaDB = this.receitaRepository.save(receita);
		
		for(IngredienteReceita ingrediente : receita.getIngredientes()) {
			ingrediente.setReceita(receitaDB);
			this.ingredienteReceitaRepository.save(ingrediente);
		}
		
		return receitaDB;
	}
	
	public Receita update(Receita receitaNova) {

		Receita receitaAntiga = this.findById(receitaNova.getId());

		receitaAntiga.setNome(receitaNova.getNome());
		receitaAntiga.setDescricao(receitaNova.getDescricao());
		receitaAntiga.setLinkVideo(receitaNova.getLinkVideo());
		receitaAntiga.setPreco(receitaNova.getPreco());
		receitaAntiga.setAtiva(receitaNova.getAtiva());
		receitaAntiga.setPorcoes(receitaNova.getPorcoes());
		
		//Ingredientes
		List<IngredienteReceita> ingredientesAntigos = new ArrayList<IngredienteReceita>();
		List<IngredienteReceita> novosIngredientes = new ArrayList<IngredienteReceita>();
		
		for(IngredienteReceita ingrediente : receitaAntiga.getIngredientes()) {
			ingredientesAntigos.add(ingrediente);
		}
		
		for(IngredienteReceita ingrediente : receitaNova.getIngredientes()) {
			ingrediente.setReceita(receitaAntiga);
			if(ingredientesAntigos.contains(ingrediente)) {
				novosIngredientes.add(ingrediente);
				ingredientesAntigos.remove(ingrediente);
			} else {
				novosIngredientes.add(this.ingredienteReceitaRepository.save(ingrediente));
			}
		}
		
		for(IngredienteReceita ingrediente : ingredientesAntigos) {
			this.ingredienteReceitaRepository.delete(ingrediente);
		}
		
		receitaAntiga.setIngredientes(novosIngredientes);
		//Fim ingredientes
		
		return this.receitaRepository.save(receitaAntiga);
	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.receitaRepository.deleteById(id);
	}
	
	public List<ReceitaDTO> findAll(){
		List<Receita> receitas = this.receitaRepository.findAll();
		
		List<ReceitaDTO> receitasDto = new ArrayList<>();
		for(Receita receita: receitas) {
			
			ReceitaDTO dto = new ReceitaDTO();
			
			dto.setCategoria(receita.getReceitaCategoria().getNome());
			dto.setNome(receita.getNome());
			dto.setDescricao(receita.getDescricao());
			dto.setImagem(receita.getImagem());
			dto.setPreco(receita.getPreco());
			dto.setIngredientes(new ArrayList<>());
			dto.setLinkVideo(receita.getLinkVideo());
			dto.setId(receita.getId());
			dto.setCategoriaId(receita.getReceitaCategoria().getId());
			dto.setAtiva(receita.getAtiva());
			dto.setPorcoes(receita.getPorcoes());
			
			double totalCusto = 0;
			
			for(IngredienteReceita ingrediente : receita.getIngredientes()) {
				IngredienteDto ingredienteDto = new IngredienteDto();
				
				ingredienteDto.setId(ingrediente.getId());
				ingredienteDto.setIngredienteId(ingrediente.getIngrediente().getId());
				ingredienteDto.setNome(ingrediente.getIngrediente().getNome());
				ingredienteDto.setUnidade(ingrediente.getIngrediente().getIngredienteUnidade().getUnidadeSigla());
				ingredienteDto.setUnidadeSingular(ingrediente.getIngrediente().getIngredienteUnidade().getNomeSingular());
				ingredienteDto.setUnidadePlural(ingrediente.getIngrediente().getIngredienteUnidade().getNomePlural());
				ingredienteDto.setQuantidade(ingrediente.getQuantidade());
				
				totalCusto += ingrediente.getIngrediente().getCusto()*ingrediente.getQuantidade();
				
				dto.getIngredientes().add(ingredienteDto);
			}
			
			dto.setCusto(BigDecimal.valueOf(totalCusto).setScale(2, RoundingMode.HALF_UP).doubleValue());
			
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
	
	public Long count() {
		return this.receitaRepository.count();
	}
	
}