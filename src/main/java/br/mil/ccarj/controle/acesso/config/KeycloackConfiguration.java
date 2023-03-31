package br.mil.ccarj.controle.acesso.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.ClientBuilder;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Configuration
public class KeycloackConfiguration {


    final KeycloakConfigurationProperties keycloakInitializerConfigurationProperties;


    private final ResourceLoader resourceLoader;

    @Autowired
    public KeycloackConfiguration(KeycloakConfigurationProperties keycloakInitializerConfigurationProperties, ResourceLoader resourceLoader) {
        this.keycloakInitializerConfigurationProperties = keycloakInitializerConfigurationProperties;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    protected Keycloak keycloak() throws IOException {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .realm(keycloakInitializerConfigurationProperties.getRealm())
                .clientId(keycloakInitializerConfigurationProperties.getClientId())
                .username(keycloakInitializerConfigurationProperties.getUsername())
                .password(keycloakInitializerConfigurationProperties.getPassword())
                .serverUrl(keycloakInitializerConfigurationProperties.getServerUrl())
                .resteasyClient(
                        (ResteasyClient) ClientBuilder
                                .newBuilder()
                                .sslContext(
                                        getSslContext()
                                )
                                .hostnameVerifier(new NoopHostnameVerifier())
                                .build()
                )
                .build();
    }



    private SSLContext getSslContext() throws IOException {
        // load the certificate
        Resource resource = resourceLoader.getResource(
                ResourceUtils.CLASSPATH_URL_PREFIX
                        .concat("static/keycloak")
        );
        File file = new File(resource.getFile().getAbsolutePath());
        InputStream fis;
        try {
            fis = new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        CertificateFactory cf;
        SSLContext ctx;
        try {
            cf = CertificateFactory.getInstance("X.509");

            Certificate cert = cf.generateCertificate(fis);

            // load the keystore that includes self-signed cert as a "trusted" entry
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            keyStore.setCertificateEntry("auth.homolog.ccarj.intraer/auth/", cert);
            tmf.init(keyStore);
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tmf.getTrustManagers(), null);
        } catch (CertificateException | IOException | NoSuchAlgorithmException | KeyManagementException |
                 KeyStoreException e) {
            throw new RuntimeException(e);
        }
        return ctx;
    }
}
