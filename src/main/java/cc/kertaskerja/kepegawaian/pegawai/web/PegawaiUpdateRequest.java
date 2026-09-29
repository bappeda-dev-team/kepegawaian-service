package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiStatus;

import java.time.LocalDate;

public record PegawaiUpdateRequest(
        String nip,
        String namaPegawai,
        String jenisKelamin,
        String tempatLahir,
        String jenisPegawai,
        LocalDate tanggalLahir,
        PegawaiStatus statusPegawai
) {
    public Pegawai toCommand() {
        return Pegawai.of(
                nip,
                namaPegawai,
                jenisKelamin,
                tempatLahir,
                tanggalLahir,
                jenisPegawai,
                statusPegawai
        );
    }
}
