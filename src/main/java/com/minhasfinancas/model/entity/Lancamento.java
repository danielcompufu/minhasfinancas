package com.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.minhasfinancas.model.enums.StatusLancamento;
import com.minhasfinancas.model.enums.TipoLancamento;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Usuario usuario;
	
	@NotBlank
	private String descricao;
	
	@NotBlank
	private Integer mes;
	
	@NotBlank
	private Integer ano;
	
	@NotBlank
	private BigDecimal valor;
	
	@NotBlank
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	@NotBlank
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotBlank
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status;
	
}
