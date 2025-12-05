package br.com.forum_hub.domain.autenticacao.github;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioRepository;
import br.com.forum_hub.infra.client.GitHubAuthClient;
import br.com.forum_hub.infra.client.GitHubUserClient;
import br.com.forum_hub.infra.config.GitHubProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginGitHubService {

    private final GitHubProperties props;
    private final GitHubAuthClient authClient;
    private final GitHubUserClient userClient;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;


    public LoginGitHubService(GitHubAuthClient client, GitHubUserClient userClient , GitHubProperties props, UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.props = props;
        this.authClient = client;
        this.userClient = userClient;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    public String gerarUrl(){

        return "https://github.com/login/oauth/authorize" +
                "?client_id="+props.clientId() +
                "&redirect_uri="+props.redirectUri() +
                "&scope=read:user,user:email";
    }

    public DadosToken autenticar(String code) {
        var email = obterEmail(code);

        var usuario = usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(email).orElseThrow();
        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return new DadosToken(tokenAcesso, refreshToken);

    }

    private String obterEmail(String code) {
        String token = obterToken(code);
        var emails = userClient.buscarEmail("Bearer " + token);

        for (DadosEmail d : emails) {
            if(d.primary() && d.verified()){
                return d.email();
            }
        }

        return null;
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
