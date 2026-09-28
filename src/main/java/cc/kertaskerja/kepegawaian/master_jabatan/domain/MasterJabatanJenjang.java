package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import cc.kertaskerja.kepegawaian.common.domain.LabeledEnum;

public enum MasterJabatanJenjang implements LabeledEnum {

    JPT_UTAMA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Utama"),
    JPT_MADYA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Madya"),
    JPT_PRATAMA(MasterJabatanKategori.PIMPINAN_TINGGI, "JPT Pratama"),

    ESELON_1(MasterJabatanKategori.STRUKTURAL, "Eselon I"),
    KEPALA_DINAS(MasterJabatanKategori.STRUKTURAL, "Kepala Dinas"),
    ESELON_2(MasterJabatanKategori.STRUKTURAL, "Eselon II"),
    SEKRETARIS(MasterJabatanKategori.STRUKTURAL, "Sekretaris"),
    ESELON_3(MasterJabatanKategori.STRUKTURAL, "Eselon III"),
    KEPALA_BIDANG(MasterJabatanKategori.STRUKTURAL, "Kepala Bidang"),
    ESELON_4(MasterJabatanKategori.STRUKTURAL, "Eselon IV"),
    KEPALA_SUBBAGIAN(MasterJabatanKategori.STRUKTURAL, "Kepala Subbagian"),
    ESELON_5(MasterJabatanKategori.STRUKTURAL, "Eselon V"),

    ADMINISTRATOR(MasterJabatanKategori.ADMINISTRASI, "Administrator"),
    PENGAWAS(MasterJabatanKategori.ADMINISTRASI, "Pengawas"),

    FUNGSIONAL_AHLI_UTAMA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Fungsional Ahli - Ahli Utama"),
    FUNGSIONAL_AHLI_MADYA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Fungsional Ahli -Ahli Madya"),
    FUNGSIONAL_AHLI_MUDA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Fungsional Ahli -Ahli Muda"),
    FUNGSIONAL_AHLI_PERTAMA(MasterJabatanKategori.FUNGSIONAL_AHLI, "Fungsional Ahli -Ahli Pertama"),

    FUNGSIONAL_TERTENTU_UTAMA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Teknis - Ahli Utama"),
    FUNGSIONAL_TERTENTU_MADYA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Teknis - Ahli Madya"),
    FUNGSIONAL_TERTENTU_MUDA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis - Ahli Muda"),
    FUNGSIONAL_TERTENTU_PERTAMA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis - Ahli Pertama"),
    FUNGIONAL_TERTENTU_PENYELIA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis - Penyelia"),
    FUNGIONAL_TERTENTU_MAHIR(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis -M ahir"),
    FUNGIONAL_TERTENTU_TERAMPIL(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis - Terampil"),
    FUNGIONAL_TERTENTU_PEMULA(MasterJabatanKategori.FUNGSIONAL_TERTENTU_TEKNIS, "Fungsional Tertentu Teknis - Pemula"),

    PENYELIA(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Fungsional Terampil - Penyelia"),
    MAHIR(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Fungsional Terampil - Mahir"),
    TERAMPIL(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Fungsional Terampil - Terampil"),
    PEMULA(MasterJabatanKategori.FUNGSIONAL_KETERAMPILAN, "Fungsional Terampil - Pemula"),

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

    @Override
    public String label() {
        return label;
    }
}
