package br.com.santander.icheffv1.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ingredientes_receita")
public class IngredienteReceita implements Serializable {
	
	private static final long serialVersionUID = -2435472164992123790L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "inr_id")
	private Long id;
	
	@Column(name= "inr_quantidade", length = 2, nullable = false)
	@NotNull(message = "A quantidade não pode ser nula")
	@Min(value = 0, message = "A quantidade não pode ser negativa")
	private double quantidade;
	
	@ManyToOne
	@NotNull(message = "A receita não pode ser nula")
	@JoinColumn(name = "inr_receita_id", referencedColumnName = "rec_id")
	private Receita receita;
	
	@ManyToOne
	@NotNull(message = "O ingrediente não pode ser nulo")
	@JoinColumn(name = "inr_ingrediente_id", referencedColumnName = "ing_id")
	private Ingrediente ingrediente;
	
	public IngredienteReceita() {
		
	}

	public IngredienteReceita(
		Long id,
		@NotNull(message = "A quantidade não pode ser nula") @Min(value = 0, message = "A quantidade não pode ser negativa") double quantidade,
		@NotNull(message = "A receita não pode ser nula") Receita receita,
		@NotNull(message = "O ingrediente não pode ser nulo") Ingrediente ingrediente
	) {
		this.id = id;
		this.quantidade = quantidade;
		this.receita = receita;
		this.ingrediente = ingrediente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		IngredienteReceita other = (IngredienteReceita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}