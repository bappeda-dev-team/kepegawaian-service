package cc.kertaskerja.kepegawaian.pegawai.domain;

public class PegawaiAlreadyExistsException extends RuntimeException {
    public PegawaiAlreadyExistsException(String pegawaiId) {
        super("Pegawai dengan nip " + pegawaiId + " sudah ada.");
    }
}
