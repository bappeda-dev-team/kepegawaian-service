package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

public enum JabatanPegawaiJenisPenugasan {

    UTAMA("Jabatan Utama"),
    PLT("Pelaksana Tugas"),
    PLH("Pelaksana Harian"),
    PJ("Penjabat"),

    BELUM_DIATUR("Belum Diatur");

    private final String label;

    JabatanPegawaiJenisPenugasan(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public boolean isUtama() {
        return this == UTAMA;
    }

    public boolean isPlt() {
        return this == PLT;
    }

    public boolean isPlh() {
        return this == PLH;
    }

    public boolean isPj() {
        return this == PJ;
    }
}
