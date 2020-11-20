package br.com.santander.icheffv1.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vendas_relacao")
public class VendaRelacao implements Serializable {

	private static final long serialVersionUID = -8114530627690500526L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name= "vr_id")
	private Long id;

	@Column(name= "vr_quantidade_receita", length = 11, nullable = false)
	@ColumnDefault("1")
	@NotNull(message = "A receita não pode ser nula")
	@Min(value = 0, message = "A quantidade não pode ser negativa")
	private double preco;

	@ManyToOne
	@JoinColumn(name = "vr_receita_id", nullable = false)
	private Receita receita;

	@ManyToOne
	@JoinColumn(name = "vr_venda_id", nullable = false)
	private Venda venda;

	public VendaRelacao() {
		
	}

}