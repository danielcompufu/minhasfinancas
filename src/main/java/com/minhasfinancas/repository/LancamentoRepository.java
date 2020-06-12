package com.minhasfinancas.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minhasfinancas.model.entity.Lancamento;
import com.minhasfinancas.model.enums.StatusLancamento;
import com.minhasfinancas.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	

	@Query(value = 
			" select sum(l.valor) from Lancamento l join l.usuario u "
		  + " where u.id = :idUsuario and l.tipo = :tipo and l.status = :status group by u ")
	BigDecimal obterSaldoPorTipoLancamentoUsuarioStatus(
			@Param(value = "idUsuario") Long idUsuario, 
			@Param(value = "tipo") TipoLancamento tipo,
			@Param(value = "status") StatusLancamento status);

}
