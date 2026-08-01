package cc.kertaskerja.kepegawaian.role.web;

import cc.kertaskerja.kepegawaian.role.domain.Role;

public record RoleResponse(
    Long id,
    String kodeRole,
    String namaRole
) {
    public static RoleResponse from(Role role) {
        return new RoleResponse(
            role.id(),
            role.kodeRole(),
            role.namaRole()
        );
    }
}
