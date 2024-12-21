package com.hordealianca.api.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FieldMessage> errors = new ArrayList<>();

    /**
     * Construtor
     *
     * @param timestamp Timestamp
     * @param status    Status
     * @param error     Erro
     * @param message   Mensagem
     * @param path      Path
     */
    public ValidationError(long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    /**
     * Adiciona um erro
     *
     * @param fieldName Nome do campo
     * @param message   Mensagem de erro
     */
    public void addErrors(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
