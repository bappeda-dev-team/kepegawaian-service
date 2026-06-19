package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JabatanJenjang {

    JPT_UTAMA(JabatanKategori.PIMPINAN_TINGGI, "JPT Utama"),
    JPT_MADYA(JabatanKategori.PIMPINAN_TINGGI, "JPT Madya"),
    JPT_PRATAMA(JabatanKategori.PIMPINAN_TINGGI, "JPT Pratama"),

    ADMINISTRATOR(JabatanKategori.ADMINISTRASI, "Administrator"),
    PENGAWAS(JabatanKategori.ADMINISTRASI, "Pengawas"),

    AHLI_UTAMA(JabatanKategori.FUNGSIONAL_AHLI, "Ahli Utama"),
    AHLI_MADYA(JabatanKategori.FUNGSIONAL_AHLI, "Ahli Madya"),
    AHLI_MUDA(JabatanKategori.FUNGSIONAL_AHLI, "Ahli Muda"),
    AHLI_PERTAMA(JabatanKategori.FUNGSIONAL_AHLI, "Ahli Pertama"),

    PENYELIA(JabatanKategori.FUNGSIONAL_KETERAMPILAN, "Penyelia"),
    MAHIR(JabatanKategori.FUNGSIONAL_KETERAMPILAN, "Mahir"),
    TERAMPIL(JabatanKategori.FUNGSIONAL_KETERAMPILAN, "Terampil"),
    PEMULA(JabatanKategori.FUNGSIONAL_KETERAMPILAN, "Pemula"),

    PELAKSANA(JabatanKategori.PELAKSANA, "Pelaksana"),

    BELUM_ADA_JENJANG(JabatanKategori.LAINNYA, "Belum Ada Jenjang");

    private final JabatanKategori kategori;
    private final String label;

    JabatanJenjang(JabatanKategori kategori, String label) {
        this.kategori = kategori;
        this.label = label;
    }

    public JabatanKategori getKategori() {
        return kategori;
    }

    public String getLabel() {
        return label;
    }

//    @JsonCreator
//    public static JabatanJenjang fromString(String value) {
//        if (value == null) return BELUM_ADA_JENJANG;
//        try {
//            return JabatanJenjang.valueOf(value.toUpperCase());
//        } catch (IllegalArgumentException ex) {
//            return BELUM_ADA_JENJANG;
//        }
//    }
}
