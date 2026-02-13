package com.dev.encurtadorURL.Exception;

/**
 * Exception lançada para erros internos do servidor
 */
public class ErroInternoException extends RuntimeException {

    public ErroInternoException(String mensagem) {
        super(mensagem);
    }

    public ErroInternoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
