package br.com.santander.icheffv1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.Tipo;
import br.com.santander.icheffv1.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<List<Usuario>> findByTipo(Tipo tipo);
	
}