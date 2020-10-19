package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1264482886062073151L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name= "usu_id")
	private Long id;
	
	@Column(name= "usu_nome", length = 200, nullable = false)
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	@Column(name= "usu_login", length = 100, nullable = false, unique = true)
	@NotNull(message = "O login não pode ser nulo e precisa ter no mínimo 5 caracteres")
	@Length(min = 5, max = 100)
	private String login;
	
	@Column(name= "usu_senha", length = 255, nullable = false)
	@Length(min = 5, max = 255)
	@NotNull(message = "O login não pode ser nulo e precisa ter no mínimo 5 caracteres")
	private String senha;
	
	@Column(name= "usu_tipo", length = 20, nullable = false)
	@ColumnDefault("1")
	@NotNull
	private Tipo tipo;
	
	@Column(name= "usu_data_nascimento", length = 50, nullable = false)
	private LocalDate dataNascimento;
	
	@Column(name= "usu_data_cadastro", length = 100, nullable = false)
	private LocalDateTime dataCadastro;
	
	@Column(name= "usu_rg", length = 12, nullable = false, unique = true)
	@NotNull(message = "O rg não pode ser nulo")
	private String rg;
	
	@CPF
	@Column(name= "usu_cpf", length = 14, nullable = false, unique = true)
	@NotNull(message = "O cpf não pode ser nulo")
	private String cpf;
	
	//@ManyToOne
	//@JoinColumn(name = "endereco_usuario")
	//private UsuarioEndereco usuarioEndereco;
	
	public Usuario() { }
	
	public Usuario(Long id, @NotNull String nome, @NotNull @Length(min = 5, max = 100) String login,
			@Length(min = 5, max = 255) @NotNull String senha, @NotNull Tipo tipo, LocalDate dataNascimento,
			LocalDateTime dataCadastro, @NotNull String rg, @CPF @NotNull String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.tipo = tipo;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = dataCadastro;
		this.rg = rg;
		this.cpf = cpf;
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
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}