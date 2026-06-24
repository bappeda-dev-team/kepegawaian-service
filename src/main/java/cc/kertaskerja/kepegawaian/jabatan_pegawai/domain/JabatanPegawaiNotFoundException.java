package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

public class JabatanPegawaiNotFoundException extends RuntimeException {
    public JabatanPegawaiNotFoundException(Long id) {
        super("Jabatan pegawai dengan id " + id + " tidak ditemukan.");
    }
    public JabatanPegawaiNotFoundException() {
        super("Pegawai belum memiliki jabatan aktif.");
    }
}
