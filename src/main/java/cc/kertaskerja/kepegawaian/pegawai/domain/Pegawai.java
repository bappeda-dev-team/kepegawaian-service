package cc.kertaskerja.kepegawaian.pegawai.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;

@Table("pegawai")
public record Pegawai(
        @Id
        Long id,

        String nip,

        @Column("nama_pegawai")
        String namaPegawai,

        @Column("jenis_kelamin")
        String jenisKelamin,

        @Column("tempat_lahir")
        String tempatLahir,

        @Column("tanggal_lahir")
        LocalDate tanggalLahir,

        @Column("jenis_pegawai")
        String jenisPegawai,

        @Column("status_pegawai")
        PegawaiStatus statusPegawai,

        String keterangan,

        @Column("keycloak_user_id")
        String keycloakUserId,

        @CreatedDate
        @Column("created_date")
        Instant createdDate,

        @LastModifiedDate
        @Column("last_modified_date")
        Instant lastModifiedDate
) {

    public static Pegawai of(
            String nip,
            String namaPegawai,
            String jenisKelamin,
            String tempatLahir,
            LocalDate tanggalLahir,
            String jenisPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                null,
                nip,
                namaPegawai,
                jenisKelamin,
                tempatLahir,
                tanggalLahir,
                jenisPegawai,
                statusPegawai,
                null,
                null,
                null,
                null
        );
    }

    public Pegawai update(
            String nip,
            String namaPegawai,
            String jenisKelamin,
            String tempatLahir,
            LocalDate tanggalLahir,
            String jenisPegawai,
            PegawaiStatus statusPegawai
    ) {
        return new Pegawai(
                id,
                nip,
                namaPegawai,
                jenisKelamin,
                tempatLahir,
                tanggalLahir,
                jenisPegawai,
                statusPegawai,
                keterangan,
                keycloakUserId,
                createdDate,
                null
        );
    }

    public Pegawai withKeycloakUserId(String keycloakUserId) {
        return new Pegawai(
                id,
                nip,
                namaPegawai,
                jenisKelamin,
                tempatLahir,
                tanggalLahir,
                jenisPegawai,
                statusPegawai,
                keterangan,
                keycloakUserId,
                createdDate,
                null
        );
    }
}
