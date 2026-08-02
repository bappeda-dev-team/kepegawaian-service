package cc.kertaskerja.kepegawaian.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "kertaskerja.cors")
public record CorsProperties(
        boolean enabled,
        List<String> allowedHosts
) {}
