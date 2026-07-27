package cc.kertaskerja.kepegawaian.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdentityProperties.class)
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(IdentityProperties properties) {

        var keycloak = properties.keycloak();

        return KeycloakBuilder.builder()
                .serverUrl(keycloak.serverUrl())
                .realm(keycloak.realm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloak.admin().clientId())
                .clientSecret(keycloak.admin().clientSecret())
                .build();
    }

}
