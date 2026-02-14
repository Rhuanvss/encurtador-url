package com.dev.encurtadorURL.Controller;

import com.dev.encurtadorURL.DTO.UrlEncurtadaRequest;
import com.dev.encurtadorURL.DTO.UrlEncurtadaResponse;
import com.dev.encurtadorURL.Service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/encurtador-url")
    public ResponseEntity<UrlEncurtadaResponse> encurtarUrl(@Valid @RequestBody UrlEncurtadaRequest request) {
        UrlEncurtadaResponse response = urlService.encurtarUrl(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{codigo}")
    public RedirectView redirecionarUrl(@PathVariable String codigo) {
        String urlOriginal = urlService.buscarUrlOriginal(codigo);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlOriginal);
        redirectView.setStatusCode(HttpStatus.FOUND); // 302 - Redirect temporário

        return redirectView;
    }
}
