package com.hordealianca.api.exception;

/**
 * Classe que contém as mensagens de erro da aplicação
 */
public class RepositorioNaoInicializadoException extends RuntimeException {
    public RepositorioNaoInicializadoException(String mensagem) {super(mensagem); }
}
