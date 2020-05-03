package com.minhasfinancas.api.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.minhasfinancas.exception.RegraNegocioException;
import com.minhasfinancas.model.entity.Usuario;
import com.minhasfinancas.service.LancamentoService;
import com.minhasfinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Usuario cadastraUsuario(@Valid @RequestBody Usuario usuario) {
		return usuarioService.salvar(usuario);	
	}
	
	@PostMapping(value = "/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Usuario autenticarUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioAutenticado = usuarioService.auntenticar(usuario.getEmail(), usuario.getSenha());
		return usuarioAutenticado;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Usuario> listaUsuarios(){
		return usuarioService.listar();
	}
	
	@GetMapping(value = "{id}/saldo")
	public BigDecimal obterSaldo(@PathVariable(value = "id") Long id) {
		usuarioService
				.obterPorId(id)
				.orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado para o Id informado!"));
		
		return lancamentoService.obterSaldoPorUsuario(id);
	}
}
