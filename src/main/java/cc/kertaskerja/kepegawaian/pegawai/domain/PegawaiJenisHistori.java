package cc.kertaskerja.kepegawaian.pegawai.domain;

public enum PegawaiJenisHistori {
    JABATAN("Jabatan"),
    PANGKAT("Pangkat"),
    GAJI("Gaji"),
    OPD("OPD");

    private final String label;

    PegawaiJenisHistori(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
