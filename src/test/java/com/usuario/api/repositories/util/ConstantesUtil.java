package com.usuario.api.repositories.util;

public class ConstantesUtil {

	public final class Url{
		public static final String CADASTRA_USUARIO = "/api/usuarios";
		public static final String AUTENTICACAO = "/auth/";
	}
	
	public final class Usuario{
		public static final String SENHA_VALIDA = "12345678";
		public static final String SENHA_MENOR= "1234567";
		public static final String SENHA_MAIOR = "12345678901234567";
		public static final String LOGIN = "login";
		public static final String LOGIN_AUX = "loginAux";
		public static final String EMAIL_VALIDO = "teste@email.com";
		public static final String EMAIL_INVALIDO = "teste.com";
	}
}
