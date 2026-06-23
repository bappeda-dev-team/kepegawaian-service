package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

public record PegawaiCreateRequest(
        String pegawaiId,
        String namaPegawai
) {

    public Pegawai toCommand() {
        return Pegawai.of(
                pegawaiId,
                namaPegawai,
                PegawaiStatus.AKTIF
        );
    }
}
