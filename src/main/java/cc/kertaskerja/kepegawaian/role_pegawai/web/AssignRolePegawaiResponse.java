package cc.kertaskerja.kepegawaian.role_pegawai.web;

import cc.kertaskerja.kepegawaian.role_pegawai.domain.AssignRoleResult;

public record AssignRolePegawaiResponse(
        Long id,
        Long roleId,
        String roleName,
        Long pegawaiId
) {
    public static AssignRolePegawaiResponse from(AssignRoleResult result) {
        return new AssignRolePegawaiResponse(
                result.assignment().id(),
                result.assignment().roleId(),
                result.role().namaRole(),
                result.assignment().pegawaiId()
        );
    }
}
