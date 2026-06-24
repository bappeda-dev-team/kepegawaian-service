package cc.kertaskerja.kepegawaian.opd.web;

import cc.kertaskerja.kepegawaian.opd.domain.Opd;

public record OpdResponse(
        Long id,
        String kodeLembaga,
        String kodeOpd,
        String namaOpd,
        String singkatanOpd,
        String statusOpd
) {
    public static OpdResponse from(Opd opd) {
        return new OpdResponse(
                opd.id(),
                opd.kodeLembaga(),
                opd.kodeOpd(),
                opd.namaOpd(),
                opd.singkatanOpd(),
                opd.statusOpd().name()
        );
    }
}
