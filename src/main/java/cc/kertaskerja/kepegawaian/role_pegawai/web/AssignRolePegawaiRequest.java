package cc.kertaskerja.kepegawaian.role_pegawai.web;

import cc.kertaskerja.kepegawaian.role_pegawai.domain.RolePegawai;

public record AssignRolePegawaiRequest(
        Long roleId,
        Long pegawaiId
) {

    public RolePegawai toCommand() {
        return RolePegawai.create(
                roleId,
                pegawaiId
        );
    }
}
