package com.usuario.api.security.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.usuario.api.repositories.util.ConstantesUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserDetailsService usuarioService;

	@Test
	public void gerarTokenJwt() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.AUTENTICACAO)
				.content("{\"email\": \"\",\"senha\": \"\"}").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").isNotEmpty());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.AUTENTICACAO)
				.content("{\"email\": \"" + ConstantesUtil.Usuario.EMAIL_VALIDO + "\",\"senha\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isNotEmpty());
		
		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.AUTENTICACAO)
				.content("{\"email\": \"\",\"senha\": \"" + ConstantesUtil.Usuario.SENHA_VALIDA + "\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isNotEmpty());
		
	}
}
