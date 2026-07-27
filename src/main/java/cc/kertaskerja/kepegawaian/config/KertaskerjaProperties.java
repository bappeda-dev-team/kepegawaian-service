package cc.kertaskerja.kepegawaian.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kertaskerja")
public record KertaskerjaProperties(
        String kodeLembaga,
        String status,
        List<String> allowedHosts) {
}
