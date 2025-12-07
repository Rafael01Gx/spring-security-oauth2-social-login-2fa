package br.com.forum_hub.domain.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosGitHubUser(
        String name,
        String login
) {
}
