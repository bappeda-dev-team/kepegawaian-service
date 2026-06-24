package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;

public record JabatanPegawaiResponse(
        Long jabatanPegawaiId,
        String pegawaiId,
        String namaPegawai,
        String namaJabatan,
        String namaOpd
) {
    public static JabatanPegawaiResponse from(JabatanPegawaiView view) {
        return new JabatanPegawaiResponse(
                view.jabatanPegawaiId(),
                view.pegawaiId(),
                view.namaPegawai(),
                view.namaJabatan(),
                view.namaOpd()
        );
    }
}
