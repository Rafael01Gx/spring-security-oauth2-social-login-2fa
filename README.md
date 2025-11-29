<div  align="center">
<h1>Java e Spring Security: <br/>
 Login com GitHub, Google e Autenticação de 2 Fatores</h1> 
</div>


<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/OAuth2-3D3D3D?style=for-the-badge&logo=oauth&logoColor=white" />
  <img src="https://img.shields.io/badge/OpenID%20Connect-3D3D3D?style=for-the-badge&logo=openid&logoColor=white" />
  <img src="https://img.shields.io/badge/GitHub%20OAuth-181717?style=for-the-badge&logo=github&logoColor=white" />
  <img src="https://img.shields.io/badge/Google%20OAuth-4285F4?style=for-the-badge&logo=google&logoColor=white" />
  <img src="https://img.shields.io/badge/2FA%20%2F%20TOTP-FF6F00?style=for-the-badge&logo=authy&logoColor=white" />
  <img src="https://img.shields.io/badge/QR%20Code-000000?style=for-the-badge&logo=qr-code&logoColor=white" />
</p>


## 📝 Descrição do repositório

Este repositório reúne todos os códigos, exercícios e anotações desenvolvidos ao longo do curso Java e Spring Security: login com GitHub, Google e autenticação de 2 fatores.
Aqui você aprenderá a implementar um sistema de autenticação seguro e moderno, usando OAuth2, OIDC e aplicações autenticadoras.

---

# 📘 README — Java e Spring Security: Login Social + OAuth2 + 2FA

Este repositório reúne todos os códigos, exercícios e anotações desenvolvidos ao longo do curso **Java e Spring Security: login com GitHub, Google e autenticação de 2 fatores**.

Aqui você aprenderá a implementar um sistema de autenticação seguro e moderno, usando OAuth2, OIDC e aplicações autenticadoras.

---

# 📚 Conteúdos do Curso

## 🔐 1. Login com GitHub

Aprenda como integrar sua aplicação com o GitHub como provedor OAuth2.

### ✔️ Conteúdos:

* Configuração do ambiente
* Registro do cliente OAuth2 no GitHub
* Redirecionamento para login externo
* Entendendo papéis do OAuth
* Uso correto do parâmetro `state`
* Troca de código → token
* Comparação entre RestClient e RestTemplate
* Integrando login social ao projeto Listin
* Segurança em plataformas de bem‑estar
* Registro de cliente em múltiplos provedores
* O que aprendemos

### 🎯 Objetivo:

Implementar o fluxo completo de login via GitHub usando OAuth 2.0 Authorization Code.

---

## 🧩 2. Acessando a API do GitHub

Agora, vamos usar o token obtido para consultar dados do usuário.

### ✔️ Conteúdos:

* Obtendo dados do Resource Owner
* Filtrando email verificado
* Autenticando usuário via GitHub
* Grant types do OAuth
* Trabalhando com scopes
* URL de callback
* Implementando DTOs
* Padrões de segurança vistos na Jornada Milhas
* Cadastrando usuários com informações vindas do GitHub

### 🎯 Objetivo:

Consumir a API do GitHub com o token e autenticar o usuário no sistema.

---

## 🔵 3. Login com Google (OpenID Connect)

Integração com Google Cloud e protocolo OpenID Connect.

### ✔️ Conteúdos:

* Criando projeto no Google Cloud
* Registrando cliente OAuth2
* Armazenando credenciais em variáveis de ambiente
* Adaptação do fluxo do GitHub
* Entendendo o OpenID Connect
* Uso de refresh tokens
* O que é o Spring Security OAuth Client
* Otimizando fluxo de autenticação
* Segurança no ScreenMatch
* Cadastro de usuário com dados do Google

### 🎯 Objetivo:

Implementar login com Google usando OpenID Connect e ID Token.

---

## 🔐 4. Ativando Autenticação de Dois Fatores (2FA)

Aumente a segurança exigindo confirmação via aplicativo autenticador.

### ✔️ Conteúdos:

* O que é 2FA
* Comparação de níveis de segurança
* Como funcionam aplicativos autenticadores
* Implementação de TOTP
* Gerando QR Code
* Segurança e experiência na plataforma Freelando
* Refatoração do código do usuário

### 🎯 Objetivo:

Implementar TOTP usando bibliotecas Java e exibir QR Code para configuração do autenticador.

---

## 🔑 5. Verificando o Segundo Fator

Validação do código TOTP durante o fluxo de login.

### ✔️ Conteúdos:

* Validação do TOTP
* Modificação do fluxo de login para exigir 2FA
* Confirmação do segundo fator
* Vulnerabilidades e riscos comuns
* Proteção de transações (UseDev)
* Integração com Listin
* Projeto final do curso

### 🎯 Objetivo:

Completar o fluxo: login → senha → TOTP.

---

# 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3+**
* **Spring Security 6+**
* **OAuth2 Client**
* **OpenID Connect (OIDC)**
* **TOTP (Google Authenticator / Authy)**
* **QR Code Generator**
* **API GitHub**
* **Google Cloud OAuth2**

---


---

# ▶️ Como Executar o Projeto

```bash
git clone https://github.com/Rafael01Gx/spring-security-oauth2-social-login-2fa.git
cd spring-security-oauth2-social-login-2fa
./mvnw spring-boot:run
```

### ⚙️ Variáveis de ambiente necessárias

```
GITHUB_CLIENT_ID=
GITHUB_CLIENT_SECRET=
GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
JWT_SECRET=
```

---

# 📖 Referências

* Documentação oficial Spring Security
* RFC 6749 — OAuth 2.0 Authorization Framework
* OpenID Connect Core Specs
* GitHub OAuth Apps Docs
* Google Identity Platform Docs

---

# 🎓 Sobre o Curso

Este repositório acompanha todas as aulas práticas do curso **Java e Spring Security: login com GitHub, Google e autenticação de 2 fatores**, organizado em módulos com exercícios, desafios e implementações completas.

---

Se quiser, posso gerar:

* 📌 Versão do README em inglês
* 📌 Badge shields para deixar visualmente mais bonito
* 📌 Fluxogramas de OAuth/OIDC
* 📌 Diagrama UML do fluxo de autenticação

Só pedir! 🚀
