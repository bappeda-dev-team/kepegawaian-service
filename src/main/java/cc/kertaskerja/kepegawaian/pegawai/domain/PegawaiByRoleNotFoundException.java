package cc.kertaskerja.kepegawaian.pegawai.domain;

public class PegawaiByRoleNotFoundException extends RuntimeException {
    public PegawaiByRoleNotFoundException(String namaRole) {
        super("Pegawai dengan role : " + namaRole + " tidak ditemukan");
    }
}
