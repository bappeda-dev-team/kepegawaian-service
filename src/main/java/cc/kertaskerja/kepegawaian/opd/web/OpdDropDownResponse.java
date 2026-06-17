package cc.kertaskerja.kepegawaian.opd.web;

import cc.kertaskerja.kepegawaian.opd.domain.Opd;

public record OpdDropDownResponse(
        Long id,
        String kodeOpd,
        String namaOpd,
        String singkatanOpd
) {
    public static OpdDropDownResponse from(Opd opd) {
        return new OpdDropDownResponse(
                opd.Id(),
                opd.kodeOpd(),
                opd.namaOpd(),
                opd.singkatanOpd()
        );
    }
}
