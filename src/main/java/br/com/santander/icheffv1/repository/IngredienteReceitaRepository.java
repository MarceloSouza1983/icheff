package br.com.santander.icheffv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.IngredienteReceita;

@Repository
public interface IngredienteReceitaRepository extends JpaRepository<IngredienteReceita, Long>{
	
}