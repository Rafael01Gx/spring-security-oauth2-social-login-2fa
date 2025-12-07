package br.com.forum_hub.infra.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange
public interface GoogleAuthClient {

    @PostExchange("/token")
    Map<String,Object> trocarCodePorToken(@RequestBody Map<String, String> body);

}
