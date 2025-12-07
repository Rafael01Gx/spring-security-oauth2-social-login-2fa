package br.com.forum_hub.infra.client;

import br.com.forum_hub.domain.autenticacao.github.DadosEmail;
import br.com.forum_hub.domain.usuario.DadosGitHubUser;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Map;

@HttpExchange("/user")
public interface GitHubUserClient {

    @GetExchange
    DadosGitHubUser buscarDadosUsuario(@RequestHeader("Authorization") String authorization);

    @GetExchange("/emails")
    List<DadosEmail> buscarEmail(@RequestHeader("Authorization") String authorization);

    @GetExchange("/repos")
    String obterRepositorios(@RequestHeader("Authorization") String authorization);
}
