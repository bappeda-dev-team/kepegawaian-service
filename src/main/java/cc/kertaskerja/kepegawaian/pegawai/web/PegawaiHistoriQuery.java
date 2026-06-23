package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiJenisHistori;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PegawaiHistoriQuery(
        @Parameter(
                description = "Filter Bulan histori",
                example = "12"
        )
        @Min(1) @Max(12)
        Integer bulan,

        @Parameter(
                description = "Filter Tahun histori ",
                example = "2026"
        )
        Integer tahun,

        @Parameter(
                description = "Jenis histori pegawai",
                example = "JABATAN"
        )
        PegawaiJenisHistori jenisHistori
        ) {
}
