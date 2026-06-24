package cc.kertaskerja.kepegawaian.pegawai.domain;

public class PegawaiNotFoundException extends RuntimeException {
    public PegawaiNotFoundException(Long id) {
        super("Pegawai dengan id " + id + " tidak ditemukan.");
    }

    public PegawaiNotFoundException(String pegawaiId) {
        super("Pegawai dengan nip " + pegawaiId + " tidak ditemukan.");
    }
}
