package com.dev.encurtadorURL.Exception;

/**
 * Exception lançada quando uma URL fornecida é inválida
 */
public class UrlInvalidaException extends RuntimeException {

    public UrlInvalidaException(String mensagem) {
        super(mensagem);
    }

    public UrlInvalidaException(String url, String motivo) {
        super("URL inválida: " + url + ". Motivo: " + motivo);
    }
}
