package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanJenjang;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.JabatanStatus;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MasterJabatanCreateRequest(
        @NotBlank(message = "Nama jabatan wajib terisi")
        @NotNull
        String namaJabatan,

        @NotNull(message = "Jenjang jabatan wajib terisi")
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
