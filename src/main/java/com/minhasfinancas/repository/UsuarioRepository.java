package com.minhasfinancas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmail(String email);

}