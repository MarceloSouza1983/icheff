package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingredientes_unidades")
public class IngredienteUnidade implements Serializable {
	
	private static final long serialVersionUID = -4225672164902123790L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "iun_id")
	private Long id;
	
	@Column(name= "iun_unidade_sigla", length = 30, nullable = false)
	@NotNull(message = "A unidade não pode ser nula")
	private String unidadeSigla;
	
	@Column(name= "iun_nome_singular", length = 100, nullable = false)
	@NotNull(message = "Este campo não pode ser nulo")
	private String nomeSingular;
	
	@Column(name= "iun_nome_plural", length = 100, nullable = false)
	@NotNull(message = "Este campo não pode ser nulo")
	private String nomePlural;
	
	@Column(name= "iun_unidade_descricao", length = 500)
	private String descricao;
	
	@OneToMany(mappedBy = "ingredienteUnidade")
	private List <IngredienteUnidade> ingredienteUnidade;
	
	public IngredienteUnidade() { }
	
	public IngredienteUnidade(
			Long id,
			@NotNull(message = "A unidade não pode ser nula") String unidadeSigla,
			@NotNull(message = "Este campo não pode ser nulo") String nomeSingular,
			@NotNull(message = "Este campo não pode ser nulo") String nomePlural,
			@NotNull(message = "A descrição não pode ser nula") String descricao
	) {
		super();
		this.id = id;
		this.unidadeSigla = unidadeSigla;
		this.nomeSingular = nomeSingular;
		this.nomePlural = nomePlural;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnidadeSigla() {
		return unidadeSigla;
	}

	public void setUnidadeSigla(String unidadeSigla) {
		this.unidadeSigla = unidadeSigla;
	}

	public String getNomeSingular() {
		return nomeSingular;
	}

	public void setNomeSingular(String nomeSingular) {
		this.nomeSingular = nomeSingular;
	}

	public String getNomePlural() {
		return nomePlural;
	}

	public void setNomePlural(String nomePlural) {
		this.nomePlural = nomePlural;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		IngredienteUnidade other = (IngredienteUnidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}