package cc.kertaskerja.kepegawaian.jabatan_pegawai.web;

import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiCreateCommand;
import cc.kertaskerja.kepegawaian.jabatan_pegawai.domain.JabatanPegawaiJenisPenugasan;

import java.time.LocalDate;

public record JabatanPegawaiCreateRequest(
        Long pegawaiId,
        Long masterJabatanId,
        Long opdId,
        JabatanPegawaiJenisPenugasan jenisPenugasan,
        LocalDate tmtMulai
) {
    public JabatanPegawaiCreateCommand toCommand() {
        return new JabatanPegawaiCreateCommand(
                pegawaiId,
                masterJabatanId,
                opdId,
                jenisPenugasan,
                tmtMulai
        );
    }
}
