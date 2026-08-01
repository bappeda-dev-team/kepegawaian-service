package cc.kertaskerja.kepegawaian.role.domain;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Long id) {
        super("Role dengan id " + id + " tidak ditemukan.");
    }

    public RoleNotFoundException(String kodeRole) {
        super("Role dengan kode " + kodeRole + " tidak ditemukan.");
    }
}
