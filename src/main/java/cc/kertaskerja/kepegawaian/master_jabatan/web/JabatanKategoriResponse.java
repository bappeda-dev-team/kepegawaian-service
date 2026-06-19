package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanKategori;

public record JabatanKategoriResponse(
        String label,
        String value
) {
    public static JabatanKategoriResponse from(JabatanKategori kategoriJabatan) {
        return new JabatanKategoriResponse(
                kategoriJabatan.getLabel(),
                kategoriJabatan.name()
        );
    }
}
