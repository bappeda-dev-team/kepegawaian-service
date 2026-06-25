package cc.kertaskerja.kepegawaian.jabatan_pegawai.domain;

import cc.kertaskerja.kepegawaian.common.domain.LabeledEnum;

public enum JabatanPegawaiAlasanBerakhir implements LabeledEnum {

    MUTASI("Mutasi"),
    PENSIUN("Pensiun"),
    PROMOSI("Promosi"),
    DEMOSI("Demosi"),
    MENINGGAL("Meninggal Dunia"),

    LAINNYA("Lainnya");

    private final String label;

    JabatanPegawaiAlasanBerakhir(String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }

    public boolean isMutasi() {
        return this == MUTASI;
    }

    public boolean isPensiun() {
        return this == PENSIUN;
    }
}
