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

        @Column("pegawai_id")
        String pegawaiId,

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
            String pegawaiId,
            String namaPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                null,
                pegawaiId,
                namaPegawai,
                statusPegawai,
                null,
                null
        );
    }

    public Pegawai update(
            String pegawaiId,
            String namaPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                id,
                pegawaiId,
                namaPegawai,
                statusPegawai,
                createdDate,
                null
        );
    }

    public String golongan() {
        return null;
    }

    public String pangkat() {
        return null;
    }

    public String jenisJabatan() {
        return null;
    }

    public String namaJabatan() {
        return null;
    }
}
