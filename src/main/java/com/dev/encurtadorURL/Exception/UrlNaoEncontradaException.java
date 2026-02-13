package com.dev.encurtadorURL.Exception;

/**
 * Exception lançada quando uma URL encurtada não é encontrada no banco de dados
 */
public class UrlNaoEncontradaException extends RuntimeException {

    public UrlNaoEncontradaException(String urlEncurtada) {
        super("URL encurtada não encontrada: " + urlEncurtada);
    }

    public UrlNaoEncontradaException(String urlEncurtada, String mensagemAdicional) {
        super("URL encurtada não encontrada: " + urlEncurtada + ". " + mensagemAdicional);
    }
}
