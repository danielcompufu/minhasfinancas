package com.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.minhasfinancas.model.entity.Usuario;
import com.minhasfinancas.repository.UsuarioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		
		Usuario usuario = Usuario.builder()
				.nome("nome")
				.email("usuario@email.com")
				.senha("1234")
				.build();
		
		repository.save(usuario);
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		Assertions.assertThat(result).isTrue();
	}
}
