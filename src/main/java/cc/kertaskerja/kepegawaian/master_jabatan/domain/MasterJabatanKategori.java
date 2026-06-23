package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public enum MasterJabatanKategori {
    PIMPINAN_TINGGI("Jabatan Pimpinan Tinggi"),
    STRUKTURAL("Jabatan Struktural"),
    FUNGSIONAL_AHLI("Jabatan Fungsional Ahli"),
    FUNGSIONAL_KETERAMPILAN("Jabatan Fungsional Keterampilan"),
    ADMINISTRASI("Jabatan Administrasi"),
    PELAKSANA("Jabatan Pelaksana"),
    LAINNYA("Belum Ada Kategori");

    private final String label;

    MasterJabatanKategori(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
