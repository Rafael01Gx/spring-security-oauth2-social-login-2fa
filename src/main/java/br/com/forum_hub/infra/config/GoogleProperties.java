package br.com.forum_hub.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google")
public record GoogleProperties(
        String apiBaseUrl,
        String clientId,
        String clientSecret,
        String redirectUri
) {
}
