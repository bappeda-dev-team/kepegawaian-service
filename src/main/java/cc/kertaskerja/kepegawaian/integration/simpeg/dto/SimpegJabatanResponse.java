package cc.kertaskerja.kepegawaian.integration.simpeg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimpegJabatanResponse(
        @JsonProperty("opd_kode")
        String opdKode,

        @JsonProperty("opd_nama")
        String opdNama,

        @JsonProperty("jenis_jabatan_kode")
        String jenisJabatanKode,

        @JsonProperty("jenis_jabatan_nama")
        String jenisJabatanNama,

        @JsonProperty("jabatan_kode")
        String jabatanKode,

        @JsonProperty("jabatan_nama")
        String jabatanNama,

        @JsonProperty("jenjang_jabatan_kode")
        String jenjangJabatanKode,

        @JsonProperty("jenjang_jabatan_nama")
        String jenjangJabatanNama
) {
}
