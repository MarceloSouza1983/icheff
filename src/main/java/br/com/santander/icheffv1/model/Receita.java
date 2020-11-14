package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

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
	
	//@SequenceGenerator(name = "xx", sequenceName = "xx", initialValue = 1, allocationSize = 1)
	//@Column(name= "rec_id_categoria", length = 11, nullable = false, unique = true)
	//private Integer categoria;
	
	@Column(name= "rec_link_youtube", length = 500)
	private String link;
	
	@Column(columnDefinition = "TEXT", name= "rec_receita_txt", length = 1500)
	private String descricao;
	
	@Column(name= "rec_data_cadastro", length = 100, nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime dataCadastro;
	
	@Column(name= "rec_preco", length = 12, nullable = false)
	@ColumnDefault("0")
	@NotNull(message = "O preço não pode ser nulo")
	@Min(value = 0, message = "O preço não pode ser negativo")
	private double preco;
	
	//@OneToMany(mappedBy="receita")
	//private Set<Ingrediente> ingredientes;
	
	@ManyToOne
	@JoinColumn(name = "FK_categoria_receita")
	private ReceitaCategoria receitaCategoria;
	
	@ManyToOne
	@JoinColumn(name = "FK_usuario_receita")
	private Usuario usuario;
	
	public Receita() { }
	
	public Receita(Long id, @NotNull String nome, String link, String descricao, LocalDateTime dataCadastro,
			@NotNull double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.link = link;
		this.descricao = descricao;
		this.dataCadastro = dataCadastro;
		this.preco = preco;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
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