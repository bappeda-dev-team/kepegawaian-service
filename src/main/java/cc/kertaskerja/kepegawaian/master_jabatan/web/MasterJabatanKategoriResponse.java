package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanKategori;

public record MasterJabatanKategoriResponse(
        String label,
        String value
) {
    public static MasterJabatanKategoriResponse from(MasterJabatanKategori kategoriJabatan) {
        return new MasterJabatanKategoriResponse(
                kategoriJabatan.getLabel(),
                kategoriJabatan.name()
        );
    }
}
