package br.com.santander.icheffv1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.IngredienteUnidade;

@Repository
public interface IngredienteUnidadeRepository extends JpaRepository<IngredienteUnidade, Long>{
	
	public Optional<List<IngredienteUnidade>> findByUnidadeSigla(String unidadeSigla);

}