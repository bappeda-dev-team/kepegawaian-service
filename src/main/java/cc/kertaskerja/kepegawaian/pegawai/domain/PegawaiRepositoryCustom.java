package cc.kertaskerja.kepegawaian.pegawai.domain;

import java.util.List;

public interface PegawaiRepositoryCustom {
    void upsertAll(List<Pegawai> pegawais);
    List<Pegawai> findDistinctByJabatanPegawaiOpdId(Long opdId);
    List<Pegawai> findDistinctByJabatanPegawaiOpdIdAndRolePegawaiRoleNama(
            Long opdId,
            String roleNama
    );
}
