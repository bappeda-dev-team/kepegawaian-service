package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public enum JabatanKategori {
    PIMPINAN_TINGGI("Jabatan Pimpinan Tinggi"),
    STRUKTURAL("Jabatan Struktural"),
    FUNGSIONAL_AHLI("Jabatan Fungsional Ahli"),
    FUNGSIONAL_KETERAMPILAN("Jabatan Fungsional Keterampilan"),
    ADMINISTRASI("Jabatan Administrasi"),
    PELAKSANA("Jabatan Pelaksana"),
    LAINNYA("Belum Ada Kategori");

    private final String label;

    JabatanKategori(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
