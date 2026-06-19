package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;

public record MasterJabatanResponse(
        Long id,

        String kodeJabatan,

        String namaJabatan,

        String kategoiJabatan,

        String jenjangJabatan,

        String statusJabatan
) {
    public static MasterJabatanResponse from(MasterJabatan masterJabatan) {
        return new MasterJabatanResponse(
                masterJabatan.id(),
                masterJabatan.kodeJabatan(),
                masterJabatan.namaJabatan(),
                masterJabatan.jenjangJabatan().getKategori().toString(),
                masterJabatan.jenjangJabatan().toString(),
                masterJabatan.statusJabatan().toString()
        );
    }
}
