# 📘 **HTTP Interfaces --- (Spring Boot 3.2+)**

> Um guia definitivo para entender **o fluxo completo**: desde a criação
> das interfaces, configuração dos beans, até o uso em Services.

------------------------------------------------------------------------

# 🚀 **1. O que são HTTP Interfaces?**

HTTP Interfaces são uma feature moderna do Spring Boot (3.0+),
aprimorada no 3.2, que permite:

-   Declarar **interfaces Java** representando chamadas HTTP.
-   Deixar o código mais limpo, sem `client.get().retrieve()`.
-   Desacoplar Services da lógica de requisição.
-   Criar "clients HTTP tipados", semelhantes ao OpenFeign.

Em vez de escrever:

``` java
restClient.get()
    .uri("/user")
    .retrieve()
    .body(User.class);
```

Você escreve:

``` java
@GetExchange("/user")
User buscarUsuario();
```

------------------------------------------------------------------------

# 🧩 **2. Estrutura Geral**

    Interface HTTP  →  RestClient Config  →  ProxyFactory  →  Service

------------------------------------------------------------------------

# 🧱 **3. Criando uma HTTP Interface**

``` java
@HttpExchange
public interface GitHubAuthClient {

    @PostExchange("/login/oauth/access_token")
    Map<String, Object> trocarCodePorToken(Map<String, String> body);

    @GetExchange("/user")
    String buscarDadosUsuario(@RequestHeader("Authorization") String auth);
}
```

------------------------------------------------------------------------

# ⚙️ **4. Criando o RestClient Base**

``` java
@Bean
public RestClient githubRestClient(RestClient.Builder builder, GitHubProperties props) {
    return builder
        .baseUrl(props.apiBaseUrl())
        .defaultHeader("Accept", "application/json")
        .build();
}
```

------------------------------------------------------------------------

# 🏭 **5. Criando o Proxy**

``` java
@Bean
public GitHubAuthClient githubAuthClient(@Qualifier("githubRestClient") RestClient client) {
    var adapter = RestClientAdapter.create(client);
    var factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(GitHubAuthClient.class);
}
```

------------------------------------------------------------------------

# 🧪 **6. Uso no Service**

``` java
public String obterEmail(String code) {
    var token = obterToken(code);
    return client.buscarDadosUsuario("Bearer " + token);
}
```

------------------------------------------------------------------------

# 🧠 **7. Vantagens**

-   Código limpo\
-   Baixo acoplamento\
-   Remoção de lógica HTTP dos Services\
-   Similar ao OpenFeign, porém nativo

------------------------------------------------------------------------

# 🛑 **8. Observações**

-   Tokens dinâmicos **não** são aplicados automaticamente.\
-   Use `@RequestHeader` para Authorization.

------------------------------------------------------------------------

# ✔️ **9. Mini Cheat-Sheet**

``` java
@HttpExchange
public interface API {}

@Bean
RestClient api(RestClient.Builder b) { ... }

@Bean
API apiClient(RestClient c) { ... }

@Service
class XService { ... }
```

------------------------------------------------------------------------

# 🎯 **Conclusão**

Você agora entende toda a arquitetura de HTTP Interfaces no Spring Boot
--- do RestClient ao uso no Service.
