package cc.kertaskerja.kepegawaian.role_pegawai.domain;

public class RolePegawaiNotFoundException extends RuntimeException {
    public RolePegawaiNotFoundException() {
        super("Role Pegawai tidak ditemukan");
    }
}
