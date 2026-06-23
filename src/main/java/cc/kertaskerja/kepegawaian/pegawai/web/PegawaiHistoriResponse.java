package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.pegawai.domain.Pegawai;

public record PegawaiHistoriResponse(
        String pegawiId,

        String namaPegawai,

        String namaJabatan,

        String pangkat,

        String golongan,

        String jenisJabatan

) {

    public static PegawaiHistoriResponse from(Pegawai pegawai) {
        return new PegawaiHistoriResponse(
                pegawai.pegawaiId(),
                pegawai.namaPegawai(),
                pegawai.namaJabatan(),
                pegawai.pangkat(),
                pegawai.golongan(),
                pegawai.jenisJabatan()
        );
    }
}
