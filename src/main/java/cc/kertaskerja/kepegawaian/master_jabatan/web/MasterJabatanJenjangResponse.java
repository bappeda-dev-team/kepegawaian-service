package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanJenjang;

public record MasterJabatanJenjangResponse(
        String label,
        String value
) {
    public static MasterJabatanJenjangResponse from(MasterJabatanJenjang jenjangJabatan) {
        return new MasterJabatanJenjangResponse(
                jenjangJabatan.getLabel(),
                jenjangJabatan.name()
        );
    }
}
