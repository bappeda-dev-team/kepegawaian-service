package cc.kertaskerja.kepegawaian.role.web;

import cc.kertaskerja.kepegawaian.role.domain.Role;

public record RoleUpdateRequest(
        String kodeRole,
        String namaRole,
        Boolean isAdmin) {
    public Role toCommand() {
        return Role.of(
                kodeRole,
                namaRole,
                isAdmin);
    }
}
