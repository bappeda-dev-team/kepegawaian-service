package cc.kertaskerja.kepegawaian.role.domain;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String namaRole) {
        super("Role " + namaRole + " sudah ada.");
    }
}
