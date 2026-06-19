package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("master_jabatan")
public record MasterJabatan(
        @Id
        Long id,

        @Column("kode_jabatan")
        String kodeJabatan,

        @Column("nama_jabatan")
        String namaJabatan,

        @Column("jenjang_jabatan")
        JabatanJenjang jenjangJabatan,

        @Column("status_jabatan")
        JabatanStatus statusJabatan,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate
) {

        public static MasterJabatan of(
            String kodeJabatan,
            String namaJabatan,
            JabatanJenjang jenjangJabatan,
            JabatanStatus statusJabatan) {
                return new MasterJabatan(
                        null,
                        kodeJabatan,
                        namaJabatan,
                        jenjangJabatan,
                        statusJabatan,
                        null,
                        null);
        }

        public MasterJabatan update(
                String namaJabatan,
                JabatanJenjang jenjangJabatan,
                String kodeJabatan
        ) {
                return new MasterJabatan(
                        id,
                        kodeJabatan,
                        namaJabatan,
                        jenjangJabatan,
                        statusJabatan,
                        createdDate,
                        null
                );
        }
}
