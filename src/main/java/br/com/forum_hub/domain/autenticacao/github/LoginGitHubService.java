package br.com.forum_hub.domain.autenticacao.github;

import br.com.forum_hub.infra.config.GitHubProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGitHubService {

    private final GitHubProperties props;
    private final RestClient client;


    public LoginGitHubService(@Qualifier("githubRestClient") RestClient client, GitHubProperties props) {
        this.props = props;
        this.client = client;
    }

    public String gerarUrl(){

        return "https://github.com/login/oauth/authorize" +
                "?client_id="+props.clientId() +
                "&redirect_uri="+props.redirectUri() +
                "&scope=read:user,user:email";
    }

    public String obterToken(String code) {
        return  client.post()
                .uri("/login/oauth/access_token")
                .body(Map.of(
                        "code", code,
                        "client_id",props.clientId(),
                        "client_secret",props.clientSecret(),
                        "redirect_uri",props.redirectUri()))
                .retrieve()
                .body(Map.class).get("access_token").toString();

    }
}
