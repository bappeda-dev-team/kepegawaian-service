package cc.kertaskerja.kepegawaian.pegawai.web;

import cc.kertaskerja.kepegawaian.identity.domain.User;
import java.util.Set;

public record UserResponse(
        String userName,
        String email,
        Set<String> roles,
        String kodeOpd,
        String namaOpd,
        Boolean isActive) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.userName(),
                user.email(),
                user.roles(),
                user.kodeOpd(),
                user.namaOpd(),
                user.isActive());
    }
}
