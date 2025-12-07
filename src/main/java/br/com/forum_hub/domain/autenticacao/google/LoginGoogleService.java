package br.com.forum_hub.domain.autenticacao.google;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.TokenService;
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
        var idToken = tokens.get("id_token").toString();
        var refreshToken = tokens.get("refresh_token").toString();
        var email = extractEmailByIdToken(idToken);

        if (refreshToken != null) {
            // Armazene o refresh token de forma segura no banco de dados
            System.out.println("Refresh Token: " + refreshToken);
        }

        Usuario usuario = usuarioService.findByEmailIgnoreCaseAndVerificadoTrue(email).orElseThrow();

        return dadosToken(usuario);
    }

    public Map<String,Object> registrar(String code) {
        var tokens = obterToken(code, props.redirectUriRegistro(),"authorization_code");
        var token = tokens.get("access_token").toString();
    //Implementar a logica de registro ...
        return userClient.informacoesUsuario("Bearer" + token);
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
