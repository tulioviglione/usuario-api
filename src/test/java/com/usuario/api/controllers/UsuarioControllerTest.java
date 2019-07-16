package com.usuario.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.usuario.api.enteties.Usuario;
import com.usuario.api.services.UsuarioService;
import com.usuario.api.util.ConstantesUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;
	
	@Before
	public void setUp() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNome("nome");
		usuario.setSobrenome("sobrenome");
		usuario.setEmail("mail@mail.com");
		usuario.setLogin("login");
		usuario.setSenha("12345678");
		
		List<Usuario> users = new ArrayList<>();
		users.add(usuario);
		
		BDDMockito.given(this.usuarioService.buscaTodosUsuarios(Mockito.any(PageRequest.class)))
		.willReturn(new PageImpl<Usuario>(users));
	}
	
	@Test
	public void cadastraNovoUsuario() throws Exception {
		BDDMockito.given(this.usuarioService.cadastraNovoUsuario(Mockito.any(Usuario.class))).willReturn(new Usuario());

		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content("{\"nome\": \"nome\",\"sobrenome\": \"sobrenome\",\"login\": \"" + ConstantesUtil.Usuario.LOGIN
						+ "\",\"email\": \"" + ConstantesUtil.Usuario.EMAIL_VALIDO + "\",\"senha\": \""
						+ ConstantesUtil.Usuario.SENHA_VALIDA + "\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content("{\"nome\": \"nome\",\"sobrenome\": \"sobrenome\",\"login\": \"" + ConstantesUtil.Usuario.LOGIN
						+ "\",\"email\": \"\",\"senha\": \"" + ConstantesUtil.Usuario.SENHA_VALIDA + "\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isNotEmpty());

		mvc.perform(MockMvcRequestBuilders.post(ConstantesUtil.Url.CADASTRA_USUARIO)
				.content("{\"nome\": \"\",\"sobrenome\": \"\",\"login\": \"\",\"email\": \"\",\"senha\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isNotEmpty());
	}

	@Test
	public void testValidaExistenciaEmail() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get(ConstantesUtil.Url.VALIDA_EXISTENCIA_EMAIL + ConstantesUtil.Usuario.EMAIL_VALIDO)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.errors").isEmpty()).andExpect(jsonPath("$.data").isNotEmpty());

	}

	@Test
	public void testValidaExistenciaLogin() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get(ConstantesUtil.Url.VALIDA_EXISTENCIA_LOGIN + ConstantesUtil.Usuario.LOGIN)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.errors").isEmpty())
				.andExpect(jsonPath("$.data").isNotEmpty());
	}

	@Test
	@WithMockUser // anotação para gerar um usuário e passar pela autenticação
	public void buscaTodosUsuarios() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(ConstantesUtil.Url.BUSCA_LISTA_TODOS_USUARIOS)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void buscaTodosUsuariosSemToken() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(ConstantesUtil.Url.BUSCA_LISTA_TODOS_USUARIOS)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
	}
}
