package cc.kertaskerja.kepegawaian.role.web;

import cc.kertaskerja.kepegawaian.role.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RoleUpdateRequest(
        @NotBlank(message = RoleConstraints.NAMA_ROLE_REQUIRED_MESSAGE) @Pattern(regexp = RoleConstraints.NAMA_ROLE_PATTERN, message = RoleConstraints.NAMA_ROLE_INVALID_MESSAGE) String namaRole,
        @NotNull(message = "Status administrator wajib diisi") Boolean isAdmin) {
    public Role.UpdateCommand toCommand() {
        return new Role.UpdateCommand(
                namaRole,
                isAdmin);
    }
}
