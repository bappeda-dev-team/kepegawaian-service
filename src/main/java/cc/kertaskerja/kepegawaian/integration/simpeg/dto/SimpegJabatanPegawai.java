package cc.kertaskerja.kepegawaian.integration.simpeg.dto;

import java.time.LocalDate;

public record SimpegJabatanPegawai(
        String nip,
        Long opdId,
        String kodeJabatan,
        String namaJabatan,
        String jenisJabatan,
        String kodeEselon,
        LocalDate mulaiJabatan
) {
}
