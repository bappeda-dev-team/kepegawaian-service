package cc.kertaskerja.kepegawaian.pegawai.domain;

import java.util.List;

public interface PegawaiRepositoryCustom {
    void upsertAll(List<Pegawai> pegawais);
}
