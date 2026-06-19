package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanJenjang;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanStatus;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;

public record MasterJabatanUpdateRequest(
        String namaJabatan,
        JabatanJenjang jenjangJabatan
) {
    public MasterJabatan toCommand() {
        return MasterJabatan.of(
                null,
                namaJabatan,
                jenjangJabatan,
                JabatanStatus.AKTIF
        );
    }
}
