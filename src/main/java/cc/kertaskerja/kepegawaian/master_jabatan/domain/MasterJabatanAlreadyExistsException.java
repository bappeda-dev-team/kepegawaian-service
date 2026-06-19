package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public class MasterJabatanAlreadyExistsException extends RuntimeException {
    public MasterJabatanAlreadyExistsException(String namaJabatan) {
        super("Jabatan " + namaJabatan + " sudah ada.");
    }
}
