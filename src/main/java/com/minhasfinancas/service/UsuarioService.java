package com.minhasfinancas.service;

import com.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario auntenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void validarEmail(String email);
}
