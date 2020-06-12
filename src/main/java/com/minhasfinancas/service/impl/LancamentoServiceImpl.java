package com.minhasfinancas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.minhasfinancas.exception.RegraNegocioException;
import com.minhasfinancas.model.entity.Lancamento;
import com.minhasfinancas.model.enums.StatusLancamento;
import com.minhasfinancas.model.enums.TipoLancamento;
import com.minhasfinancas.repository.LancamentoRepository;

@Service
public class LancamentoServiceImpl implements com.minhasfinancas.service.LancamentoService {

	@Autowired
	private LancamentoRepository repository;

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validar(lancamento);
		lancamento.setDataCadastro(LocalDate.now());
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validar(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);

	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamento) {
		Example<Lancamento> example = Example.of(lancamento,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public Lancamento atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		return repository.save(lancamento);
	}

	@Override
	public void validar(Lancamento lancamento) {
		if (lancamento.getDescricao() == null || "".equals(lancamento.getDescricao().trim())) {
			throw new RegraNegocioException("Informe uma Descricao valida");
		}

		if (lancamento.getMes() == null || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um Mes valido");
		}

		if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Informe um Ano valido");
		}

		if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um Usuario valido");
		}

		if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor valido");
		}

		if (lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um Tipo valido");
		}
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public BigDecimal obterSaldoPorUsuario(Long idUsuario) {
		BigDecimal saldoReceita = repository.obterSaldoPorTipoLancamentoUsuarioStatus(idUsuario, TipoLancamento.RECEITA,
				StatusLancamento.EFETIVADO);
		BigDecimal saldoDespesa = repository.obterSaldoPorTipoLancamentoUsuarioStatus(idUsuario, TipoLancamento.DESPESA,
				StatusLancamento.EFETIVADO);

		if (saldoDespesa == null) {
			saldoDespesa = BigDecimal.ZERO;
		}

		if (saldoReceita == null) {
			saldoReceita = BigDecimal.ZERO;
		}

		return saldoReceita.subtract(saldoDespesa);
	}
}
