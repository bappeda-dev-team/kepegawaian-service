package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.web.JabatanPegawaiResponse;
import cc.kertaskerja.kepegawaian.pegawai.domain.PegawaiDetails;

import java.util.List;

public record PegawaiHistoriResponse(
        Long id,
        String nip,
        String namaPegawai,
        List<JabatanPegawaiResponse> jabatanPegawais
) {

    public static PegawaiHistoriResponse from(PegawaiDetails pegawai) {
        return new PegawaiHistoriResponse(
                pegawai.id(),
                pegawai.nip(),
                pegawai.namaPegawai(),
                pegawai.jabatanPegawais()
                        .stream()
                        .map(JabatanPegawaiResponse::from)
                        .toList()
        );
    }
}
