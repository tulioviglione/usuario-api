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
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO).content(obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content(obterJsonInvalido())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content(obterJsonVazio())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
	}
	
	private String obterJsonVazio() throws JsonProcessingException {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setEmail("");
		dto.setSenha(ConstantesUtil.Usuario.SENHA_MENOR);
		dto.setLogin("");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	private String obterJsonInvalido() throws JsonProcessingException {
		UsuarioDTO dto = new UsuarioDTO();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setEmail(ConstantesUtil.Usuario.EMAIL_VALIDO);
		dto.setSenha(ConstantesUtil.Usuario.SENHA_VALIDA);
		dto.setLogin(ConstantesUtil.Usuario.LOGIN);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
}
