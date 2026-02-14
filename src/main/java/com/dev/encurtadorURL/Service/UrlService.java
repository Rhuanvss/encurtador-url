package com.dev.encurtadorURL.Service;

import com.dev.encurtadorURL.DTO.UrlEncurtadaRequest;
import com.dev.encurtadorURL.DTO.UrlEncurtadaResponse;
import com.dev.encurtadorURL.Exception.ErroInternoException;
import com.dev.encurtadorURL.Exception.UrlExpiradaException;
import com.dev.encurtadorURL.Exception.UrlInvalidaException;
import com.dev.encurtadorURL.Exception.UrlNaoEncontradaException;
import com.dev.encurtadorURL.Model.Url;
import com.dev.encurtadorURL.Repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.regex.Pattern;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // Injeção de dependência via construtor (forma recomendada)
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // Caracteres permitidos no código encurtado (letras e números)
    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TAMANHO_MINIMO_CODIGO = 5;
    private static final int TAMANHO_MAXIMO_CODIGO = 10;
    private static final int TENTATIVAS_MAXIMAS = 10;
    private static final SecureRandom random = new SecureRandom();

    // Pattern para validar URLs
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)([\\w\\-]+\\.)+[\\w\\-]+(/[\\w\\-./?%&=]*)?$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Encurta uma URL longa
     * @param request contém a URL original a ser encurtada
     * @return UrlEncurtadaResponse com a URL encurtada completa
     */
    public UrlEncurtadaResponse encurtarUrl(UrlEncurtadaRequest request) {
        String urlOriginal = request.getUrl();

        // Validar URL
        validarUrl(urlOriginal);

        // Verificar se a URL já foi encurtada anteriormente
        Url urlExistente = urlRepository.findByUrlOriginal(urlOriginal)
                .orElse(null);

        // Se existe e não expirou, retorna a mesma URL encurtada
        if (urlExistente != null && !urlExistente.isExpired()) {
            String urlCompleta = baseUrl + "/" + urlExistente.getUrlEncurtada();
            return new UrlEncurtadaResponse(urlCompleta);
        }

        // Gerar código único
        String codigoEncurtado = gerarCodigoUnico();

        // Criar e salvar a nova URL
        Url novaUrl = new Url();
        novaUrl.setUrlOriginal(urlOriginal);
        novaUrl.setUrlEncurtada(codigoEncurtado);

        try {
            urlRepository.save(novaUrl);
        } catch (Exception e) {
            throw new ErroInternoException("Erro ao salvar URL no banco de dados", e);
        }

        // Retornar URL completa
        String urlCompleta = baseUrl + "/" + codigoEncurtado;
        return new UrlEncurtadaResponse(urlCompleta);
    }

    /**
     * Busca a URL original a partir do código encurtado
     * @param codigoEncurtado o código da URL encurtada
     * @return a URL original para redirecionamento
     */
    public String buscarUrlOriginal(String codigoEncurtado) {
        // Buscar URL no banco de dados
        Url url = urlRepository.findByUrlEncurtada(codigoEncurtado)
                .orElseThrow(() -> new UrlNaoEncontradaException(codigoEncurtado));

        // Verificar se a URL expirou
        if (url.isExpired()) {
            throw new UrlExpiradaException(codigoEncurtado);
        }

        return url.getUrlOriginal();
    }

    /**
     * Valida se a URL está no formato correto
     * @param url a URL a ser validada
     */
    private void validarUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new UrlInvalidaException("A URL não pode estar vazia");
        }

        if (!URL_PATTERN.matcher(url).matches()) {
            throw new UrlInvalidaException(url, "formato de URL inválido");
        }

        if (url.length() > 2000) {
            throw new UrlInvalidaException(url, "URL muito longa (máximo 2000 caracteres)");
        }
    }

    /**
     * Gera um código único para a URL encurtada
     * Código terá entre 5 e 10 caracteres alfanuméricos
     * @return código único gerado
     */
    private String gerarCodigoUnico() {
        int tentativas = 0;

        while (tentativas < TENTATIVAS_MAXIMAS) {
            // Gerar código aleatório com tamanho entre 5 e 10 caracteres
            int tamanho = TAMANHO_MINIMO_CODIGO + random.nextInt(TAMANHO_MAXIMO_CODIGO - TAMANHO_MINIMO_CODIGO + 1);
            String codigo = gerarCodigoAleatorio(tamanho);

            // Verificar se o código já existe
            if (!urlRepository.existsByUrlEncurtada(codigo)) {
                return codigo;
            }

            tentativas++;
        }

        // Se não conseguir gerar código único após as tentativas
        throw new ErroInternoException("Não foi possível gerar um código único após " + TENTATIVAS_MAXIMAS + " tentativas");
    }

    /**
     * Gera um código aleatório com o tamanho especificado
     * @param tamanho número de caracteres do código
     * @return código aleatório gerado
     */
    private String gerarCodigoAleatorio(int tamanho) {
        StringBuilder codigo = new StringBuilder(tamanho);

        for (int i = 0; i < tamanho; i++) {
            int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
            codigo.append(CARACTERES_PERMITIDOS.charAt(indice));
        }

        return codigo.toString();
    }
}
