package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.AssignRoleResult;

import java.util.List;

public record PegawaiDetails(
        Long id,
        String nip,
        String namaPegawai,
        List<JabatanPegawaiView> jabatanPegawais,
        List<AssignRoleResult> rolePegawais
) {
}
