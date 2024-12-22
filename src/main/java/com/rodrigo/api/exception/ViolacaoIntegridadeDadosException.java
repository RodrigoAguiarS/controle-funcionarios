package com.rodrigo.api.exception;

/**
 * Classe que contém as mensagens de erro da aplicação
 */
public class ViolacaoIntegridadeDadosException extends RuntimeException {
    public ViolacaoIntegridadeDadosException(String mensagem) {super(mensagem); }
}
