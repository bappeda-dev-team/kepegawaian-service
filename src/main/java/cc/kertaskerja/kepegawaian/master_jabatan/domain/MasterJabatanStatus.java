package cc.kertaskerja.kepegawaian.master_jabatan.domain;

import cc.kertaskerja.kepegawaian.common.domain.LabeledEnum;

import java.io.Serializable;

public enum MasterJabatanStatus implements LabeledEnum {
    AKTIF("Aktif"),
    NONAKTIF("Non Aktif");

    private final String label;

    MasterJabatanStatus(String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }
}
