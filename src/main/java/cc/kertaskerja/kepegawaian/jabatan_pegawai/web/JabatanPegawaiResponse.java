package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import cc.kertaskerja.kepegawaian.common.web.EnumLabelResponse;

import java.time.Instant;
import java.time.LocalDate;

public record JabatanPegawaiResponse(
        Long id,
        Long pegawaiId,
        String namaJabatan,
        String namaOpd,
        EnumLabelResponse jenisPenugasan,
        EnumLabelResponse alasanBerakhir,
        LocalDate tmtMulai,
        LocalDate tmtAkhir,
        Instant createdDate
) {
    public static JabatanPegawaiResponse from(JabatanPegawaiView view) {
        return new JabatanPegawaiResponse(
                view.jabatanPegawaiId(),
                view.pegawaiId(),
                view.namaJabatan(),
                view.namaOpd(),
                EnumLabelResponse.of(view.jenisPenugasan()),
                EnumLabelResponse.of(view.alasanBerakhir()),
                view.tmtMulai(),
                view.tmtAkhir(),
                view.createdDate()
        );
    }
}
