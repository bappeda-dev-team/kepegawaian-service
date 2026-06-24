package cc.kertaskerja.kepegawaian.opd.domain;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "opd")
public record Opd(
        @Id Long id,

        @NotBlank(message = "Kode lembaga harus diisi")
        @Pattern(regexp = "^[1-9]{2}\\.[0-9]{2}$")
        @Column("kode_lembaga") String kodeLembaga,

        @Column("kode_opd") String kodeOpd,

        @Column("nama_opd") String namaOpd,

        @Column("singkatan_opd") String singkatanOpd,

        @Column("status_opd") OpdStatus statusOpd,

        @CreatedDate Instant createdDate,

        @LastModifiedDate Instant lastModifiedDate

) {

    public static Opd of(
            String kodeLembaga,
            String kodeOpd,
            String namaOpd,
            String singkatanOpd) {
        return new Opd(
                null,
                kodeLembaga,
                kodeOpd,
                namaOpd,
                singkatanOpd,
                OpdStatus.AKTIF,
                null,
                null);
    }

    public Opd update(
            String kodeOpd,
            String namaOpd,
            String singkatanOpd) {
        return new Opd(
                id,
                kodeLembaga,
                kodeOpd,
                namaOpd,
                singkatanOpd,
                statusOpd,
                createdDate,
                null);
    }
}
