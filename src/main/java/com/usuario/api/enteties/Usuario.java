package com.usuario.api.enteties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.usuario.api.dtos.UsuarioDTO;
import com.usuario.api.enums.PerfilEnum;
import com.usuario.api.enums.SituacaoUsuarioEnum;

/**
 * 
 * Entidade Usuario
 *
 * @author Tulio Viglione
 */
@Entity
@Table(name = "usuario")
public class Usuario extends Generics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", length = 50)
	private String nome;

	@Column(name = "SOBRENOME", length = 50)
	private String sobrenome;

	@Column(name = "LOGIN", length = 100, nullable = false, unique = true)
	private String login;

	@Column(name = "EMAIL", length = 150, nullable = false, unique = true)
	private String email;

	@Column(name = "SENHA", length = 255, nullable = false)
	private String senha;

	@Enumerated(EnumType.STRING)
	@Column(name = "PERFIL", nullable = false)
	private PerfilEnum perfil;

	@Column(name = "SITUACAO", length = 9, nullable = false)
	private SituacaoUsuarioEnum situacao;

	public Usuario() {
		// construtor padrão
	}
	
	public Usuario(UsuarioDTO dto) {
		super();
		setId(dto.getId());
		this.nome = dto.getNome();
		this.sobrenome = dto.getSobrenome();
		this.login = dto.getLogin();
		this.email = dto.getEmail();
		this.senha = dto.getSenha();
		this.perfil = dto.getPerfil();
		this.situacao = dto.getSituacao();
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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