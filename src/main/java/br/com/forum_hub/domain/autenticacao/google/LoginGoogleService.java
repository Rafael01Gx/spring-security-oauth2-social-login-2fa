package br.com.forum_hub.domain.autenticacao.google;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import br.com.forum_hub.infra.client.GoogleAuthClient;
import br.com.forum_hub.infra.client.GoogleUserClient;
import br.com.forum_hub.infra.config.GoogleProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginGoogleService {
    private final GoogleProperties props;
    private final GoogleAuthClient authClient;
    private final GoogleUserClient userClient;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public LoginGoogleService(GoogleProperties props, GoogleAuthClient authClient, GoogleUserClient userClient, UsuarioService usuarioService, TokenService tokenService) {
        this.props = props;
        this.authClient = authClient;
        this.userClient = userClient;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    public URI gerarUrl(int tipoLogin) {
        var redirectUri = (tipoLogin == 2) ? props.redirectUriRegistro() : props.redirectUri();
        var scope = (tipoLogin == 2)
                ? "https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile"
                : "https://www.googleapis.com/auth/userinfo.email";
        String authUrl = String.format(
                "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&scope=%s&response_type=code&access_type=offline",
                props.clientId(),
                redirectUri,
                scope
        );
        return URI.create(authUrl);
    }


    public DadosToken autenticar(String code) {
        var tokens = obterToken(code, props.redirectUri(),"authorization_code");

        IO.println(tokens);

        var idToken = tokens.get("id_token").toString();

        var email = extractEmailByIdToken(idToken);

        Usuario usuario = usuarioService.findByEmailIgnoreCaseAndVerificadoTrue(email).orElseThrow();

        return dadosToken(usuario);
    }

    public DadosToken registrar(String code) {
        var tokens = obterToken(code, props.redirectUriRegistro(),"authorization_code");
        var token = tokens.get("access_token").toString();
        var userInfo= userClient.informacoesUsuario("Bearer" + token);

        var senha = UUID.randomUUID().toString();
        Usuario user =  usuarioService.cadastrarVerificado(new DadosCadastroUsuario(userInfo.email(),senha, userInfo.name(), userInfo.email(),null,null));
        return dadosToken(user);
    }

    private Map<String,Object> obterToken(String code, String redirect_uri, String grant_type) {
        var body = Map.of(
                "code", code,
                "client_id", props.clientId(),
                "client_secret", props.clientSecret(),
                "redirect_uri", redirect_uri,
                "grant_type", grant_type
        );
        return authClient.trocarCodePorToken(body);
    }

    private String extractEmailByIdToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("email").asString();
    }


    public String renovarAccessToken(String refreshToken) {

        return obterToken(refreshToken,
                props.redirectUri(),
                "refresh_token")
                .get("access_token")
                .toString();

    }

    private DadosToken dadosToken(Usuario usuario) {
        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return new DadosToken(tokenAcesso, refreshToken);
    }



}
