package cc.kertaskerja.kepegawaian.pegawai.domain;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiView;
import cc.kertaskerja.kepegawaian.role_pegawai.domain.AssignRoleResult;

public record PegawaiView(
        Pegawai pegawai,
        JabatanPegawaiView jabatanPegawai,
        AssignRoleResult role
) {
}
