package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiView;

public record PegawaiDetailResponse(
        Long id,
        String nip,
        String namaPegawai,
        String kodeOpd,
        String namaOpd,
        String role
) {

    public static PegawaiDetailResponse from(PegawaiView view) {
        return new PegawaiDetailResponse(
                view.pegawai().id(),
                view.pegawai().nip(),
                view.pegawai().namaPegawai(),
                view.jabatanPegawai().kodeOpd(),
                view.jabatanPegawai().namaOpd(),
                view.role().role().namaRole()
        );
    }
}
