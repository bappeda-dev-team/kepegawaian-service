package cc.kertaskerja.kepegawaian.pegawai.domain;

import java.util.Set;

public record PegawaiIdentityData(
        Pegawai pegawai,
        String kodeOpd,
        String namaOpd,
        Set<String> roles
) {
}
