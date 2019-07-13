package com.usuario.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.usuario.api.enums.PerfilEnum;
import com.usuario.api.enums.SituacaoUsuarioEnum;

public class UsuarioDTO {

	private Long id;
	private String nome;
	private String sobrenome;
	private String login;
	private String email;
	private String senha;
	private PerfilEnum perfil;
	private SituacaoUsuarioEnum situacao;

	
	public UsuarioDTO(Long id, String nome, String sobrenome, String login, String email, PerfilEnum perfil,
			SituacaoUsuarioEnum situacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.login = login;
		this.email = email;
		this.perfil = perfil;
		this.situacao = situacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	@NotNull(message = "{login.nulo}")
	@Size(min = 1, message = "{login.vazio}")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotNull(message = "{email.nulo}")
	@Size(min = 1, message = "{email.vazio}")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull(message = "{senha.nulo}")
	@Size(min = 8, max = 16, message = "{senha.invalida}")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public SituacaoUsuarioEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoUsuarioEnum situacao) {
		this.situacao = situacao;
	}

}
