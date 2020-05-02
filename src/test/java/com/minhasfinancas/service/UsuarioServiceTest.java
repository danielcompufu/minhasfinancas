package com.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.minhasfinancas.exception.RegraNegocioException;
import com.minhasfinancas.model.entity.Usuario;
import com.minhasfinancas.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	
	@Test
	public void deveValidarEmail() {
		repository.deleteAll();
		
		Assertions.assertDoesNotThrow(
	            ()->{
	            	service.validarEmail("email@email.com");
	            });
		
	}
	
	@Test
	public void deveLancarErroQuandoHouverEmailCadastrado() {
		repository.deleteAll();
		

		Usuario usuario = Usuario.builder()
				.nome("nome")
				.email("usuario@email.com")
				.senha("1234")
				.build();
		
		repository.save(usuario);
		
		Assertions.assertThrows(RegraNegocioException.class,
	            ()->{
	            	service.validarEmail("usuario@email.com");
	            });
		
	}

}
