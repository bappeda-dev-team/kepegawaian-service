package cc.kertaskerja.kepegawaian.role.web;

public final class RoleConstraints {

    private RoleConstraints() {
    }

    public static final String NAMA_ROLE_PATTERN =
            "^[a-zA-Z0-9_-]+$";

    public static final String NAMA_ROLE_INVALID_MESSAGE =
            "Nama role hanya boleh berisi huruf, angka, "
            + "underscore (_), dan tanda hubung (-), tanpa spasi";

    public static final String NAMA_ROLE_REQUIRED_MESSAGE =
            "Nama role wajib diisi";
}
