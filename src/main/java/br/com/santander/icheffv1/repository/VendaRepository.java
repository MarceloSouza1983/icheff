package br.com.santander.icheffv1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.model.Usuario;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>{
	
	public Optional<Long> countByUsuario(Usuario usuario);

}