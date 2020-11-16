package br.com.santander.icheffv1.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingredientes_receita")
public class IngredienteReceita implements Serializable {
	
	private static final long serialVersionUID = -2435472164992123790L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "inr_id_ingrediente")
	private Long id;
	
	@Column(name= "inr_id_unidade", length = 11, nullable = false)
	@NotNull
	private int unidade;

	@Column(name= "inr_quantidade", length = 2, nullable = false)
	@NotNull(message = "A quantidade não pode ser nula")
	@Min(value = 0, message = "A quantidade não pode ser negativa")
	private double quantidade;
	
	@ManyToOne
	@JoinColumn(name = "inr_ingrediente_receita")
	private Receita receita;
	
	@ManyToOne
	@JoinColumn(name = "ingrediente_receita")
	private Ingrediente ingrediente;
	
	@ManyToOne
	@JoinColumn(name = "inr_unidade_ingrediente")
	private IngredienteUnidade ingredienteUnidade;
	
	public IngredienteReceita() { }

	public IngredienteReceita(Long id, @NotNull int unidade,
			@NotNull(message = "A quantidade não pode ser nula") @Min(value = 0, message = "A quantidade não pode ser negativa") double quantidade,
			Receita receita, Ingrediente ingrediente, IngredienteUnidade ingredienteUnidade) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.quantidade = quantidade;
		this.receita = receita;
		this.ingrediente = ingrediente;
		this.ingredienteUnidade = ingredienteUnidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUnidade() {
		return unidade;
	}

	public void setUnidade(int unidade) {
		this.unidade = unidade;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public IngredienteUnidade getIngredienteUnidade() {
		return ingredienteUnidade;
	}

	public void setIngredienteUnidade(IngredienteUnidade ingredienteUnidade) {
		this.ingredienteUnidade = ingredienteUnidade;
	}
	
}