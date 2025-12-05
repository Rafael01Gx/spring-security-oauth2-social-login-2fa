package br.com.forum_hub.infra.config;

import br.com.forum_hub.infra.client.GitHubAuthClient;
import br.com.forum_hub.infra.client.GitHubUserClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
    public RestClient gitHubAuthRestClient(RestClient.Builder builder, GitHubProperties gitHubProperties) {
        return builder
                .baseUrl(gitHubProperties.baseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestClient gitHubUserRestClient(RestClient.Builder builder, GitHubProperties gitHubProperties) {
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

    @Bean
    RestClientAdapter gitHubAuthAdapter(RestClient gitHubAuthRestClient) {
        return RestClientAdapter.create(gitHubAuthRestClient);
    }

    @Bean
    RestClientAdapter gitHubUserAdapter(RestClient gitHubUserRestClient) {
        return RestClientAdapter.create(gitHubUserRestClient);
    }

    @Bean
    public GitHubAuthClient gitHubAuthClient(RestClientAdapter gitHubAuthAdapter) {
        return HttpServiceProxyFactory
                .builderFor(gitHubAuthAdapter)
                .build()
                .createClient(GitHubAuthClient.class);
    }

    @Bean
    public GitHubUserClient gitHubUserClient(RestClientAdapter gitHubUserAdapter) {
        return HttpServiceProxyFactory
                .builderFor(gitHubUserAdapter)
                .build()
                .createClient(GitHubUserClient.class);
    }


}
