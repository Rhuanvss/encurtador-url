package com.dev.encurtadorURL.Exception;

/**
 * Exception lançada quando uma URL encurtada está expirada
 */
public class UrlExpiradaException extends RuntimeException {

    public UrlExpiradaException(String urlEncurtada) {
        super("A URL encurtada expirou: " + urlEncurtada);
    }
}
