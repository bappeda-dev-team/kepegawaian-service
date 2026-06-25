package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import cc.kertaskerja.kepegawaian.common.domain.LabeledEnum;

public enum MasterJabatanKategori implements LabeledEnum {
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

    @Override
    public String label() {
        return label;
    }
}
