package com.usuario.api.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.api.dtos.UsuarioDTO;
import com.usuario.api.enteties.Usuario;
import com.usuario.api.response.Response;
import com.usuario.api.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<Response<UsuarioDTO>> adicionar(@Valid @RequestBody UsuarioDTO usuarioDto,
			BindingResult result) {

		log.debug("Cadastrando novo usuário na API");

		Response<UsuarioDTO> response = new Response<>();

		if (result.hasErrors()) {
			log.error("Erro Validação Usuário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(new UsuarioDTO(this.usuarioService.cadastraNovoUsuario(new Usuario(usuarioDto))));
		return ResponseEntity.ok(response);

	}
}
