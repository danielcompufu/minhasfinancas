package com.minhasfinancas.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minhasfinancas.dto.StatusLancamentoDTO;
import com.minhasfinancas.exception.RegraNegocioException;
import com.minhasfinancas.model.entity.Lancamento;
import com.minhasfinancas.model.entity.Usuario;
import com.minhasfinancas.model.enums.StatusLancamento;
import com.minhasfinancas.service.LancamentoService;
import com.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping(value = "/api/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Lancamento salvar(@Valid @RequestBody Lancamento lancamento) {
		Usuario usuario = usuarioService
				.obterPorId(lancamento.getUsuario().getId())
				.orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado para o Id informado!"));
		lancamento.setUsuario(usuario);
		return lancamentoService.salvar(lancamento);
	}
	
	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Lancamento atualizar(@PathVariable(value = "id") Long id, @Valid @RequestBody Lancamento lancamento) {
		Usuario usuario = usuarioService
				.obterPorId(lancamento.getUsuario().getId())
				.orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado para o Id informado!"));
		
		lancamentoService.obterPorId(id)
				.orElseThrow(() -> new RegraNegocioException("Lancamento nao encontrado para o Id informado!"));
		
		lancamento.setUsuario(usuario);
		lancamento.setId(id);
		return lancamentoService.atualizar(lancamento);
	}
	
	
	@PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Lancamento atualizarStatus(@PathVariable(value = "id") Long id, @RequestBody StatusLancamentoDTO status) {
		Lancamento lancamentoEntity = lancamentoService.obterPorId(id)
				.orElseThrow(() -> new RegraNegocioException("Lancamento nao encontrado para o Id informado!"));
		
	    return lancamentoService.atualizarStatus(lancamentoEntity, StatusLancamento.valueOf(status.getStatus()));
	}
	
	@DeleteMapping(value = "{id}")
	public Lancamento deletar(@PathVariable(value = "id") Long id) {
		Lancamento lancamento = lancamentoService.obterPorId(id)
				.orElseThrow(() -> new RegraNegocioException("Lancamento nao encontrado para o Id informado!"));
		
		lancamentoService.deletar(lancamento);
		return lancamento;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Lancamento> buscar(
			@RequestParam(name = "descricao", required = false) String descricao,
			@RequestParam(name = "ano", required = false) Integer ano,
			@RequestParam(name = "mes", required = false) Integer mes,
			@RequestParam(name = "usuario") Long idUsuario
			){
		Usuario usuario = usuarioService.obterPorId(idUsuario)
				.orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado para o Id informado!"));
		
		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setAno(ano);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setUsuario(usuario);
		
		return lancamentoService.buscar(lancamentoFiltro); 
	}
	
	@GetMapping("{id}")
	public Lancamento buscarPorId(@PathVariable("id") long id) {
		return lancamentoService.obterPorId(id)
			.orElseThrow(() -> new RegraNegocioException("Lancamento nao encontrado para o Id informado!"));
	}
	
}
