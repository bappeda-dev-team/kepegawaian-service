package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public class MasterJabatanNotFoundException extends RuntimeException {
    public MasterJabatanNotFoundException(Long id) {
        super("Jabatan dengan Id " + id + " tidak ditemukan.");
    }
}
