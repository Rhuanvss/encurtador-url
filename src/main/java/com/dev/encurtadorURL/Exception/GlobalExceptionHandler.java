package com.dev.encurtadorURL.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global para capturar e tratar exceções da aplicação
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções de URL não encontrada
     * Retorna status 404 (Not Found)
     */
    @ExceptionHandler(UrlNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleUrlNaoEncontrada(
            UrlNaoEncontradaException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata exceções de URL expirada
     * Retorna status 410 (Gone)
     */
    @ExceptionHandler(UrlExpiradaException.class)
    public ResponseEntity<ErrorResponse> handleUrlExpirada(
            UrlExpiradaException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.GONE.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.GONE).body(error);
    }

    /**
     * Trata exceções de URL inválida
     * Retorna status 400 (Bad Request)
     */
    @ExceptionHandler(UrlInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleUrlInvalida(
            UrlInvalidaException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções de erro interno
     * Retorna status 500 (Internal Server Error)
     */
    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<ErrorResponse> handleErroInterno(
            ErroInternoException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Trata exceções gerais não capturadas
     * Retorna status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExcecaoGeral(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor: " + ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
