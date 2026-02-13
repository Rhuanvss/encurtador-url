package com.dev.encurtadorURL.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlEncurtadaRequest {

    @NotBlank(message = "URL não pode estar vazia")
    private String url;
}
