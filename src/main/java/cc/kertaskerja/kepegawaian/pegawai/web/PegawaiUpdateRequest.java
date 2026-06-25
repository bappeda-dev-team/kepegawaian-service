package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

public record PegawaiUpdateRequest(
        String nip,
        String namaPegawai,
        PegawaiStatus statusPegawai
) {
    public Pegawai toCommand() {
        return Pegawai.of(
                nip,
                namaPegawai,
                statusPegawai
        );
    }
}
