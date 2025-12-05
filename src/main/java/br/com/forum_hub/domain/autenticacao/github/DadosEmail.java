package br.com.forum_hub.domain.autenticacao.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEmail(
        String email,
        Boolean primary,
        Boolean verified,
        String visibility
) {
}
