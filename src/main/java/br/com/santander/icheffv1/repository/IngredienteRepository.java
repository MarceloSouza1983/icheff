package br.com.santander.icheffv1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.Ingrediente;
import br.com.santander.icheffv1.model.IngredienteUnidade;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>{

	public Optional<Ingrediente> findByNomeAndIngredienteUnidade(String nome, IngredienteUnidade ingredienteUnidade);
	
}