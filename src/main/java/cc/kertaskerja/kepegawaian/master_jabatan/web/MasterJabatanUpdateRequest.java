package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanJenjang;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanStatus;

public record MasterJabatanUpdateRequest(
        String namaJabatan,
        MasterJabatanJenjang jenjangJabatan
) {
    public MasterJabatan toCommand() {
        return MasterJabatan.of(
                null,
                namaJabatan,
                jenjangJabatan,
                MasterJabatanStatus.AKTIF
        );
    }
}
