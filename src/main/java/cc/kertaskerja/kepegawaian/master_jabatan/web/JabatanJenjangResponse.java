package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanJenjang;

public record JabatanJenjangResponse(
        String label,
        String value
) {
    public static JabatanJenjangResponse from(JabatanJenjang jenjangJabatan) {
        return new JabatanJenjangResponse(
                jenjangJabatan.getLabel(),
                jenjangJabatan.name()
        );
    }
}
