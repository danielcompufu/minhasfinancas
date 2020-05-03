package com.minhasfinancas.service;

import java.util.List;
import java.util.Optional;

import com.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario auntenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
	
	List<Usuario> listar();
	
	Optional<Usuario> obterPorId(Long id);
}
