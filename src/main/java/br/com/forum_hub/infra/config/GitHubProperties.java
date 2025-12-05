package br.com.forum_hub.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public record GitHubProperties(
        String apiBaseUrl,
        String baseUrl,
        String clientId,
        String clientSecret,
        String redirectUri
) {
}
