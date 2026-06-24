package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

public record PegawaiResponse(
        Long id,
        String pegawaiId,
        String namaPegawai,
        PegawaiStatus statusPegawai
) {
    public static PegawaiResponse from(Pegawai pegawai) {
        return new PegawaiResponse(
                pegawai.id(),
                pegawai.pegawaiId(),
                pegawai.namaPegawai(),
                pegawai.statusPegawai()
        );
    }
}
