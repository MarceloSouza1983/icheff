package br.com.santander.icheffv1.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoDeVendaDTO {

	private LocalDate data;
	
	private Long quantidade;
	
	public HistoricoDeVendaDTO(LocalDate data, Long quantidade) {
		this.data = data;
		this.quantidade = quantidade;
	}
	
	public void somaQuantidade() {
		this.quantidade++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		HistoricoDeVendaDTO other = (HistoricoDeVendaDTO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
}
