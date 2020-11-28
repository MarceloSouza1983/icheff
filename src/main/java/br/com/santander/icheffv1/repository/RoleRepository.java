package br.com.santander.icheffv1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<List<Role>> findByName(String name);

}