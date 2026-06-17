package cc.kertaskerja.kepegawaian.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KertaskerjaInfoContributor implements InfoContributor {
    private final KertaskerjaProperties kertaskerjaProperties;

    public KertaskerjaInfoContributor(KertaskerjaProperties kertaskerjaProperties) {
        this.kertaskerjaProperties = kertaskerjaProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("kertaskerja", Map.of(
                "kode-lembaga", kertaskerjaProperties.kodeLembaga()
        ));
    }
}
