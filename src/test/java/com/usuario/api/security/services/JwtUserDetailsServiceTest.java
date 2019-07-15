package com.usuario.api.security.services;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.usuario.api.enteties.Usuario;
import com.usuario.api.enums.PerfilEnum;
import com.usuario.api.repositories.util.ConstantesUtil;
import com.usuario.api.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JwtUserDetailsServiceTest {

	@MockBean
	private UsuarioService usuarioService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void loadUser() {
		Usuario usuario = new Usuario();
		usuario.setEmail(ConstantesUtil.Usuario.EMAIL_VALIDO);
		usuario.setSenha(ConstantesUtil.Usuario.SENHA_VALIDA);
		usuario.setPerfil(PerfilEnum.ADMIN);
		BDDMockito.given(this.usuarioService.findByEmail(Mockito.anyString())).willReturn(Optional.of(usuario));
		UserDetails user  = this.userDetailsService.loadUserByUsername(Mockito.anyString());
		assertNotNull(user);
		
		thrown.expect(UsernameNotFoundException.class);
		BDDMockito.given(this.usuarioService.findByEmail(Mockito.anyString())).willReturn(Optional.empty());
		user  = this.userDetailsService.loadUserByUsername(Mockito.anyString());
	}
	
}
