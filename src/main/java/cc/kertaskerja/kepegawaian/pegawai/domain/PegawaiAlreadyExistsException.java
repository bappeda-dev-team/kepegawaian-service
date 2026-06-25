package cc.kertaskerja.kepegawaian.pegawai.domain;

public class PegawaiAlreadyExistsException extends RuntimeException {
    public PegawaiAlreadyExistsException(String nip) {
        super("Pegawai dengan nip " + nip + " sudah ada.");
    }
}
