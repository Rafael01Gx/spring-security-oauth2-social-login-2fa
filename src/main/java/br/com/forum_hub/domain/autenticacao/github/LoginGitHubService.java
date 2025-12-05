package br.com.forum_hub.domain.autenticacao.github;

import br.com.forum_hub.infra.client.GitHubAuthClient;
import br.com.forum_hub.infra.client.GitHubUserClient;
import br.com.forum_hub.infra.config.GitHubProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginGitHubService {

    private final GitHubProperties props;
    private final GitHubAuthClient authClient;
    private final GitHubUserClient userClient;


    public LoginGitHubService(GitHubAuthClient client,GitHubUserClient userClient ,GitHubProperties props) {
        this.props = props;
        this.authClient = client;
        this.userClient = userClient;
    }

    public String gerarUrl(){

        return "https://github.com/login/oauth/authorize" +
                "?client_id="+props.clientId() +
                "&redirect_uri="+props.redirectUri() +
                "&scope=read:user,user:email";
    }

    public String obterEmail(String code) {
        String token = obterToken(code);

        return userClient.buscarEmail("Bearer " + token);
    }

    private String obterToken(String code) {
        var body = Map.of(
                "code", code,
                "client_id", props.clientId(),
                "client_secret", props.clientSecret(),
                "redirect_uri", props.redirectUri()
        );
        var response = authClient.trocarCodePorToken(body);
        return response.get("access_token").toString();
    }

}
