package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {
	
	private static final long serialVersionUID = -4435672164902123790L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name= "ing_id")
	private Long id;
	
	@Column(name= "ing_nome", length = 200, nullable = false, unique = true)
	@NotNull(message = "O nome não pode ser nulo")
	@Size(min = 3, max = 200)
	private String nome;
	
	//@Column(name= "ing_unidade_padrao", length = 10, nullable = false)
	//@NotNull
	//private int unidadePadrao;
	
	@Column(name= "ing_data_cadastro", length = 100, nullable = false)
	private LocalDateTime dataCadastro;
	
	@Column(columnDefinition = "TINYINT(1)", name= "ing_ativo", length = 1, nullable = false)
	@NotNull
	private int ativo;

	@Column(name= "ing_custo", length = 10, nullable = false) // columnDefinition = "decimal(4,2)"
	@NotNull(message = "O custo não pode ser nulo")
	@Min(value = 0, message = "O valor do custo não pode ser negativo")
	private double custo;
	
	/* @ManyToOne
	@JoinColumn(name = "ingredientes_id")
	@JsonIgnore
	private Receita receita; */
	
	@ManyToOne
	@JoinColumn(name = "ing_unidade_padrao")
	private IngredienteUnidade ingredienteUnidade;
	
	public Ingrediente() { }
	
	public Ingrediente(Long id, @NotNull(message = "O nome não pode ser nulo") @Size(min = 3, max = 200) String nome,
			LocalDateTime dataCadastro, @NotNull int ativo,
			@NotNull(message = "O custo não pode ser nulo") @Min(value = 0, message = "O valor do custo não pode ser negativo") double custo,
			IngredienteUnidade ingredienteUnidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataCadastro = dataCadastro;
		this.ativo = ativo;
		this.custo = custo;
		this.ingredienteUnidade = ingredienteUnidade;
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		
//		dataCadastro = LocalDateTime.now().minusHours(3);
//		System.out.println(dataCadastro);
		this.dataCadastro = dataCadastro;
		
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
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
		Ingrediente other = (Ingrediente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}