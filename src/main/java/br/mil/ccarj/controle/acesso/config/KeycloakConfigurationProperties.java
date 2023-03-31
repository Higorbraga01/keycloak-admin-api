package br.mil.ccarj.controle.acesso.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakConfigurationProperties {

    @Getter(AccessLevel.NONE)
    private boolean initializeOnStartup;

    public boolean initializeOnStartup() {
        return initializeOnStartup;
    }

    private String realm;

    private String clientId;

    private String username;

    private String password;

    private String serverUrl;

    private String grantType;
}