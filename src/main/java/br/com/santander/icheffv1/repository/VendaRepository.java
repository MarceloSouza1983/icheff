package br.com.santander.icheffv1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>{

	public Optional<List<Venda>> findByUsuario(Usuario usuario);
	
	public Optional<Long> countByUsuario(Usuario usuario);
	
}