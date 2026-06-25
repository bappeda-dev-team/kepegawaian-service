package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

public class JabatanPegawaiAlreadyNonAktifException extends RuntimeException {
    public JabatanPegawaiAlreadyNonAktifException(JabatanPegawai jabatanPegawai) {
        super("Jabatan ini sudah berakhir pada " + jabatanPegawai.tmtAkhir() + " .Alasan berakhir: " + jabatanPegawai.alasanBerakhir());
    }
}
