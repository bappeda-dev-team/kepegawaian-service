package cc.kertaskerja.kepegawaian.master_jabatan.domain;

public enum MasterJabatanJenjang {

    JPT_UTAMA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Utama"),
    JPT_MADYA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Madya"),
    JPT_PRATAMA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Pratama"),

    ESELON_1(MasterJabatanKategori.STRUKTURAL, "Eselon I"),
    ESELON_2(MasterJabatanKategori.STRUKTURAL, "Eselon II"),
    ESELON_3(MasterJabatanKategori.STRUKTURAL, "Eselon III"),
    ESELON_4(MasterJabatanKategori.STRUKTURAL, "Eselon IV"),
    ESELON_5(MasterJabatanKategori.STRUKTURAL, "Eselon V"),

    ADMINISTRATOR(MasterJabatanKategori.ADMINISTRASI, "Administrator"),
    PENGAWAS(MasterJabatanKategori.ADMINISTRASI, "Pengawas"),

    AHLI_UTAMA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Ahli Utama"),
    AHLI_MADYA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Ahli Madya"),
    AHLI_MUDA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Ahli Muda"),
    AHLI_PERTAMA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Ahli Pertama"),

    PENYELIA(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Penyelia"),
    MAHIR(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Mahir"),
    TERAMPIL(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Terampil"),
    PEMULA(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Pemula"),

    PELAKSANA(MasterJabatanKategori.PELAKSANA, "Pelaksana"),

    BELUM_ADA_JENJANG(MasterJabatanKategori.LAINNYA, "Belum Ada Jenjang");

    private final MasterJabatanKategori kategori;
    private final String label;

    MasterJabatanJenjang(MasterJabatanKategori kategori, String label) {
        this.kategori = kategori;
        this.label = label;
    }

    public MasterJabatanKategori getKategori() {
        return kategori;
    }

    public String getLabel() {
        return label;
    }
}
