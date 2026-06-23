package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MasterJabatanCreateRequest(
        @NotBlank(message = "Nama jabatan wajib terisi")
        @NotNull
        String namaJabatan,

        @NotNull(message = "Jenjang jabatan wajib terisi")
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
