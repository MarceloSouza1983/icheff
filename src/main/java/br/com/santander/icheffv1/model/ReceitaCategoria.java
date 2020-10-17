package br.com.santander.icheffv1.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "receitas_categorias")
public class ReceitaCategoria implements Serializable {
	
	private static final long serialVersionUID = -2335692164902111799L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name= "cat_id")
	private Long id;
	
	@Column(name= "ing_nome", length = 100, nullable = false)
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	public ReceitaCategoria() { }

	public ReceitaCategoria(Long id, @NotNull(message = "O nome não pode ser nulo") String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

}