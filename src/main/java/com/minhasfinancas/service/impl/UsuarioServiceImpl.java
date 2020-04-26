package com.minhasfinancas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minhasfinancas.exception.RegraNegocioException;
import com.minhasfinancas.model.entity.Usuario;
import com.minhasfinancas.repository.UsuarioRepository;
import com.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario auntenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		if(usuarioRepository.existsByEmail(email)) {
			throw new RegraNegocioException("Ja existe um usuario cadastrado com este email!");
		}
		
	}

	
}
