package com.usuario.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.api.dtos.UsuarioDTO;
import com.usuario.api.enteties.Usuario;
import com.usuario.api.repositories.util.ConstantesUtil;
import com.usuario.api.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;

	@Test
	public void cadastraNovoUsuario() throws Exception {
		BDDMockito.given(this.usuarioService.cadastraNovoUsuario(Mockito.any(Usuario.class))).willReturn(new Usuario());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO).content("{\"nome\": \"nome\",\"sobrenome\": \"sobrenome\",\"login\": \""+ConstantesUtil.Usuario.LOGIN+"\",\"email\": \""+ConstantesUtil.Usuario.EMAIL_VALIDO+"\",\"senha\": \""+ConstantesUtil.Usuario.SENHA_VALIDA+"\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content("{\"nome\": \"nome\",\"sobrenome\": \"sobrenome\",\"login\": \""+ConstantesUtil.Usuario.LOGIN+"\",\"email\": \"\",\"senha\": \""+ConstantesUtil.Usuario.SENHA_VALIDA+"\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content("{\"nome\": \"\",\"sobrenome\": \"\",\"login\": \"\",\"email\": \"\",\"senha\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
	}
	
}
