package cc.kertaskerja.kepegawaian.role.web;

import cc.kertaskerja.kepegawaian.role.domain.Role;

public record RoleCreateRequest(
    String kodeRole,
    String namaRole
) {
    public Role toCommand() {
        return Role.of(
            kodeRole,
            namaRole
        );
    }
}
