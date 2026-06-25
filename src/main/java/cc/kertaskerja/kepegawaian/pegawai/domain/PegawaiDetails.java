package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;

import java.util.List;

public record PegawaiDetails(
        String nip,
        String namaPegawai,
        List<JabatanPegawaiView> jabatanPegawais
) {
}
