package cc.kertaskerja.kepegawaian.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kertaskerja")
public record KertaskerjaProperties(
        String kodeLembaga,
        String status
) {
}
