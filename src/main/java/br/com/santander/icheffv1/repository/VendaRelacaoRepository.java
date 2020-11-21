package br.com.santander.icheffv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.santander.icheffv1.model.VendaRelacao;

@Repository
public interface VendaRelacaoRepository extends JpaRepository<VendaRelacao, Long>{

}