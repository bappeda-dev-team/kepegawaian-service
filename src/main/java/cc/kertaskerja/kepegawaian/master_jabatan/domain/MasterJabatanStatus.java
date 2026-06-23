package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public enum MasterJabatanStatus {
    AKTIF("Aktif"),
    NONAKTIF("Non Aktif");

    private final String label;

    MasterJabatanStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
