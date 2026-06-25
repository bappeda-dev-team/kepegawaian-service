package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiAlasanBerakhir;

import java.time.LocalDate;

public record JabatanPegawaiNonAktifkanRequest(
        JabatanPegawaiAlasanBerakhir alasanBerakhir,
        LocalDate tmtAkhir
) {
}
