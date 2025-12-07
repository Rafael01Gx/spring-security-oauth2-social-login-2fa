package br.com.forum_hub.domain.autenticacao.github;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import br.com.forum_hub.domain.usuario.DadosGitHubUser;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import br.com.forum_hub.infra.client.GitHubAuthClient;
import br.com.forum_hub.infra.client.GitHubUserClient;
import br.com.forum_hub.infra.config.GitHubProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class LoginGitHubService {

    private final GitHubProperties props;
    private final GitHubAuthClient authClient;
    private final GitHubUserClient userClient;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;


    public LoginGitHubService(GitHubAuthClient client, GitHubUserClient userClient, GitHubProperties props, UsuarioService usuarioService, TokenService tokenService) {
        this.props = props;
        this.authClient = client;
        this.userClient = userClient;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    public String gerarUrl() {

        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + props.clientId() +
                "&redirect_uri=" + props.redirectUri() +
                "&scope=user:email,public_repo";
    }

    public String gerarUrlRegistro() {
        return "https://github.com/login/oauth/authorize" +
                "?client_id=" + props.clientId() +
                "&redirect_uri=" + props.redirectUriRegistro() +
                "&scope=read:user,user:email";
    }

    public DadosToken autenticar(String code) {
        var token = obterToken(code, props.redirectUri());
        var email = obterEmail(token);

        var usuario = usuarioService.findByEmailIgnoreCaseAndVerificadoTrue(email).orElseThrow();

        return dadosToken(usuario);
    }

    private String obterEmail(String token) {
        var emails = userClient.buscarEmail("Bearer " + token);
        //var repositorios = userClient.obterRepositorios("Bearer " + token);
        for (DadosEmail d : emails) {
            if (d.primary() && d.verified()) {
                return d.email();
            }
        }

        return null;
    }

    private String obterToken(String code, String redirect_uri) {
        var body = Map.of(
                "code", code,
                "client_id", props.clientId(),
                "client_secret", props.clientSecret(),
                "redirect_uri", redirect_uri
        );

        var response = authClient.trocarCodePorToken(body);
        return response.get("access_token").toString();
    }


    public DadosToken registrarUsuario(String code) {
        var token = obterToken(code, props.redirectUriRegistro());
        DadosGitHubUser dados = userClient.buscarDadosUsuario("Bearer " + token);
        String email = obterEmail(token);
        var senha = UUID.randomUUID().toString();
        Usuario user =  usuarioService.cadastrarVerificado(new DadosCadastroUsuario(email,senha, dados.name(), dados.login(),null,null));
        return dadosToken(user);
    }

    private DadosToken dadosToken(Usuario usuario) {
        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return new DadosToken(tokenAcesso, refreshToken);
    }


}
