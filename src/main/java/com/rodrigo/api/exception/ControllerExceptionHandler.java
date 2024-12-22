package com.rodrigo.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Trata exceção de UsuarioNaoEncontradoException
     *
     * @param ex      Exceção
     * @param request Requisição
     * @return ResponseEntity
     */
    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<StandardError> objetoNaoEncontradoException(ObjetoNaoEncontradoException ex,
                                                                       HttpServletRequest request) {
        logger.error("ObjetoNaoEncontradoException: {}", ex.getMessage());
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Objeto não Encontrado",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata exceção de ViolacaoIntegridadeDadosException
     *
     * @param ex      Exceção
     * @param request Requisição
     * @return ResponseEntity
     */
    @ExceptionHandler(ViolacaoIntegridadeDadosException.class)
    public ResponseEntity<StandardError> violacaoIntegridadeDadosException(ViolacaoIntegridadeDadosException ex,
                                                                           HttpServletRequest request) {
        logger.error("ViolacaoIntegridadeDadosException: {}", ex.getMessage());
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação de dados",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceção de MethodArgumentNotValidException
     *
     * @param ex      Exceção
     * @param request Requisição
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage());
        ValidationError errors = new ValidationError(System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Erro na validação dos campos",
                request.getRequestURI());

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.addErrors(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Trata exceção de Exception
     *
     * @param ex      Exceção
     * @param request Requisição
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> globalExceptionHandler(Exception ex, WebRequest request) {
        logger.error("Exception: {}", ex.getMessage());
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno",
                ex.getMessage(),
                request.getContextPath());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Trata exceção de RepositorioNaoInicializadoException
     *
     * @param ex      Exceção
     * @param request Requisição
     * @return ResponseEntity
     */
    @ExceptionHandler(RepositorioNaoInicializadoException.class)
    public ResponseEntity<StandardError> repositorioNaoInicializadoException(RepositorioNaoInicializadoException ex,
                                                                             HttpServletRequest request) {
        logger.error("RepositorioNaoInicializadoException: {}", ex.getMessage());
        StandardError error = new StandardError(
                System.currentTimeMillis(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Repositório não inicializado",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
