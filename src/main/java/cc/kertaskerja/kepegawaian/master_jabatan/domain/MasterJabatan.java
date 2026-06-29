package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import cc.kertaskerja.kepegawaian.common.domain.Optionable;
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
        MasterJabatanJenjang jenjangJabatan,

        @Column("status_jabatan")
        MasterJabatanStatus statusJabatan,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate
) implements Optionable {

        public static MasterJabatan of(
            String kodeJabatan,
            String namaJabatan,
            MasterJabatanJenjang jenjangJabatan,
            MasterJabatanStatus statusJabatan) {
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
                MasterJabatanJenjang jenjangJabatan,
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

        @Override
        public String optionLabel() {
                return namaJabatan;
        }

        @Override
        public String optionValue() {
                return id.toString();
        }
}
