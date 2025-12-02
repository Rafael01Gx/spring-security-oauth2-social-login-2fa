package br.com.forum_hub.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GitHubProperties.class,
        GoogleProperties.class})
public class AppConfig {
}
