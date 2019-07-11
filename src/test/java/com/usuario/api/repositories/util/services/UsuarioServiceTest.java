package com.usuario.api.repositories.util.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.usuario.api.enteties.Usuario;
import com.usuario.api.repositories.UsuarioRepository;
import com.usuario.api.repositories.util.ConstantesUtil;
import com.usuario.api.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.usuarioRepository.save(Mockito.any(Usuario.class))).willReturn(new Usuario());
		BDDMockito.given(this.usuarioRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new Usuario()));
		BDDMockito.given(this.usuarioRepository.findByLogin(Mockito.anyString())).willReturn(Optional.of(new Usuario()));
		BDDMockito.given(this.usuarioRepository.findAll(Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Usuario>(new ArrayList<Usuario>()));
	}

	@Test
	public void buscaUsuario() {
		assertTrue(this.usuarioService.findByEmail(ConstantesUtil.Usuario.EMAIL_VALIDO).isPresent());
		assertTrue(this.usuarioService.findByLogin(ConstantesUtil.Usuario.LOGIN).isPresent());
		assertTrue(this.usuarioService.isLoginExist(ConstantesUtil.Usuario.LOGIN));
		assertTrue(this.usuarioService.isEmailExist(ConstantesUtil.Usuario.EMAIL_VALIDO));
	}

	@Test
	public void TestCadastroNovoUsuario() throws ParseException {
		Usuario usuario = new Usuario();
		usuario.setEmail(ConstantesUtil.Usuario.EMAIL_VALIDO);
		usuario.setLogin(ConstantesUtil.Usuario.LOGIN);
		usuario.setSenha(ConstantesUtil.Usuario.SENHA_VALIDA);
		usuario = this.usuarioService.cadastraNovoUsuario(usuario);
		assertNotNull(usuario);
	}

	@Test
	public void TestPersistencia() {
		Usuario usuario = this.usuarioService.persisteUsuario(new Usuario());

		assertNotNull(usuario);
	}

	@Test
	public void TestBuscaUsuariosCadastrados() {
		Page<Usuario> usuario = this.usuarioService.buscaTodosUsuarios(PageRequest.of(0, 10));

		assertNotNull(usuario);
	}

}