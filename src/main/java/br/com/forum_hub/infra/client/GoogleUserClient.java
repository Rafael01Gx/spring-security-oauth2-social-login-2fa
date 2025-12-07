package br.com.forum_hub.infra.client;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange
public interface GoogleUserClient {

    @PostExchange("/userinfo")
    Map<String,Object> informacoesUsuario(@RequestHeader("Authorization") String token);
}
