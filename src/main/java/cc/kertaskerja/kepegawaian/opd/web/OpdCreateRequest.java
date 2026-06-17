package cc.kertaskerja.kepegawaian.opd.web;

import cc.kertaskerja.kepegawaian.opd.domain.Opd;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OpdCreateRequest(
        @NotBlank(message = "Kode OPD wajib terisi")
        @NotNull
        @Pattern(
                regexp = "^[1-9]\\.[0-9]{2}\\.[0-9]\\.[0-9]{2}\\.[0-9]\\.[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}$",
                message = "Format kode tidak valid"
        )
        String kodeOpd,

        @NotBlank(message = "Nama OPD wajib terisi")
        @NotNull
        String namaOpd,

        @NotNull
        String singkatanOpd
) {

        public Opd toCommand() {
                return Opd.of(
                        "",
                        kodeOpd,
                        namaOpd,
                        singkatanOpd
                );
        }
}
