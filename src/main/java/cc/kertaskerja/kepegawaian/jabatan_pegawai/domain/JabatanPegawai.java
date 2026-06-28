package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;

@Table("jabatan_pegawai")
public record JabatanPegawai(
        @Id
        Long id,
        Long pegawaiId,

        Long masterJabatanId,
        String namaJabatan,

        Long opdId,
        String kodeOpd,
        String namaOpd,

        JabatanPegawaiJenisPenugasan jenisPenugasan,
        JabatanPegawaiAlasanBerakhir alasanBerakhir,

        LocalDate tmtMulai,
        LocalDate tmtAkhir,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate
) {
    // validasi
    public JabatanPegawai {

        if (tmtMulai == null) {
            throw new JabatanPegawaiValidationException("TMT mulai wajib ada");
        }

        if (tmtAkhir != null && tmtAkhir.isBefore(tmtMulai)) {
            throw new JabatanPegawaiValidationException(
                    "TMT akhir tidak boleh sebelum TMT mulai"
            );
        }

        if (tmtAkhir == null && alasanBerakhir != null) {
            throw new JabatanPegawaiValidationException(
                    "Jabatan aktif tidak boleh memiliki alasan berakhir"
            );
        }

        if (tmtAkhir != null && alasanBerakhir == null) {
            throw new JabatanPegawaiValidationException(
                    "Jabatan non aktif harus memiliki alasan berakhir"
            );
        }
    }

    public boolean isAktif() {
        return tmtAkhir == null;
    }

    public boolean isAktifAndUtama() {
        return tmtAkhir == null && jenisPenugasan.isUtama();
    }

    public boolean isNonAktif() {
        return !isAktif();
    }

    public boolean isMutasi() {
        return alasanBerakhir == JabatanPegawaiAlasanBerakhir.MUTASI;
    }

    public boolean isPensiun() {
        return alasanBerakhir == JabatanPegawaiAlasanBerakhir.PENSIUN;
    }

    public boolean isUtama() {
        return jenisPenugasan == JabatanPegawaiJenisPenugasan.UTAMA;
    }

    // factory
    public static JabatanPegawai aktif(
            Long pegawaiId,
            Long masterJabatanId,
            String namaJabatan,
            Long opdId,
            String kodeOpd,
            String namaOpd,
            LocalDate tmtMulai
    ) {
        return new JabatanPegawai(
                null,
                pegawaiId,

                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                JabatanPegawaiJenisPenugasan.UTAMA,
                null,

                tmtMulai,
                null,

                null,
                null
        );
    }

    public static JabatanPegawai plt(
            Long pegawaiId,
            Long masterJabatanId,
            String namaJabatan,
            Long opdId,
            String kodeOpd,
            String namaOpd,
            LocalDate tmtMulai
    ) {
        return new JabatanPegawai(
                null,
                pegawaiId,

                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                JabatanPegawaiJenisPenugasan.PLT,
                null,

                tmtMulai,
                null,

                null,
                null
        );
    }

    public static JabatanPegawai plh(
            Long pegawaiId,
            Long masterJabatanId,
            String namaJabatan,
            Long opdId,
            String kodeOpd,
            String namaOpd,
            LocalDate tmtMulai
    ) {
        return new JabatanPegawai(
                null,
                pegawaiId,

                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                JabatanPegawaiJenisPenugasan.PLH,
                null,

                tmtMulai,
                null,

                null,
                null
        );
    }

    public JabatanPegawai nonAktif(
            JabatanPegawaiAlasanBerakhir alasanBerakhir,
            LocalDate tmtAkhir
    ) {
        return new JabatanPegawai(
                id,
                pegawaiId,

                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                jenisPenugasan,
                alasanBerakhir,

                tmtMulai,
                tmtAkhir,

                createdDate,
                null
        );
    }

    public static JabatanPegawai create(
            Long pegawaiId,
            Long masterJabatanId,
            String namaJabatan,
            Long opdId,
            String kodeOpd,
            String namaOpd,
            JabatanPegawaiJenisPenugasan jenisPenugasan,
            LocalDate tmtMulai
    ) {
        return new JabatanPegawai(
                null,
                pegawaiId,

                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                jenisPenugasan,
                null,

                tmtMulai,
                null,

                null,
                null
        );
    }

    public JabatanPegawai mutasi(
            LocalDate tanggalMutasi
    ) {
        return new JabatanPegawai(
                id,
                pegawaiId,
                masterJabatanId,
                namaJabatan,

                opdId,
                kodeOpd,
                namaOpd,

                jenisPenugasan,
                JabatanPegawaiAlasanBerakhir.MUTASI,

                tmtMulai,
                tanggalMutasi,

                createdDate,
                null
        );
    }

    public JabatanPegawaiView toJabatanPegawaiView() {
        return new JabatanPegawaiView(
                id,
                pegawaiId,
                namaJabatan,
                kodeOpd,
                namaOpd,
                jenisPenugasan,
                alasanBerakhir,
                tmtMulai,
                tmtAkhir,
                createdDate
        );
    }
}
