package cc.kertaskerja.kepegawaian.integration.simpeg.mapper;

import cc.kertaskerja.kepegawaian.config.KertaskerjaProperties;
import cc.kertaskerja.kepegawaian.integration.simpeg.dto.SimpegOpdResponse;
import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import cc.kertaskerja.kepegawaian.opd.domain.OpdStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SimpegOpdMapper {
    private final KertaskerjaProperties properties;

    public SimpegOpdMapper(KertaskerjaProperties properties) {
        this.properties = properties;
    }
    public Opd toDomain(SimpegOpdResponse source, String kodeOpd) {
        return new Opd(
                null,
                properties.kodeLembaga(),
                kodeOpd,
                source.opdNama(),
                createSingkatan(source.opdNama()),
                OpdStatus.AKTIF,
                null,
                null
        );
    }

    private String createSingkatan(String namaOpd) {
        return Arrays.stream(namaOpd.trim().toUpperCase().split("\\s+"))
                .map(word -> word.substring(0, 1))
                .collect(Collectors.joining());
    }
}
