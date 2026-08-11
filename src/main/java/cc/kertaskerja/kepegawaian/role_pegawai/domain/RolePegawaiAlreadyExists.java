package cc.kertaskerja.kepegawaian.role_pegawai.domain;

public class RolePegawaiAlreadyExists extends RuntimeException {
    public RolePegawaiAlreadyExists() {
        super("Pegawai sudah memiliki Role yang sama");
    }
}
