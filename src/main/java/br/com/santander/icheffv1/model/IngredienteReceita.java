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
			@NotNull(message = "A quantidade não pode ser nula")
			@Min(value = 0, message = "A quantidade não pode ser negativa")
			double quantidade,
			@NotNull(message = "A receita não pode ser nula")
			Receita receita,
			@NotNull(message = "O ingrediente não pode ser nulo")
			Ingrediente ingrediente
	) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.receita = receita;
		this.ingrediente = ingrediente;
	}
	
}