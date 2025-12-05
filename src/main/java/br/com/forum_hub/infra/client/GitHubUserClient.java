package br.com.forum_hub.infra.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/user")
public interface GitHubUserClient {

    @GetExchange("/")
    String buscarDadosUsuario(@RequestHeader("Authorization") String authorization);

    @GetExchange("/emails")
    String buscarEmail(@RequestHeader("Authorization") String authorization);
}
