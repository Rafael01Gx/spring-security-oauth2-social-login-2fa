package br.com.forum_hub.infra.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange
public interface GitHubAuthClient {
    @PostExchange("/login/oauth/access_token")
    Map<String, Object> trocarCodePorToken(@RequestBody Map<String, String> body);

}
