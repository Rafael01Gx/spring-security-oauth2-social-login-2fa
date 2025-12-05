package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.github.LoginGitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/login/github")
public class LoginGitHubController {

    private final LoginGitHubService loginGitHubService;


    public LoginGitHubController(LoginGitHubService loginGitHubService) {
        this.loginGitHubService = loginGitHubService;

    }

//    @GetMapping
//    public ResponseEntity<Void> redirecionarGitHub() {
//        var headers = new HttpHeaders();
//        var url = loginGitHubService.gerarUrl();
//        headers.setLocation(URI.create(url));
//
//        return new ResponseEntity<>(headers, HttpStatus.FOUND);
//    }



    @GetMapping
    public ResponseEntity<Void> redirecionarGitHub() {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(loginGitHubService.gerarUrl()))
                .build();
    }

    @GetMapping("/autorizado")
    public ResponseEntity<DadosToken> autenticarUsuarioOAuth(@RequestParam String code) {

    return ResponseEntity.ok().body(loginGitHubService.autenticar(code));
    };

}
