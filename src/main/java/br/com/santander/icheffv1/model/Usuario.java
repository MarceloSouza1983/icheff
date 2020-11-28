package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Entity
@Table(name = "usuarios")
@Builder
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1264482886062073151L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@NotNull(message = "A senha não pode ser nula e precisa ter no mínimo 5 caracteres")
	private String senha;
	
	@Column(name= "usu_tipo", length = 20, nullable = false)
	@ColumnDefault("0")
	@NotNull
	private Tipo tipo;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name= "usu_data_nascimento", length = 10, nullable = false)
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
	
	@Column(name= "usu_logradouro", length = 300, nullable = false)
	@NotNull(message = "O logradouro do endereço não pode ser nulo")
	private String logradouro;
	
	@Column(name= "usu_numero", length = 10, nullable = false)
	@NotNull(message = "O número não pode ser nulo")
	private String numero;
	
	@Column(name= "usu_complemento", length = 200)
	private String complemento;
	
	@Column(name= "usu_cep", length = 10, nullable = false)
	@NotNull(message = "O CEP não pode ser nulo")
	private String cep;
	
	@Column(name= "usu_bairro", length = 100, nullable = false)
	@NotNull(message = "O bairro não pode ser nulo")
	private String bairro;
	
	@Column(name= "usu_cidade", length = 100, nullable = false)
	@NotNull(message = "A cidade não pode ser nula")
	private String cidade;
	
	@Column(name= "usu_estado", length = 100, nullable = false)
	@NotNull(message = "O estado não pode ser nulo")
	private String estado;
	
	@Column(name= "usu_telefone", length = 11, nullable = false)
	private String telefone;
	
	@Column(name= "usu_celular", length = 15, nullable = false)
	@NotNull(message = "O campo celular não pode ser nulo")
	private String celular;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns  = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	public Usuario() {
		
	}
	
	public Usuario(
			Long id,
			@NotNull(message = "O nome não pode ser nulo") String nome,
			@NotNull(message = "O login não pode ser nulo e precisa ter no mínimo 5 caracteres") @Length(min = 5, max = 100) String login,
			@Length(min = 5, max = 255) @NotNull(message = "A senha não pode ser nula e precisa ter no mínimo 5 caracteres") String senha,
			LocalDate dataNascimento,
			LocalDateTime dataCadastro,
			@NotNull(message = "O rg não pode ser nulo") String rg,
			@CPF @NotNull(message = "O cpf não pode ser nulo") String cpf,
			@NotNull(message = "O logradouro do endereço não pode ser nulo") String logradouro,
			@NotNull(message = "O número não pode ser nulo") String numero,
			String complemento,
			@NotNull(message = "O CEP não pode ser nulo") String cep,
			@NotNull(message = "O bairro não pode ser nulo") String bairro,
			@NotNull(message = "A cidade não pode ser nula") String cidade,
			@NotNull(message = "O estado não pode ser nulo") String estado,
			String telefone,
			@NotNull(message = "O campo celular não pode ser nulo") String celular
	) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.dataCadastro = LocalDateTime.now();
		this.rg = rg;
		this.cpf = cpf;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.telefone = telefone;
		this.celular = celular;
	}
	
	public Usuario(Long id, @NotNull(message = "O nome não pode ser nulo") String nome,
			@NotNull(message = "O login não pode ser nulo e precisa ter no mínimo 5 caracteres") @Length(min = 5, max = 100) String login,
			@Length(min = 5, max = 255) @NotNull(message = "A senha não pode ser nula e precisa ter no mínimo 5 caracteres") String senha,
			@NotNull Tipo tipo, LocalDate dataNascimento, LocalDateTime dataCadastro,
			@NotNull(message = "O rg não pode ser nulo") String rg,
			@CPF @NotNull(message = "O cpf não pode ser nulo") String cpf,
			@NotNull(message = "O logradouro do endereço não pode ser nulo") String logradouro,
			@NotNull(message = "O número não pode ser nulo") String numero, String complemento,
			@NotNull(message = "O CEP não pode ser nulo") String cep,
			@NotNull(message = "O bairro não pode ser nulo") String bairro,
			@NotNull(message = "A cidade não pode ser nula") String cidade,
			@NotNull(message = "O estado não pode ser nulo") String estado, String telefone,
			@NotNull(message = "O campo celular não pode ser nulo") String celular, Set<Role> roles) {
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
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.telefone = telefone;
		this.celular = celular;
		this.roles = roles;
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
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public List<String> getSimpleRoles() {
		return this.roles.stream().map(role -> role.getName()).collect(Collectors.toList());
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Boolean isAdmin() {
		return this.getSimpleRoles().contains("admin");
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