package br.com.santander.icheffv1.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "receitas_categorias")
public class ReceitaCategoria implements Serializable {
	
	private static final long serialVersionUID = -2335692164902111799L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "cat_id")
	private Long id;
	
	@Column(name= "cat_nome", length = 100, nullable = false)
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	@Column(name = "cat_vegana", nullable = false)
	@NotNull(message = "A classificação de vegana não pode ser nula")
	private Boolean vegana;
	
	public ReceitaCategoria() {
		
	}

	public ReceitaCategoria(Long id, @NotNull(message = "O nome não pode ser nulo") String nome, Boolean vegana) {
		super();
		this.id = id;
		this.nome = nome;
		this.vegana = vegana;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getVegana() {
		return this.vegana;
	}

	public void setVegana(Boolean vegana) {
		this.vegana = vegana;
	}

}