package br.com.santander.icheffv1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.repository.VendaRepository;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;

	public VendaService(VendaRepository vendaRepository) {
		this.vendaRepository = vendaRepository;
	}
	
	public Venda create(Venda venda) {
		venda.setId(null);
		return this.vendaRepository.save(venda);
	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.vendaRepository.deleteById(id);
	}
	
	public List<Venda> findAll(){
		return this.vendaRepository.findAll();
	}
	
	public Venda findById(Long id) {
		
		if( id == null ) {
			throw new DataIntegrityException("O id não pode ser nulo");
		}

		return this.vendaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possível encontrar uma venda com id " + id));
	}
	
}
