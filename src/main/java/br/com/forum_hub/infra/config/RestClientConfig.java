package br.com.forum_hub.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties({
        GitHubProperties.class,
        GoogleProperties.class
})
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient githubRestClient(RestClient.Builder builder, GitHubProperties gitHubProperties) {
        return builder
                .baseUrl(gitHubProperties.apiBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient googleRestClient(RestClient.Builder builder, GoogleProperties googleProperties) {
        return builder
                .baseUrl(googleProperties.apiBaseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
