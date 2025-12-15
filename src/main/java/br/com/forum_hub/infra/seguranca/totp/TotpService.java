package br.com.forum_hub.infra.seguranca.totp;

import br.com.forum_hub.domain.usuario.Usuario;
import com.atlassian.onetime.core.TOTPGenerator;
import com.atlassian.onetime.model.TOTPSecret;
import com.atlassian.onetime.service.RandomSecretProvider;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    public String gerarSecret(){
        return new RandomSecretProvider().generateSecret().getBase32Encoded();
    }

    public String gerarQrCode(Usuario usuario){
        var issuer = "Fórum Hub";
      return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
              issuer,usuario.getUsername(),usuario.getSecret(),issuer);
    }

    public Boolean verificarCodigo(String codigo, Usuario logado) {
        var decodedSecret = TOTPSecret.Companion.fromBase32EncodedString(logado.getSecret());
        var codigoAplicacao = new TOTPGenerator().generateCurrent(decodedSecret).getValue();
        return codigo.equals(codigoAplicacao);
    }
}
