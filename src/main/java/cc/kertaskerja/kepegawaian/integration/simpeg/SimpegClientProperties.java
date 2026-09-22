package cc.kertaskerja.kepegawaian.integration.simpeg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integration.simpeg")
public record SimpegClientProperties(
    String baseUrl,
    String apiPath
) {
}
