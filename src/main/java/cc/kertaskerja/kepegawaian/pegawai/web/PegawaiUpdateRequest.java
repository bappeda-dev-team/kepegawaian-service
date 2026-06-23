package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

public record PegawaiUpdateRequest(
        String pegawaiId,
        String namaPegawai,
        PegawaiStatus statusPegawai
) {
    public Pegawai toCommand() {
        return Pegawai.of(
                pegawaiId,
                namaPegawai,
                statusPegawai
        );
    }
}
