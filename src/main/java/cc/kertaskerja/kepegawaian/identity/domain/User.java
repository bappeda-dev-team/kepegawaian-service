package cc.kertaskerja.kepegawaian.identity.domain;

import java.util.Set;

public record User(
        String userName,
        String email,
        Set<String> roles,
        String kodeOpd,
        String namaOpd,
        Boolean isActive) {
}
