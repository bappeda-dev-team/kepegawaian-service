package cc.kertaskerja.kepegawaian.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "identity")
public record IdentityProperties(
        String provider,
        KeycloakProperties keycloak,
        MigrationProperties migration
) {

    public record KeycloakProperties(
            String serverUrl,
            String realm,
            AdminProperties admin
    ) {
    }

    public record AdminProperties(
            String clientId,
            String clientSecret
    ) {
    }

    public record MigrationProperties(
            String defaultPassword,
            Boolean temporaryPassword
    ) {}
}
