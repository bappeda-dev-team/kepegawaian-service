package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanJenjang;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanStatus;

public record MasterJabatanUpdateRequest(
        Long opdId,
        String namaJabatan,
        MasterJabatanJenjang jenjangJabatan
) {
    public MasterJabatan toCommand() {
        return MasterJabatan.of(
                opdId,
                null,
                namaJabatan,
                jenjangJabatan,
                MasterJabatanStatus.AKTIF
        );
    }
}
