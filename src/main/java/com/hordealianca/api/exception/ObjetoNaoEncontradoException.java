package com.hordealianca.api.exception;

/**
 * Classe que representa a exceção de conta não encontrada
 */
public class ObjetoNaoEncontradoException extends RuntimeException {
    public ObjetoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
