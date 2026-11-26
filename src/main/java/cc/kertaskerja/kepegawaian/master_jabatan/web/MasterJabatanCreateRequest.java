package cc.kertaskerja.kepegawaian.master_jabatan.web;

import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatan;
import cc.kertaskerja.kepegawaian.master_jabatan.domain.MasterJabatanJenjang;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MasterJabatanCreateRequest(
        @NotNull(message = "OPD wajib dipilih") String kodeOpd,

        @NotBlank(message = "Nama jabatan wajib terisi") @NotNull String namaJabatan,

        @NotNull(message = "Jenjang jabatan wajib terisi") MasterJabatanJenjang jenjangJabatan) {

    public MasterJabatan.CreateCommand toCommand() {
        return new MasterJabatan.CreateCommand(
                kodeOpd,
                namaJabatan,
                jenjangJabatan);
    }
}
