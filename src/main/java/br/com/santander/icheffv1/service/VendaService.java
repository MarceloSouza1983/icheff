package br.com.santander.icheffv1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.santander.icheffv1.dto.ReceitaResumoDTO;
import br.com.santander.icheffv1.dto.UsuarioDTO;
import br.com.santander.icheffv1.dto.UsuarioVendaDTO;
import br.com.santander.icheffv1.dto.VendaDTO;
import br.com.santander.icheffv1.exception.DataIntegrityException;
import br.com.santander.icheffv1.exception.ObjectNotFoundException;
import br.com.santander.icheffv1.model.Receita;
import br.com.santander.icheffv1.model.Usuario;
import br.com.santander.icheffv1.model.Venda;
import br.com.santander.icheffv1.repository.VendaRelacaoRepository;
import br.com.santander.icheffv1.repository.VendaRepository;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	
	private final VendaRelacaoService vendaRelacaoService;
	
	public VendaService(VendaRepository vendaRepository, VendaRelacaoService relacaoService) {
		this.vendaRepository = vendaRepository;
		this.vendaRelacaoService = relacaoService;
	}
	
	public Venda create(Venda venda) {
		venda.setId(null);
		return this.vendaRepository.save(venda);
	}
	
	public void deleteById(Long id) {
		this.findById(id);

		this.vendaRepository.deleteById(id);
	}
	
	public List<VendaDTO> findAll(){
		
		List<VendaDTO> vendasDTO = new ArrayList<VendaDTO>();
		
		for(Venda venda : this.vendaRepository.findAll()) {
			VendaDTO vendaDTO = new VendaDTO();
			
			vendaDTO.setId(venda.getId());
			vendaDTO.setDataVenda(venda.getDataVenda());
			vendaDTO.setDataPagamento(venda.getDataPagamento());
			vendaDTO.setPagamentoRealizado(venda.getPagamentoRealizado());
			
			//Usuário
			UsuarioVendaDTO usuarioVendaDTO = new UsuarioVendaDTO();
			Usuario user = venda.getUsuario();
			
			usuarioVendaDTO.setId(user.getId());
			usuarioVendaDTO.setNome(user.getNome());
			usuarioVendaDTO.setLogradouro(user.getLogradouro());
			usuarioVendaDTO.setNumero(user.getNumero());
			usuarioVendaDTO.setCep(user.getCep());
			usuarioVendaDTO.setComplemento(user.getComplemento());
			usuarioVendaDTO.setBairro(user.getBairro());
			usuarioVendaDTO.setBairro(user.getBairro());
			usuarioVendaDTO.setCidade(user.getCidade());
			usuarioVendaDTO.setEstado(user.getEstado());
			usuarioVendaDTO.setTelefone(user.getTelefone());
			usuarioVendaDTO.setCelular(user.getCelular());;
			
			vendaDTO.setUsuario(usuarioVendaDTO);
			
			//Quantidade de vendas
			Long qtdReceitas = this.vendaRelacaoService.countByVenda(venda);
			vendaDTO.setQuantidadeReceitas(qtdReceitas);
			
			//Receitas
			List<ReceitaResumoDTO> receitas = this.vendaRelacaoService.findReceitasResumoByVenda(venda);
			vendaDTO.setReceitas(receitas);
			
			//Valor da venda
			Double valorVenda = this.vendaRelacaoService.valorDaVendaByVenda(venda);
			vendaDTO.setValorVenda(valorVenda);
			
			vendasDTO.add(vendaDTO);
		}
		
		return vendasDTO;
	}
	
	public Venda save(Venda venda) {
		return this.vendaRepository.save(venda);
	}
	
	public Venda findById(Long id) {
		
		if( id == null ) {
			throw new DataIntegrityException("O id não pode ser nulo");
		}

		return this.vendaRepository.findById(id)
				.orElseThrow( () -> new ObjectNotFoundException("Não foi possível encontrar uma venda com id " + id));
	}
	
	public Long count() {
		return this.vendaRepository.count();
	}
	
}