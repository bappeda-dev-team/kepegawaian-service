package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanStatus;

public record MasterJabatanStatusResponse(
        String label,
        String value
) {
    public static MasterJabatanStatusResponse from(MasterJabatanStatus statusJabatan) {
        return new MasterJabatanStatusResponse(
                statusJabatan.getLabel(),
                statusJabatan.name()
        );
    }
}
