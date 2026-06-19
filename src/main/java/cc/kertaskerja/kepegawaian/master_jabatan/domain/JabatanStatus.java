package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public enum JabatanStatus {
    AKTIF("Aktif"),
    NONAKTIF("Non Aktif");

    private final String label;

    JabatanStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
