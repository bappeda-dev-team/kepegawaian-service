package cc.kertaskerja.kepegawaian.pegawai.domain;

public enum PegawaiStatus {
    AKTIF("Aktif"),
    NONAKTIF("Non Aktif");

    private final String label;

    PegawaiStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
