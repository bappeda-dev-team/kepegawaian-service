package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

public class JabatanPegawaiAlreadyExistsException extends RuntimeException {
    public JabatanPegawaiAlreadyExistsException(JabatanPegawai j) {
        super("Pegawai sudah memilik jabatan aktif di OPD " + j.namaOpd());
    }

    public JabatanPegawaiAlreadyExistsException() {
        super("Pegawai sudah memiliki jabatan aktif.");
    }
}
