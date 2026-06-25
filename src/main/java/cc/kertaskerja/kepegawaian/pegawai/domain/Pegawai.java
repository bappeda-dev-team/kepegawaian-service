package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("pegawai")
public record Pegawai(
        @Id
        Long id,

        String nip,

        @Column("nama_pegawai")
        String namaPegawai,

        @Column("status_pegawai")
        PegawaiStatus statusPegawai,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate
) {
    public static Pegawai of(
            String nip,
            String namaPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                null,
                nip,
                namaPegawai,
                statusPegawai,
                null,
                null
        );
    }

    public Pegawai update(
            String nip,
            String namaPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                id,
                nip,
                namaPegawai,
                statusPegawai,
                createdDate,
                null
        );
    }

}
