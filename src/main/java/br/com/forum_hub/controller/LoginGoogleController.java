package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosToken;
import br.com.forum_hub.domain.autenticacao.google.LoginGoogleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login/google")
public class LoginGoogleController {
    private final LoginGoogleService loginGoogleService;

    public LoginGoogleController(LoginGoogleService loginGoogleService) {
        this.loginGoogleService = loginGoogleService;
    }

    @GetMapping
    public ResponseEntity<Void> redirecionarGoogle(){
        return ResponseEntity.status(HttpStatus.FOUND).location(loginGoogleService.gerarUrl(1)).build();
    }

    @GetMapping("/autorizado")
    public ResponseEntity<DadosToken> loginGoogle(@RequestParam String code){
        return ResponseEntity.ok().body(loginGoogleService.autenticar(code));
    }

    @GetMapping("/registro")
    public ResponseEntity<Void> registroGoogle(){
        return ResponseEntity.status(HttpStatus.FOUND).location(loginGoogleService.gerarUrl(2)).build();
    }

    @GetMapping("/registro-autorizado")
    public ResponseEntity<DadosToken> registroUsuarioGoogle(@RequestParam String code){
        return ResponseEntity.ok().body(loginGoogleService.registrar(code));
    }


}
