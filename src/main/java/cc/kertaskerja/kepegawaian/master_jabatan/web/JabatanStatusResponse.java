package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanStatus;

public record JabatanStatusResponse(
        String label,
        String value
) {
    public static JabatanStatusResponse from(JabatanStatus statusJabatan) {
        return new JabatanStatusResponse(
                statusJabatan.getLabel(),
                statusJabatan.name()
        );
    }
}
