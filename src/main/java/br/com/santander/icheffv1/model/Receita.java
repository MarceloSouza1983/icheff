package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "receitas")
public class Receita implements Serializable {
	
	private static final long serialVersionUID = 8059465349275819173L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "rec_id")
	private Long id;
	
	@Column(name= "rec_nome", length = 200, nullable = false, unique = true)
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	@Column(name= "rec_link_youtube", length = 500)
	private String linkVideo;
	
	@Column(name= "rec_porcoes", length = 11)
	private Long porcoes;
	
	@Column(name= "rec_link_imagem", length = 500)
	private String imagem;
	
	@Column(columnDefinition = "TEXT", name= "rec_receita_txt", length = 1500)
	private String descricao;
	
	@Column(name= "rec_data_cadastro", length = 100, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name= "rec_preco", length = 12, nullable = false)
	@ColumnDefault("0")
	@NotNull(message = "O preço não pode ser nulo")
	@Min(value = 0, message = "O preço não pode ser negativo")
	private double preco;
	
	@OneToMany(mappedBy="receita")
	private List<IngredienteReceita> ingredientes;
	
	@ManyToOne
	@JoinColumn(name = "FK_categoria_receita")
	private ReceitaCategoria receitaCategoria;
	
	@Column(columnDefinition = "TINYINT(1)", name= "rec_ativa", length = 1, nullable = false)
	@NotNull
	private Boolean ativa;
	
	public Receita() {
		
	}
	
	public Receita(
		Long id,
		@NotNull String nome,
		String imagem,
		String linkVideo,
		ReceitaCategoria receitaCategoria,
		Long porcoes,
		String descricao,
		@NotNull double preco
	) {
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
		this.linkVideo = linkVideo;
		this.receitaCategoria = receitaCategoria;
		this.porcoes = porcoes;
		this.descricao = descricao;
		this.dataCadastro = LocalDateTime.now();
		this.preco = preco;
		this.ativa = true;
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
		Receita other = (Receita) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}