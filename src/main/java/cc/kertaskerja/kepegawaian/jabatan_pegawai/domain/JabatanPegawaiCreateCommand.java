package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import java.time.LocalDate;

public record JabatanPegawaiCreateCommand(
        Long pegawaiId,
        Long masterJabatanId,
        Long opdId,
        JabatanPegawaiJenisPenugasan jenisPenugasan,
        LocalDate tmtMulai
) {
}
