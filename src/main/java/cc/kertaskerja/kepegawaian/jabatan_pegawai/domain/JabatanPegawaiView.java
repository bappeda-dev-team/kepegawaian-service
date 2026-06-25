package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import java.time.Instant;
import java.time.LocalDate;

public record JabatanPegawaiView(
        Long jabatanPegawaiId,
        Long pegawaiId,
        String namaJabatan,
        String kodeOpd,
        String namaOpd,

        JabatanPegawaiJenisPenugasan jenisPenugasan,
        JabatanPegawaiAlasanBerakhir alasanBerakhir,

        LocalDate tmtMulai,
        LocalDate tmtAkhir,

        Instant createdDate
) {

}
