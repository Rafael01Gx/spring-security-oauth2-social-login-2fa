package br.com.forum_hub.domain.autenticacao.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosUsuarioGoogle(
        String sub,
        String name,
        String email,
        Boolean email_verified
) {
}
