package cc.kertaskerja.kepegawaian.integration.simpeg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimpegOpdResponse(
        @JsonProperty("opd_kode")
        String opdKode,

        @JsonProperty("opd_level")
        String opdLevel,

        @JsonProperty("opd_nama")
        String opdNama,

        @JsonProperty("opd_jabatan")
        String opdJabatan,

        @JsonProperty("eselon_kode")
        String eselonKode,

        @JsonProperty("eselon_nama")
        String eselonNama
) {
}
