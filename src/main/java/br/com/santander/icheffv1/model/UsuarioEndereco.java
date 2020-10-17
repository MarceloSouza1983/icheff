package br.com.santander.icheffv1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Embeddable
@Table(name = "usuarios_enderecos")
public class UsuarioEndereco implements Serializable {
	
	private static final long serialVersionUID = -8914530627690509526L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name= "end_id")
	private Long id;
	
	
	@Column(name= "end_id_usuario", length = 100, nullable = false)
	@NotNull
	private Long usuarioEnd;
	
	@Column(name= "end_apelido", length = 50, nullable = false)
	@NotNull(message = "O apelido do endereço não pode ser nulo")
	private String apelido;
	
	@Column(name= "end_logradouro", length = 300, nullable = false)
	@NotNull(message = "O logradouro do endereço não pode ser nulo")
	private String logradouro;
	
	@Column(name= "end_numero", length = 10, nullable = false)
	@NotNull(message = "O número não pode ser nulo")
	private String numero;
	
	@Column(name= "end_complemento", length = 200)
	private String complemento;
	
	@Column(name= "end_cep", length = 10, nullable = false)
	@NotNull(message = "O CEP não pode ser nulo")
	private String cep;
	
	@Column(name= "end_cidade", length = 100, nullable = false)
	@NotNull(message = "A cidade não pode ser nula")
	private String cidade;
	
	@Column(name= "end_estado", length = 100, nullable = false)
	@NotNull(message = "O estado não pode ser nulo")
	private String estado;
	
	@Column(name= "end_data_cadastro", length = 100, nullable = false)
	@NotNull
	private LocalDateTime dataCadastro;
	
	/*@ManyToOne
	@JoinColumn(name = "ingredientes_id")
	@JsonIgnore
	private Venda venda; */
	
	@JoinColumn(name = "ven_id")
    @OneToOne
    private Venda venda;

    //@JoinColumn(name = "usu_id")
    //@OneToOne
    //private Usuario usuario;
	
    @ManyToOne
	@JoinColumn(name = "endereco_usuario")
	private Usuario usuario;
    
	public UsuarioEndereco() { }
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioEnd() {
		return usuarioEnd;
	}

	public void setUsuarioEnd(Long usuarioEnd) {
		this.usuarioEnd = usuarioEnd;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	
	
	
	

}