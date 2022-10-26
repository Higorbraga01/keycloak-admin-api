package br.mil.ccarj.controle.acesso;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.ws.rs.client.ClientBuilder;

@SpringBootApplication
public class ControleAcessoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleAcessoApplication.class, args);
    }

    @Bean
    public Keycloak login() {
        return  KeycloakBuilder.builder()
                .serverUrl("http://localhost:8190/auth")
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("admin-cli")
                .username("admin")
                .password("admin")
                .resteasyClient(
                        (ResteasyClient) ClientBuilder.newClient()
                ).build();
    }

}
