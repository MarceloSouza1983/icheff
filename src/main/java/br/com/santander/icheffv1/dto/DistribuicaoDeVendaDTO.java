package br.com.santander.icheffv1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistribuicaoDeVendaDTO {
	
	private String categoria;
	
	private Long quantidade;

	public DistribuicaoDeVendaDTO(String categoria, Long quantidade) {
		this.categoria = categoria;
		this.quantidade = quantidade;
	}
	
	public void somaQuantidade(Long qtd) {
		this.quantidade += qtd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistribuicaoDeVendaDTO other = (DistribuicaoDeVendaDTO) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		return true;
	}

}
