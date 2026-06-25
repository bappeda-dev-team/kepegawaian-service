package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

public record PegawaiCreateRequest(
        String nip,
        String namaPegawai
) {

    public Pegawai toCommand() {
        return Pegawai.of(
                nip,
                namaPegawai,
                PegawaiStatus.AKTIF
        );
    }
}
