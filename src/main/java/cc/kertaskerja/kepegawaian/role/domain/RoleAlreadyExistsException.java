package cc.kertaskerja.kepegawaian.role.domain;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String kodeRole) {
        super("Role dengan kode " + kodeRole + " sudah ada.");
    }
}
