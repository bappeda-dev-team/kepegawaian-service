package cc.kertaskerja.kepegawaian.role_pegawai.domain;

import cc.kertaskerja.kepegawaian.role.domain.Role;

public record AssignRoleResult(
        RolePegawai assignment,
        Role role
) {
}
